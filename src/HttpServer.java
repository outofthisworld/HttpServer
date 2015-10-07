import http.header.HttpHeader;
import http.header.HttpHeaderDecoder;
import utils.Configuration;

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

        HttpHeaderDecoder httpHeaderDecoder = new HttpHeaderDecoder();
        HttpHeader httpHeader = new HttpHeader();
        //Start accepting connections
        while(isRunning){
            try {
                Socket socket = serverSocket.accept();

                //We have received a connection... log it
                logger.log(Level.INFO, "Received connection from " + socket.getInetAddress().getHostAddress());
                httpHeaderDecoder.decode(httpHeader,socket.getInputStream(), StandardCharsets.UTF_8,true);





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
