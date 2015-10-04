import http.header.DefaultHttpHeader;
import utils.Configuration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

                DefaultHttpHeader defaultHttpHeader = new DefaultHttpHeader();

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
