package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Scanner;

import javax.jws.soap.SOAPBinding.Use;

public class CLI implements Runnable {

	private int capacity = 5; // Default value
	private String user_input;
	private Boolean valid_input = false;

	PropertyChangeSupport propertyChangeHandler;

	public CLI(InputStream in, OutputStream out) {
		propertyChangeHandler = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeHandler.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeHandler.removePropertyChangeListener(pcl);
	}

	public void writeResponse(String response) {

	}

	@Override
	public void run() {
		System.out.println("Please enter your command");

		while (true) {
			Scanner myObj = new Scanner(System.in);
			user_input = myObj.next();

			if (user_input.equals("CONFIG_SERVER_GAME")) {
				System.out.println("Please enter capacity");
				user_input = myObj.next();
				propertyChangeHandler.firePropertyChange(user_input, capacity, -1);
			} else if (user_input.equals("START")) {
				propertyChangeHandler.firePropertyChange(user_input, 0, 1);
			} else if (user_input.equals("SHUTDOWN")) {
				propertyChangeHandler.firePropertyChange(user_input, 0, 1);
			}
			else {
				System.out.println("Not a valid command");
			}

		}
	}

}
