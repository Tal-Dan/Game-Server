package com.hit.gameAlgo;

import java.util.Arrays;

public abstract class GameBoard implements IGameAlgo {

	protected char[][] board;

	// Contractor
	public GameBoard(int rowLength, int colLength) {
		board = new char[rowLength][colLength];

		// Initialize the board with '-' - meaning empty cell
		for (char[] row : board) {
			Arrays.fill(row, '-');
		}
	}

	// Nested Class
	public static class GameMove {
		private int row, col;

		public GameMove(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getColumn() {
			return this.col;
		}

		public void setMove(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	// Implement of IGameAlgo interface
	public abstract void calcComputerMove();

	public abstract char[][] getBoardState();

	public abstract GameState getGameState(GameBoard.GameMove move);

	public abstract boolean updatePlayerMove(GameBoard.GameMove move);

}
