package com.hit.services;

import java.util.HashMap;

import com.hit.exception.UnknownIdException;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameGameHandler.BoardGameHandler;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;

public class GameService {
	private static int gameId = 0;
	HashMap<Integer, BoardGameHandler> gamesMap;

	public GameService(int capacity) {
		gamesMap = new HashMap<Integer, BoardGameHandler>(capacity);
	}

	public int newGame(String gameType, String opponent) {
		gameId++;
		if (gameType.equals("Catch The Bunny")) {
			if (opponent.equals("Random")) {
				gamesMap.put(gameId, new BoardGameHandler(new CatchTheBunnyRandom(9, 9)));
			} else {
				gamesMap.put(gameId, new BoardGameHandler(new CatchTheBunnySmart(9, 9)));
			}
		}

		if (gameType.equals("Tic Tac Toe")) {
			if (opponent.equals("Random")) {
				gamesMap.put(gameId, new BoardGameHandler(new TicTacTowRandom(3, 3)));
			} else {
				gamesMap.put(gameId, new BoardGameHandler(new TicTacTowSmart(3, 3)));
			}
		}
		return gameId;
	}

	public IGameAlgo.GameState updateMove(int gameId, GameBoard.GameMove playerMove) throws UnknownIdException {
		if (!gamesMap.containsKey(gameId)) {
			throw new UnknownIdException("Game ID: " + gameId + "Not Exsits ", new Exception());
		} else {
			return gamesMap.get(gameId).playOneRound(playerMove);
		}

	}

	public char[][] getBoardState(int gameId) {
		
		char[][] c = null;
		if (!gamesMap.containsKey(gameId)) {
			try {
				throw new UnknownIdException("Game ID: " + gameId + "Not Exsits ", new Exception());
			} catch (UnknownIdException e) {
 				e.printStackTrace();
			}
		} else {

			c = gamesMap.get(gameId).getBoardState();
		}
		return c;
	}

	public char[][] computerStartGame(int gameId) throws UnknownIdException {
		char[][] c = null;
		if (!gamesMap.containsKey(gameId)) {
			try {
				throw new UnknownIdException("Game ID: " + gameId + "Not Exsits ", new Exception());
			} catch (UnknownIdException e) {
 				e.printStackTrace();
			}
		} else {
			c = gamesMap.get(gameId).computerStartGame();
		}
		return c;
	}

	public void endGame(int gameId) throws UnknownIdException {
		if (!gamesMap.containsKey(gameId)) {
			try {
				throw new UnknownIdException("Game ID: " + gameId + "Not Exsits ", new Exception());
			} catch (UnknownIdException e) {
 				e.printStackTrace();
			}
		} else {

		}
	}

}
