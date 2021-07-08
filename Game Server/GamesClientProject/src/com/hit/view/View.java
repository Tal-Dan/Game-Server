package com.hit.view;

public interface View {

	void start();

	void updateViewGameMove(int gameState, Character[] board);

	void updateViewNewGame(Character[] board);

}
