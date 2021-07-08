package com.hit.ticTacTow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTow;
import com.hit.games.TicTacTowRandom;

class TicTacTowTest {

	IGameAlgo gameBoard;
	GameMove move = new GameMove(1, 1);
	GameState state;

	@Test
	void startGame() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		assertTrue(gameBoard.getGameState(move).equals(GameState.IN_PROGRESS));
	}

	@Test
	void endGame() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		boolean valid = false;
		gameBoard.calcComputerMove();
		while (gameBoard.getGameState(move).equals(GameState.IN_PROGRESS)) {

			valid = gameBoard.updatePlayerMove(move);
			while (!valid) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						move.setMove(i, j);
						valid = gameBoard.updatePlayerMove(move);
					}
				}
				state = gameBoard.getGameState(move);
				gameBoard.calcComputerMove();
			}
		}
		assertFalse(gameBoard.getGameState(move).equals(GameState.IN_PROGRESS));
	}
	
	@Test
	void illegalMoveColMax() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		move.setMove(0, 4);
		assertFalse(gameBoard.updatePlayerMove(move));
	}
	
	@Test
	void illegalMoveColMin() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		move.setMove(0,-3);
		assertFalse(gameBoard.updatePlayerMove(move));
	}
	
	@Test
	void illegalMoveRowMax() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		move.setMove(4, 0);
		assertFalse(gameBoard.updatePlayerMove(move));
	}
	
	@Test
	void illegalMoveRowMin() {
		gameBoard = (TicTacTowRandom) new TicTacTowRandom(9, 9);
		move.setMove(-2, 0);
		assertFalse(gameBoard.updatePlayerMove(move));
	}
}
