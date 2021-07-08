package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GamesClient {

	private int serverPort;
	ObjectOutputStream output;
	ObjectInputStream input;
	Socket socket;
	String messageFromServer = null;

	public GamesClient(int serverPort) {
		this.serverPort = serverPort;
	}

	public String sendMessage(String message, boolean hasResponse) {

		messageFromServer = null;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());

			output.writeObject(message);
			output.flush();

			if (hasResponse) {
				input = new ObjectInputStream(socket.getInputStream());
				message = (String) input.readObject();
			}
			else {
				closeConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// closeConnection();
		return message;
	}

	public void connectToServer() {

		try {
			socket = new Socket("localhost", serverPort);
		} catch (IOException e) {
			System.out.println("Server Is Down");
		}
	}

	public void closeConnection() {
		try {
			output.flush();
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
