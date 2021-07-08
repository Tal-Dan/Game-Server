package com.hit.catchTheBunny;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo.GameState;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;

class CatchTheBunnyTest {

	IGameAlgo gameBoard = (CatchTheBunnySmart) new CatchTheBunnySmart(9, 9);
	GameMove move = new GameMove(1, 1);

	@Test
	void startGame() {
		assertTrue(gameBoard.getGameState(move).equals(GameState.IN_PROGRESS));
	}

	@Test
	void playerLost() {
		gameBoard = (CatchTheBunnySmart) new CatchTheBunnySmart(9, 9);
		while (gameBoard.getGameState(move).equals(GameState.IN_PROGRESS)) {

			move.setMove(1, 0);
			if (gameBoard.updatePlayerMove(move) == false) {
				move.setMove(0, -1);
			}
			gameBoard.updatePlayerMove(move);
			gameBoard.calcComputerMove();

		}
		assertTrue(gameBoard.getGameState(move).equals(GameState.PLAYER_LOST));
	}

	@Test
	void illegalPlayerMoveNotAdjacentCell() {
		move.setMove(8, 8);
		assertTrue(gameBoard.updatePlayerMove(move) == false);
	}

	@Test
	void illegalPlayerMoveBottom() {
		move.setMove(8, 8);
		assertTrue(gameBoard.updatePlayerMove(move) == false);
	}

}
