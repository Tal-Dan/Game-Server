package com.hit.gameAlgo;

import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.games.CatchTheBunny;
import com.hit.games.CatchTheBunnyRandom;
import com.hit.games.CatchTheBunnySmart;
import com.hit.games.TicTacTowRandom;
import com.hit.games.TicTacTowSmart;

import java.util.Arrays;
import java.util.Scanner;

public interface IGameAlgo {
	public static enum GameState {
		ILLEGAL_PLAYER_MOVE, IN_PROGRESS, PLAYER_LOST, PLAYER_WON, TIE
	}

	void calcComputerMove();

	char[][] getBoardState();

	IGameAlgo.GameState getGameState(GameBoard.GameMove move);

	boolean updatePlayerMove(GameBoard.GameMove move);

}
