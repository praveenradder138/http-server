import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

            byte[] bytes = inputStream.readAllBytes();

            String input = new String(bytes);

            //GET /index.html HTTP/1.1\r\nHost: localhost:4221\r\nUser-Agent: curl/7.64.1\r\nAccept: */*\r\n\r\n

            String[] inputArray = input.split("\r\n");
            String requestLine = inputArray[0];

            String resource = requestLine.split(" ")[1];
            OutputStream outputStream = clientSocket.getOutputStream();
            if("abcdefg".equals(resource)){
                System.out.println("Invalid url");
                outputStream.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }

            if("".equals(resource)){
                System.out.println("Valid url");
                outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            }






        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
