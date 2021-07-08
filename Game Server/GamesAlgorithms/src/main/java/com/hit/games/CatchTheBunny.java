package com.hit.games;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.GameBoard.GameMove;
import com.hit.gameAlgo.IGameAlgo.GameState;

public abstract class CatchTheBunny extends GameBoard {
	protected final char PLAYER_SIGN = 'K'; // K for Kid cell
	protected final char COMPUTER_SIGN = 'B'; // B for Bunny cell
	protected int turns;
	protected int[] kid_location = { 0, 0 };
	protected int[] bunny_location = { 0, 0 };
	private GameState state = GameState.IN_PROGRESS;

	public static enum BoardSigns {
		BLANK, COMPUTER, PLAYER;

		public char getSign() {
			return 'x';
		}
	}

	// Optional - by default will be 10
	public void setTurns(int x) {
		this.turns = turns;
	}

	public CatchTheBunny(int rowLength, int colLength) {
		super(rowLength, colLength);
		turns = 10;
		board[7][7] = PLAYER_SIGN; // can be modified to be random locations
		board[4][4] = COMPUTER_SIGN;
		kid_location[0] = 7;
		kid_location[1] = 7;
		bunny_location[0] = 4;
		bunny_location[1] = 4;

	}

	@Override
	public boolean updatePlayerMove(GameMove move) {

		// left
		if (move.getRow() == kid_location[0] && kid_location[1] - 1 == move.getColumn()) {
			move.setMove(0, -1);
		}

		// right
		else if (move.getRow() == kid_location[0] && kid_location[1] + 1 == move.getColumn()) {
			move.setMove(0, 1);
		}
		// up
		else if (kid_location[0] - 1 == move.getRow() && move.getColumn() == kid_location[1]) {
			move.setMove(-1, 0);
		}
		// down

		else if (kid_location[0] + 1 == move.getRow() && move.getColumn() == kid_location[1]) {
			move.setMove(1, 0);
		}

		// diagonal == ILLAGAL
		else {
			return false;
		}

		// update played turns taken
		turns -= 1;

		// update the board
		board[kid_location[0]][kid_location[1]] = '-';
		board[kid_location[0] + move.getRow()][kid_location[1] + move.getColumn()] = PLAYER_SIGN;

		// update players new location
		kid_location[0] += move.getRow();
		kid_location[1] += move.getColumn();
		return true;

	}

	@Override
	public IGameAlgo.GameState getGameState(GameBoard.GameMove move) {

		if (state == GameState.ILLEGAL_PLAYER_MOVE) {
			return state;
		}
		if (kid_location[0] == bunny_location[0] && kid_location[1] == bunny_location[1]) {
			return GameState.PLAYER_WON;
		}
		if (turns < 0) {
			return GameState.PLAYER_LOST;
		}

		return GameState.IN_PROGRESS;
	}

	@Override
	public char[][] getBoardState() {
		return board;
	}

	protected Boolean validateMove(int row, int col) {
		if (bunny_location[0] + row > board.length - 1 || bunny_location[0] + row < 0
				|| bunny_location[1] + col > board.length - 1 || bunny_location[1] + col < 0) {
			return false;
		}

		// diagonal is forbidden
		if ((row == col) || (row == -1 && col == 1) || (row == 1 && col == -1)) {
			return false;
		}

		// compute moved to player's spot
		if (kid_location[0] == bunny_location[0] + row && kid_location[1] == bunny_location[1] + col) {
			return false;

		}
		return true;
	}

}
