package projectClientServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CO2Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Use the server's IP address or hostname
        int serverPort = 12345; // Use the same port as the server

        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");
            System.out.println("Enter 'exit' to quit.");

            while (true) {
                System.out.print("Enter User ID: ");
                String userID = scanner.nextLine();

                if (userID.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.print("Enter Postcode: ");
                String postcode = scanner.nextLine();

                System.out.print("Enter CO2 concentration (ppm): ");
                String co2PPM = scanner.nextLine();

                String data = userID + "," + postcode + "," + co2PPM;
                out.println(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
