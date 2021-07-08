package com.hit.model;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GamesModel implements Model {

	PropertyChangeSupport propertyChangeHandler;
	GamesClient gameClient;
	JSONParser jsonParser = new JSONParser();
	String server_message = null;
	String gameId = "-1";

	public GamesModel() {
		propertyChangeHandler = new PropertyChangeSupport(this);
		this.gameClient = new GamesClient(34567);
	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeHandler.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void newGame(String gameType, String opponentType) {
		gameClient.connectToServer();

		JSONObject msg = new JSONObject();
		msg.put("type", "New-Game");
		msg.put("game", gameType);
		msg.put("opponent", opponentType);

		server_message = gameClient.sendMessage(msg.toString(), true);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = (JSONObject) jsonParser.parse(server_message);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		gameId = String.valueOf(jsonObject.get("ID"));

		propertyChangeHandler.firePropertyChange("Board-Update", null, (String) jsonObject.get("board"));

	}

	@Override
	public void updatePlayerMove(int row, int col) {

		JSONObject msg = new JSONObject();
		msg.put("type", "Update-Move");
		msg.put("ID", gameId);
		msg.put("row", row);
		msg.put("col", col);

		server_message = gameClient.sendMessage(msg.toString(), true);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = (JSONObject) jsonParser.parse(server_message);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		//IN_PROGRESS
		if (((String) jsonObject.get("state")).equals("0")) {
			propertyChangeHandler.firePropertyChange("Board-Update", null, (String) jsonObject.get("board"));
		}
		
		//PLAYER_LOST
		else if (((String) jsonObject.get("state")).equals("2")) {
			propertyChangeHandler.firePropertyChange("Board-Update", null, (String) jsonObject.get("board"));
			propertyChangeHandler.firePropertyChange("End-Game", null, (String) jsonObject.get("state"));
			//gameClient.closeConnection();
		}
		// ILEGALL MOVE
		else if (((String) jsonObject.get("state")).equals("4")) {
			propertyChangeHandler.firePropertyChange("Board-Update", null, (String) jsonObject.get("board"));
		}
		
		//TIE ? PLAYER_WON ? 
		else {
			propertyChangeHandler.firePropertyChange("End-Game", null, (String) jsonObject.get("state"));
			//gameClient.closeConnection();
		}

	}
	
	private void endGame() {
		JSONObject msg = new JSONObject();
		msg.put("type", "Stop-Game");
		msg.put("ID", gameId);
		
		server_message = gameClient.sendMessage(msg.toString(), false);
		gameClient.closeConnection();
		
	}
}
