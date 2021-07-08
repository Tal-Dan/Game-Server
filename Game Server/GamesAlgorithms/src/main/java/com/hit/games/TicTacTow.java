package com.hit.games;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.GameBoard.GameMove;

public abstract class TicTacTow extends GameBoard {
	protected final char PLAYER_SIGN = 'X';
	protected final char COMPUTER_SIGN = 'O';

	protected static int countFilledCells = 0; // to know if the game reached TIE (9 moves and no winning)

	public static enum BoardSigns {
		BLANK, COMPUTER, PLAYER;

		public char getSign() {
			return 'x';
		}
	}

	@Override
	public boolean updatePlayerMove(GameMove move) {

		if (move.getRow() < 0) {
			return false;
		}
		if ((move.getRow() > 2)) {
			return false;
		}
		if ((move.getColumn() < 0)) {
			return false;
		}
		if (move.getColumn() > 2) {
			return false;
		}

		// verify if location is not take
		if ((board[move.getRow()][move.getColumn()] != '-')) {
			return false;
		}

		board[move.getRow()][move.getColumn()] = PLAYER_SIGN;
		countFilledCells++;
		return true;
		 

	}

	public TicTacTow(int rowLength, int colLength) {
		super(rowLength, colLength);
		countFilledCells = 0;
	}

	@Override
	public IGameAlgo.GameState getGameState(GameBoard.GameMove move) {
		String row_result = null;

		if (countFilledCells == 9) {
			return GameState.TIE;
		}

		// check 3 row options
		for (char[] row : board) {
			row_result = String.valueOf(row);
			if (row_result.equals("XXX")) {

				return GameState.PLAYER_WON;
			}
			if (row_result.equals("OOO")) {
				return GameState.PLAYER_LOST;
			}
		}

		// check 3 columns options
		for (int i = 0; i < board.length; i++) {
			if ((board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == 'X')) {
				return GameState.PLAYER_WON;
			}
			if ((board[0][i] == 'O' && board[1][i] == 'O' && board[2][i] == 'O')
					|| board[2][0] == 'O' && board[1][1] == 'O' && board[0][2] == 'O') {
				return GameState.PLAYER_LOST;
			}
		}

		// check 2 diagonal options
		if ((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X')
				|| board[2][0] == 'X' && board[1][1] == 'X' && board[0][2] == 'X') {
			return GameState.PLAYER_WON;
		}
		if ((board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O')
				|| board[2][0] == 'O' && board[1][1] == 'O' && board[0][2] == 'O') {
			return GameState.PLAYER_LOST;
		}

		return GameState.IN_PROGRESS;

	}

	@Override
	public char[][] getBoardState() {
		return this.board;
	}

}
