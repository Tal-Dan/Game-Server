package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.services.GameServerController;

public class Server implements PropertyChangeListener, Runnable {

	private int port;
	private int capacity;
	private int connected_clients = 0;
	boolean server_on = false;
	private volatile boolean running = true;
	ServerSocket server;
	Socket clientSocket;
	GameServerController gameServerController;

	public Server(int port) {
		this.port = port;
		this.capacity = 20; // default value
	}

	private synchronized void changeServerState(boolean b) {
		running = b;
	}

	// operation can be 1 / -1 for adding or removing clients
	private synchronized void connectedClientsUpdate(int operation) {
		connected_clients += operation;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("START")) {
			gameServerController = new GameServerController(capacity);

			if (!server_on) {
				server_on = true;
				changeServerState(true);
				new Thread(this).start();
			} else {
				System.out.println("Invalid Action - Server is already on");
			}

		} else if (evt.getPropertyName().equals("CONFIG_SERVER_GAME")) {
			capacity = (int) evt.getNewValue();

		} else if (evt.getPropertyName().equals("SHUTDOWN")) {
			if (server_on) {
				server_on = false;
				changeServerState(false);

				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Invalid Action - Server is not on");
			}
		}
	}

	@Override
	public void run() {

		Executor pool = Executors.newFixedThreadPool(3);

		try {
			this.server = new ServerSocket(port);
			System.out.println("Starting server...");

			while (running) {
				if (connected_clients <= capacity) {
					connectedClientsUpdate(1);
					pool.execute(new HandleRequest(server.accept(), gameServerController));
					connectedClientsUpdate(-1);
				} else {
					System.out.println("To many connection to server");
				}

			}
		} catch (SocketException e) {
			System.out.println("Shutdown server...");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
