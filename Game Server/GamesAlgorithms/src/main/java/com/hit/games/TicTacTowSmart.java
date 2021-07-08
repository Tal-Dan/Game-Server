package com.hit.games;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class TicTacTowSmart extends TicTacTow {

	private char tempBoard[][] = new char[9][9];
	private char player = 'O';
	private char opponent = 'X';
	Object[] best = new Object[3];

	public TicTacTowSmart(int rowLength, int colLength) {
		super(rowLength, colLength);
	}

	@Override
	public void calcComputerMove() {
		tempBoard = this.board;
		GameMove move = findBestMove(this.board);
		board[move.getRow()][move.getColumn()] = 'O';
		countFilledCells++;
	}

	boolean isMovesLeft(char b[][]) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (b[i][j] == '-')
					return true;
		return false;
	}

	int evaluate(char b[][]) {
		// Checking for Rows for X or O victory.
		for (int row = 0; row < 3; row++) {
			if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
				if (b[row][0] == player)
					return +10;
				else if (b[row][0] == opponent)
					return -10;
			}
		}

		// Checking for Columns for X or O victory.
		for (int col = 0; col < 3; col++) {
			if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
				if (b[0][col] == player)
					return +10;

				else if (b[0][col] == opponent)
					return -10;
			}
		}

		// Checking for Diagonals for X or O victory.
		if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
			if (b[0][0] == player)
				return +10;
			else if (b[0][0] == opponent)
				return -10;
		}

		if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
			if (b[0][2] == player)
				return +10;
			else if (b[0][2] == opponent)
				return -10;
		}

		// Else if none of them have won then return 0
		return 0;
	}

	int minimax(char board[][], int depth, Boolean isMax) {
		int score = evaluate(board);

		// If computer has won the game end node
		if (score == 10)
			return score;

		// If player has won the game return his/her
		if (score == -10)
			return score;

		// If there are no more moves and no winner then
		// it is a tie
		if (isMovesLeft(board) == false)
			return 0;

		// If this maximizer's move
		if (isMax) {
			int best = -1000;

			// Traverse all cells
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (tempBoard[i][j] == '-') {
						// Make the move
						tempBoard[i][j] = player;

						// Call minimax recursively and choose
						// the maximum value
						best = Math.max(best, minimax(tempBoard, depth + 1, !isMax));

						// Undo the move
						tempBoard[i][j] = '-';
					}
				}
			}
			return best;
		}

		// If this player move
		else {
			int best = 1000;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Check if cell is empty
					if (tempBoard[i][j] == '-') {
						tempBoard[i][j] = opponent;

						// minmax for the minimum value
						best = Math.min(best, minimax(board, depth + 1, !isMax));

						// Undo the move
						tempBoard[i][j] = '-';
					}
				}
			}
			return best;
		}
	}

	GameMove findBestMove(char board[][]) {
		int bestVal = -1000;
		GameMove bestMove = new GameMove(-1, -1);

		// check for all cell all the option
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (tempBoard[i][j] == '-') {
					// Make the move
					tempBoard[i][j] = player;

					// compute evaluation function for this
					// move.
					int moveVal = minimax(tempBoard, 0, false);

					// Undo the move
					tempBoard[i][j] = '-';

					// if correnct cell is the best -> set move , and keep bestVal
					if (moveVal > bestVal) {
						bestMove.setMove(i, j);
						bestVal = moveVal;
					}
				}
			}
		}

		return bestMove;
	}

}
