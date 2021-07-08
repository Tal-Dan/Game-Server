package com.hit.games;

import java.util.Random;

public class CatchTheBunnyRandom extends CatchTheBunny {

	private int rnd_row;
	private int rnd_col;

	public CatchTheBunnyRandom(int rowLength, int colLength) {
		super(rowLength, colLength);
	}

	@Override
	public void calcComputerMove() {
		rnd_row = -999;
		rnd_col = -999;
		Random rand = new Random();
		// keep calculating a random location until reaching a valid location
		while (!validateMove(rnd_row, rnd_col)) {
			rnd_row = -1 + (int) (rand.nextInt(3));
			rnd_col = -1 + (int) (rand.nextInt(3));
		}

		// update the board
		board[bunny_location[0]][bunny_location[1]] = '-';

		// update players new location
		bunny_location[0] += rnd_row;
		bunny_location[1] += rnd_col;

		board[bunny_location[0]][bunny_location[1]] = COMPUTER_SIGN;

	}
}
