package com.hit.gameGameHandler;

import java.beans.PropertyChangeSupport;

import com.hit.gameAlgo.GameBoard;
import com.hit.gameAlgo.IGameAlgo;
import com.hit.gameAlgo.IGameAlgo.GameState;

public class BoardGameHandler {
	
	private IGameAlgo game ;

	public BoardGameHandler(IGameAlgo game) {
		this.game = game;
 	}
	
	public IGameAlgo.GameState playOneRound(GameBoard.GameMove playerMove){
		boolean valid =game.updatePlayerMove(playerMove);
		GameState state = game.getGameState(playerMove);
		if(!valid) {
			return GameState.ILLEGAL_PLAYER_MOVE;
		}
		if (state.equals(GameState.IN_PROGRESS)){
			game.calcComputerMove();
			state = game.getGameState(playerMove);
		}
		return state;
	}
	
	public char[][] computerStartGame(){
		game.calcComputerMove();
		return game.getBoardState();
	}
	
	public char[][] getBoardState(){
		return game.getBoardState(); 
	}

}
