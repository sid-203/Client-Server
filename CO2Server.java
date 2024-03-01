package projectClientServer;

import java.io.*;
import java.net.*;
import java.util.Date;

public class CO2Server {
	public static void main(String[] args) {
		int port = 3309; // Choose a port number

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server now is listening on port: " + port);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected from: " + clientSocket.getInetAddress());

				Thread clientHandler = new ClientHandler(clientSocket);
				clientHandler.start();
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
}

class ClientHandler extends Thread {
	private Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try (
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter dataWriter = new BufferedWriter(new FileWriter("co2_data.csv", true))
				) {
			String input;
			while ((input = in.readLine()) != null) {
				String[] parts = input.split(",");
				if (parts.length == 3) {
					String userID = parts[0];
					String postcode = parts[1];
					String co2PPM = parts[2];
					String timestamp = new Date().toString();

					String entry = timestamp + "," + userID + "," + postcode + "," + co2PPM;
					dataWriter.write(entry + "\n");
					dataWriter.flush();

					System.out.println("Received data from Researcher ID: " + userID + ", Reading: " + co2PPM + " ppm at " + postcode);
				}
			}
		} 
		
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
