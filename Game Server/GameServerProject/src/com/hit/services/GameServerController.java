package com.hit.services;

import com.hit.exception.*;
import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;

public class GameServerController {

	private int capacity;
	public GameService gameService;

	public GameServerController(int capacity) {
		this.capacity = capacity;
		gameService = new GameService(capacity);
	}

	public int newGame(String gameType, String opponent) {
		return gameService.newGame(gameType, opponent);
	}

	public void endGame(Integer gameId) throws UnknownIdException {
		try {
			gameService.endGame(gameId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public IGameAlgo.GameState updateMove(int gameId, GameBoard.GameMove playerMove) throws UnknownIdException {
		return gameService.updateMove(gameId, playerMove);
	}

	public char[][] computerStartGame(int gameId) throws UnknownIdException {

		return gameService.computerStartGame(gameId);
	}

}
