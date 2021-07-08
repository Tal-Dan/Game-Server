package com.hit.games;

public class CatchTheBunnySmart extends CatchTheBunny {
	int row;
	int col;

	public CatchTheBunnySmart(int rowLength, int colLength) {
		super(rowLength, colLength);

	}

	@Override
	public void calcComputerMove() {
		GameMove move = new GameMove(0, 0);

		// corner cases
		// left up corner
		if (bunny_location[0] == 0 && bunny_location[1] == 0) {
			if (kid_location[0] == 0) {
				row = 1;
				col = 0;
			}
			else {
				row = 0;
				col = 1;
			}
		}
		// right up corner
		else if (bunny_location[0] == 0 && bunny_location[1] == 8) {
			if (kid_location[0] == 0) {
				row = 1;
				col = 0;
			}
		}
		// left bottom corner
		else if (bunny_location[0] == 8 && bunny_location[1] == 0) {
			if (kid_location[0] == 8) {
				row = -1;
				col = 0;
			}
		}
		// right bottom corner
		else if (bunny_location[0] == 8 && bunny_location[1] == 8) {
			if (kid_location[0] == 8) {
				row = -1;
				col = 0;
			}
		}

		// check what row is the best
		else {
			if (bunny_location[0] == 8 || bunny_location[0] == 0) {
				row = 0;
			} else {
				if (kid_location[0] > bunny_location[0]) {
					row = -1;
				} else {
					row = 1;
				}
			}
			// check what col is the best
			if (row == 0) {
				if (bunny_location[1] == 8) {
					col = -1;
				} else if (bunny_location[1] == 0) {
					col = 1;
				} else if (kid_location[1] > bunny_location[1]) {
					col = -1;
				} else {
					col = 1;
				}
			}
		}

		// update the board
		board[bunny_location[0]][bunny_location[1]] = '-';

		// update players new location
		bunny_location[0] += row;
		bunny_location[1] += col;

		board[bunny_location[0]][bunny_location[1]] = COMPUTER_SIGN;

	}

}
