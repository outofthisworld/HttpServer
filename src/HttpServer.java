import Http.Header;
import Http.ResponseBuilder;
import utils.Configuration;
import utils.Delimiters;
import utils.HttpMethods;
import utils.HttpStatusCode;

import java.io.*;
import java.net.HttpCookie;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daleappleby on 3/10/15.
 */
public class HttpServer implements Runnable {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getName());
    private Callable<?> onStartUp = null;
    private Callable<?> onShutdown = null;
    private boolean isRunning = false;

    protected HttpServer(){}

    public static void main(String[] args) {
        startNewHttpServer();
    }

    public static HttpServer startNewHttpServer(){
        HttpServer httpServer = new HttpServer();
        new Thread(httpServer).start();
        return httpServer;
    }

    public void setOnStartUP(Callable<?> callable){
        this.onStartUp = callable;
    }

    public void setOnShutdown(Callable<?> callable){
        this.onShutdown = callable;
    }

    public void stop() {
        if(!isRunning)
            return;

        isRunning = false;
    }

    public boolean isRunning(){
        return isRunning;
    }

    @Override
    public void run() {
        //Set the server to running
        isRunning = true;

        //Execute the on start up task should there be one.
        executeTask(onStartUp);

        //Log that the server is now running
        logger.log(Level.INFO,"Started http server on port " + Configuration.HTTP_PORT);

        //Create a new server socket
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Configuration.HTTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Check to see if the socket is null
        if(serverSocket == null) {
            isRunning = false;
            logger.log(Level.SEVERE,"Error occurred whilst starting the server, socket was null.");
            return;
        }

        final ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.setAllow(new HttpMethods[]{HttpMethods.GET,HttpMethods.POST})
                        .setServer(Configuration.HTTP_SERVER_NAME);

        //Start accepting connections
        while(isRunning){
            try {
                Socket socket = serverSocket.accept();

                //We have received a connection... log it
                logger.log(Level.INFO, "Received connection from " + socket.getInetAddress().getHostAddress());

                //Create a buffered input stream to read incoming packets
                BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());

                //Byte buffer to hold the header
                byte[] buffer = new byte[Configuration.MAX_HEADER_SIZE];

                //Read the header into the buffer
                bufferedInputStream.read(buffer,0,buffer.length);

                //Create a string from the header
                String header = new String(buffer,0,buffer.length, StandardCharsets.UTF_8);

                //Create and parse the header via a header obj
                Header headerObj = new Header(header);

                //Check to see if its a valid header
                if(!headerObj.isValidHeader()){
                    //Malformed header
                    logger.log(Level.INFO,"Received malformed header: " + headerObj.getHeader());

                    //Write bad request response - status code 400
                    responseBuilder.setHttpVers(headerObj.getHeader("httpVers"))
                            .setHttpResponseCode(HttpStatusCode.BAD_REQUEST)
                            .setDate(Calendar.getInstance().getTime());
                    System.out.println(responseBuilder.build());
                    new BufferedOutputStream(socket.getOutputStream()).write(responseBuilder.build().getBytes());
                    continue;
                }

                //Check the resource exists otherwise 404 not found
                String resourcePath = headerObj.getHeader("resource");
                File file = new File(HttpServer.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                        Configuration.DEFAULT_WEB_FOLDER + (resourcePath.equals(Delimiters.SLASH)?Configuration.DEFAULT_WEB_FILE:resourcePath));
                try {
                    if(!file.exists()){
                        logger.log(Level.INFO,"Could not retrieve requested resource " + file.getAbsolutePath());
                    }
                    FileInputStream fileInputStream = new FileInputStream(file);
                }catch (FileNotFoundException ex){
                    //Resource not found Handle this soon...
                    responseBuilder.setHttpVers(headerObj.getHeader("httpVers"))
                            .setHttpResponseCode(HttpStatusCode.NOT_FOUND)
                            .setDate(Calendar.getInstance().getTime());
                    System.out.println(responseBuilder.build());
                    socket.close();
                    continue;
                }

                //Everything has gone okay so far, reply with 202 Accepted to notify the response is being processed.
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                responseBuilder.setHttpVers(headerObj.getHeader("httpVers"))
                        .setHttpResponseCode(HttpStatusCode.ACCEPTED)
                        .setDate(Calendar.getInstance().getTime());


                //The request has been processed and everything had gone okay.. read in the resource and attach it to the request body
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileBuffer = new byte[(int)file.length()];
                fileInputStream.read(fileBuffer,0,fileBuffer.length);
                responseBuilder.setHttpVers(headerObj.getHeader("httpVers"))
                               .setContentType("text/html")
                               .setContentLength(fileBuffer.length)
                               .setHttpResponseCode(HttpStatusCode.OK)
                               .setDate(Calendar.getInstance().getTime()).
                                setBody(new String(fileBuffer));

                System.out.println(responseBuilder.build());

                //Write out the response
                DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);
                dataOutputStream.writeUTF(responseBuilder.build());
                dataOutputStream.flush();

                //Unused atm, test later
                HttpCookie cookie = new HttpCookie(String.format(Locale.ENGLISH,"SessionCookie{0}",Configuration.HTTP_SERVER_NAME), UUID.randomUUID().toString());
                cookie.setComment("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Execute the on shutdown task should there be one.
        executeTask(onShutdown);
    }


    private final void executeTask(Callable<?> task){
        if(task == null)
            return;

        try {
            task.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
