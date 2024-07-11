import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        // Uncomment this block to pass the first stage

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(4221);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.
            System.out.println("accepted new connection");

            InputStream inputStream = clientSocket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            //GET /echo/abc HTTP/1.1\r\nHost: localhost:4221\r\nUser-Agent: curl/7.64.1\r\nAccept: */*\r\n\r\n
            String line = reader.readLine();

            System.out.println(line);

            String[] inputArray = line.split(" ",0);

            OutputStream outputStream = clientSocket.getOutputStream();
            String target = inputArray[1];

            String outputMessage = "";
            System.out.println("target is "+target);
            if ("/".equals(target)) {
                outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            }else if(target.startsWith("/echo")){

                String responseString = target.split("/")[2];


                outputMessage = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: " + responseString.length() + "\r\n\r\n" +responseString;

                System.out.println("output message is "+outputMessage);

                outputStream.write(outputMessage.getBytes());
            }else{
                outputStream.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }




            //HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: 3\r\n\r\nabc
            //HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: 3\r\n\r\nabc







        } catch (Exception e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
