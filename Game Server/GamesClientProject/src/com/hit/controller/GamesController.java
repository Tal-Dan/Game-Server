package com.hit.controller;

import java.beans.PropertyChangeEvent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hit.model.Model;
import com.hit.view.View;

//implements to  PropertyChangeListener - Observer
public class GamesController implements Controller {
	private Model model;
	private View view;
	String board;
	Character[] boardChar;

	public GamesController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("New-Game")) {
			model.newGame((String) evt.getNewValue(), (String) evt.getOldValue());
			view.updateViewNewGame(boardChar);
		}

		else if (evt.getPropertyName().equals("Board-Update")) {

			board = (String) evt.getNewValue();
			boardChar = new Character[board.length()];
			int i = 0;
			for (char value : board.toCharArray()) {
				boardChar[i++] = value;
			}

			view.updateViewNewGame(boardChar);
		}

		else if (evt.getPropertyName().equals("Update-Move")) {
			int[] location = (int[]) evt.getNewValue();
			model.updatePlayerMove(location[0], location[1]);
		}
		
		else if (evt.getPropertyName().equals("End-Game")) {
			view.updateViewGameMove(Integer.valueOf((String) evt.getNewValue()), null);
		}
	}

}
