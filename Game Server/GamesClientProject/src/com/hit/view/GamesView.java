package com.hit.view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Time;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class GamesView implements View, ActionListener {

	JFrame frame;
	JButton[][] btnArrayTic = new JButton[3][3];
	JButton[][] btnArrayBunny = new JButton[9][9];
	JButton tempK;
	JButton tempB = null;
	JPanel panel_tic;
	JPanel panel_bunny;
	String gameType = "Tic Tac Toe";
	String gameLevel = "Random";
	int reset = 0;
	int prevX, prevY;

	PropertyChangeSupport propertyChangeHandler;
	private JButton btnNewGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

			}
		});
		// GamesView v = new GamesView();
	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public GamesView() {

		// frame.setVisible(true);
		propertyChangeHandler = new PropertyChangeSupport(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 635, 613);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setBounds(800, 800, 800, 800);
		frame.setVisible(false);
		panel_tic = new JPanel();
		panel_tic.setBounds(30, 136, 574, 450);
		panel_tic.setLayout(new GridLayout(3, 3));

		panel_bunny = new JPanel();
		panel_bunny.setBounds(30, 136, 574, 450);
		panel_bunny.setLayout(new GridLayout(9, 9));

		for (int i = 0; i < btnArrayTic.length; i++) {
			for (int j = 0; j < btnArrayTic[0].length; j++) {
				btnArrayTic[i][j] = new JButton();
				btnArrayTic[i][j].setText(" ");
				btnArrayTic[i][j].addActionListener(this);
				btnArrayTic[i][j].setSize(50, 50);
				panel_tic.add(btnArrayTic[i][j]);

			}
		}
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel_tic);

		for (int i = 0; i < btnArrayBunny.length; i++) {
			for (int j = 0; j < btnArrayBunny[0].length; j++) {
				btnArrayBunny[i][j] = new JButton();
				btnArrayBunny[i][j].setText(" ");
				btnArrayBunny[i][j].addActionListener(this);
				btnArrayBunny[i][j].setSize(50, 50);
				panel_bunny.add(btnArrayBunny[i][j]);
			}
		}

		frame.getContentPane().add(panel_bunny);
		panel_tic.setVisible(true);
		

		JPanel panel_setting = new JPanel();
		panel_setting.setBounds(30, 25, 574, 99);
		frame.getContentPane().add(panel_setting);
		panel_setting.setLayout(null);

		JLabel lblSelectOppo = new JLabel("Select opponent");
		lblSelectOppo.setBounds(240, 5, 130, 15);
		panel_setting.add(lblSelectOppo);

		JLabel lblSelectGame = new JLabel("Select game");
		lblSelectGame.setBounds(33, 5, 137, 15);
		panel_setting.add(lblSelectGame);

		JRadioButton rdbtnTicTacTow = new JRadioButton("Tic Tac Toe");
		rdbtnTicTacTow.setSelected(true);
		rdbtnTicTacTow.setBounds(33, 50, 149, 23);
		panel_setting.add(rdbtnTicTacTow);

		JRadioButton rdbtnBunny = new JRadioButton("Catch The Rabbit");
		rdbtnBunny.setBounds(33, 25, 149, 23);
		panel_setting.add(rdbtnBunny);

		JRadioButton rdbtnSmart = new JRadioButton("Smart");
		rdbtnSmart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// rdbtnBunny.setSelected(false);
			}
		});
		rdbtnSmart.setBounds(240, 50, 149, 23);
		panel_setting.add(rdbtnSmart);

		JRadioButton rdbtnRandom = new JRadioButton("Random");
		rdbtnRandom.setSelected(true);
		rdbtnRandom.setBounds(240, 25, 149, 23);
		panel_setting.add(rdbtnRandom);

		rdbtnTicTacTow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnBunny.setSelected(false);
				gameType = "Tic Tac Toe";
			}
		});

		rdbtnBunny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnTicTacTow.setSelected(false);
				gameType = "Catch The Bunny";
			}
		});

		rdbtnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnSmart.setSelected(false);
				gameLevel = "Random";
			}
		});

		rdbtnSmart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnRandom.setSelected(false);
				gameLevel = "Smart";
			}
		});

		//// New game
		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(420, 10, 117, 25);
		panel_setting.add(btnNewGame);

		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameType.equals("Tic Tac Toe")) {
					panel_tic.setVisible(true);
					panel_bunny.setVisible(false);
				} else {
					panel_tic.setVisible(false);
					panel_bunny.setVisible(true);
				}
				propertyChangeHandler.firePropertyChange("New-Game", gameLevel, gameType);
			}
		});
		frame.setVisible(true);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeHandler.addPropertyChangeListener(pcl);

	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propertyChangeHandler.removePropertyChangeListener(pcl);
	}

	@Override
	public void start() {
		String[] args = null;
		this.main(args);
		frame.setVisible(true);
	}

	@Override
	public void updateViewGameMove(int gameState, Character[] board) {

		if (gameState == 1) {
			JOptionPane.showMessageDialog(null, "You Won!");
			resetBoard();
		} else if (gameState == 2) {
			JOptionPane.showMessageDialog(null, "Computer Won");
			resetBoard();
		} else if (gameState == 4) {
			JOptionPane.showMessageDialog(null, "llegal move");
			resetBoard();
		}

		else {
			JOptionPane.showMessageDialog(null, "It's a Tie..");
			resetBoard();
		}

	}

	@Override
	public void updateViewNewGame(Character[] board) {
		if (gameType.equals("Tic Tac Toe")) {
			for (int i = 0; i < board.length; i++) {
				if (!board[i].equals('-')) {
					btnArrayTic[i / 3][i % 3].setText(board[i].toString());
					btnArrayTic[i / 3][i % 3].removeActionListener(this);
				}
			}

		} else if (gameType.equals("Catch The Bunny")) {
			for (int i = 0; i < board.length; i++) {
				if (board[i].equals('K')) {
					btnArrayBunny[i / 9][i % 9].setText(board[i].toString());
					// btnArrayBunny[i / 9][i % 9].removeActionListener(this);
					tempK = btnArrayBunny[i / 9][i % 9];

				} else if (board[i].equals('B')) {
					btnArrayBunny[i / 9][i % 9].setText(board[i].toString());
					if (tempB != null) {
						tempB.setText(" ");
					}
				} else {
					btnArrayBunny[i / 9][i % 9].setText(" ");
				}

				// btnArrayBunny[i / 9][i % 9].removeActionListener(this);
				tempB = btnArrayBunny[i / 9][i % 9];

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gameType.equals("Tic Tac Toe")) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (arg0.getSource() == btnArrayTic[i][j]) {
						this.btnArrayTic[i][j].setText("X");
						int index[] = { i, j };
						btnArrayTic[i][j].removeActionListener(this);
						propertyChangeHandler.firePropertyChange("Update-Move", null, index);
					}
				}
			}
		} else if (gameType.equals("Catch The Bunny"))
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (arg0.getSource() == btnArrayBunny[i][j]) {
						int index[] = { i, j };
						this.btnArrayBunny[i][j].setText("K");
						tempK.setText(" ");
						// tempK.addActionListener(this);
						tempK = btnArrayBunny[i][j];
						propertyChangeHandler.firePropertyChange("Update-Move", null, index);
					}
				}
			}

	}

	private void resetBoard() {

		if (gameType == "Tic Tac Toe") {
			for (int i = 0; i < btnArrayTic.length; i++) {
				for (int j = 0; j < btnArrayTic[0].length; j++) {
					btnArrayTic[i][j].setText(" ");
					if (btnArrayTic[i][j].getActionListeners().length == 0) {
						btnArrayTic[i][j].addActionListener(this);
					}
					btnArrayTic[i][j].setSize(50, 50);
					panel_tic.add(btnArrayTic[i][j]);
				}
			}
		}

		else {
			for (int i = 0; i < btnArrayBunny.length; i++) {
				for (int j = 0; j < btnArrayBunny[0].length; j++) {
					btnArrayBunny[i][j].setText(" ");
					// btnArrayBunny[i][j].addActionListener(this);
					btnArrayBunny[i][j].setSize(50, 50);
					panel_bunny.add(btnArrayBunny[i][j]);
				}
			}
		}
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel_tic);

	}
}
