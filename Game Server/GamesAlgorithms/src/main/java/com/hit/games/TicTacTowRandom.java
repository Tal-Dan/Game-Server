package com.hit.games;
 
public class TicTacTowRandom extends TicTacTow {

	private int mRand;

	public TicTacTowRandom(int rowLength, int colLength) {
		super(rowLength, colLength);
	}

	@Override
	public void calcComputerMove() {
		
		if(countFilledCells == 9) return;

		mRand = (int) (Math.random() * 9); // the board is total of 9 options

		// There must be at list one empty cell , random until he finds it
		while (board[mRand / 3][mRand % 3] != '-') {
			mRand = (int) (Math.random() * 9);
		}
		board[mRand / 3][mRand % 3] = COMPUTER_SIGN;
		countFilledCells++;
	}

	

}
