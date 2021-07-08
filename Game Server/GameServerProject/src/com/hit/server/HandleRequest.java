package com.hit.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.services.GameServerController;

public class HandleRequest implements Runnable {

	private Socket socket;
	private GameServerController controller;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String messageIn = null;
	int gameId;
	IGameAlgo.GameState state;
	private boolean connection = true;
	char[][] board = null;
	JSONParser jsonParser = new JSONParser();
	JSONObject msg;
	JSONObject jsonObject;
	String type;

	private GameBoard.GameMove move = new GameBoard.GameMove(1, 1);

	public HandleRequest(Socket s, GameServerController controller) throws IOException {
		this.socket = s;
		this.controller = controller;
		state = GameState.IN_PROGRESS;
	}

	@Override
	public void run() {
		int row, col;
		try {
			while (state.equals(GameState.IN_PROGRESS) || state.equals(GameState.ILLEGAL_PLAYER_MOVE)) {
				// Parse the income message from client's socket
				msg = new JSONObject();
				input = new ObjectInputStream(socket.getInputStream());
				
				//get message type
				messageIn = (String) input.readObject();
				jsonObject = (JSONObject) jsonParser.parse(new StringReader(messageIn));
				type = (String) jsonObject.get("type");

				if (type.equals("New-Game")) {
					gameId = controller.newGame((String) jsonObject.get("game"), (String) jsonObject.get("opponent"));
					board = controller.computerStartGame(gameId);

					msg.put("type", "New-Game");
					msg.put("ID", gameId);
					msg.put("board", String(board));

					//send response with new game to client
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(msg.toString());
					output.flush();
					
					
					
				}
				else if (type.equals("Update-Move")) {
					row = Integer.valueOf(jsonObject.get("row").toString());
					col = Integer.valueOf(jsonObject.get("col").toString());
					GameBoard.GameMove move = new GameBoard.GameMove(row, col);
					move.setMove(row, col);

					gameId = Integer.valueOf((String) jsonObject.get("ID"));
					state = controller.updateMove(gameId, move);
					board = controller.gameService.getBoardState(gameId);

					msg.put("type", "Update-Move");
					msg.put("ID", gameId);
					msg.put("state", getStateId(state.toString()));
					msg.put("board", String(board));

					//send response with game upadte to client
					output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(msg.toString());
					output.flush();

				}
			}

			output.close();
			input.close();
			socket.close();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnknownIdException e) {
			e.printStackTrace();
		}
	}

	private Object String(char[][] board2) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				builder.append(board2[i][j]);
			}
		}
		return builder.toString();
	}

	private String getStateId(String s) {
		if (s.equals("IN_PROGRESS")) {
			return "0";
		}
		if (s.equals("PLAYER_WON")) {
			return "1";
		}
		if (s.equals("PLAYER_LOST")) {
			return "2";
		}
		if (s.equals("TIE")) {
			return "3";
		}
		if (s.equals("ILLEGAL_PLAYER_MOVE")) {
			return "4";
		}
		return "";

	}

}
