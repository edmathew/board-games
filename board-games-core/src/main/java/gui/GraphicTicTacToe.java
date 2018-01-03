package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ticTacToe.*;

public class GraphicTicTacToe extends TicTacToe {

	private final static String HELP_URL = "http://en.wikipedia.org/wiki/Tic-tac-toe";
	private JFrame frame;
	private BoardLabel boardLabel;
	private JLabel playerLabel;
	private ClickListener listener = new ClickListener();

	public GraphicTicTacToe() {
		frame = new JFrame("Tic Tac Toe");
		frame.setSize(new Dimension(400, 350));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setJMenuBar(menuBar());

		boardLabel = new BoardLabel(getBoard());
		boardLabel.addMouseListener(listener);

		playerLabel = new JLabel(getPlayerToPlay());

		JPanel p = new JPanel();
		p.add(new JLabel("Next Player:  "));
		p.add(playerLabel);

		frame.add(boardLabel, BorderLayout.CENTER);
		frame.add(p, BorderLayout.SOUTH);
	}

	public void execute() {
		frame.setVisible(true);
	}

	private void updatePlayerLabel() {
		playerLabel.setText(getPlayerToPlay());
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu game = new JMenu("Game");
		JMenu help = new JMenu("Help");

		final JMenuItem newGame = new JMenuItem("New");

		game.add(newGame);

		final JMenuItem rules = new JMenuItem("Rules on Web");
		final JMenuItem about = new JMenuItem("About");
		help.add(rules);
		help.add(about);

		class LocalListener implements ActionListener {

			public void actionPerformed(ActionEvent event) {
				JMenuItem item = (JMenuItem) event.getSource();
				if (item == newGame) {
					GraphicTicTacToe.this.eraseBoard();
					GraphicTicTacToe.this.randomPlayerToStart();
					updatePlayerLabel();
					frame.repaint();
				} else if (item == rules) {
					try {
						Runtime.getRuntime().exec(
								"rundll32 url.dll,FileProtocolHandler "
										+ HELP_URL);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (item == about) {
					JOptionPane
							.showMessageDialog(
									null,
									"<html>Developed by Edgar Mateus<hr><i><center>2009/2010</html>",
									"About", JOptionPane.NO_OPTION);
				}
			}

		}

		LocalListener sentinela = new LocalListener();
		newGame.addActionListener(sentinela);
		rules.addActionListener(sentinela);
		about.addActionListener(sentinela);

		menuBar.add(game);
		menuBar.add(help);

		return menuBar;
	}

	private class ClickListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			if (!endGame()) {
				int x = e.getX();
				int y = e.getY();
				int aux1, aux2;

				BoardLabel label = (BoardLabel) e.getSource();
				int cellWidth = label.getCellWidth();
				int cellHeight = label.getCellHeight();

				if (y < cellHeight)
					aux1 = 0;
				else if (y > 2 * cellHeight)
					aux1 = 2;
				else
					aux1 = 1;

				if (x < cellWidth)
					aux2 = 0;
				else if (x > 2 * cellWidth)
					aux2 = 2;
				else
					aux2 = 1;

				try {
					play(new Coordinate(aux1, aux2));
				} catch (IllegalMove e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

				updatePlayerLabel();
				frame.repaint();

				if (existsWinner()) {
					JOptionPane.showMessageDialog(null, "Vencedor: "
							+ getWinner());
					playerLabel.setText("");
				} else if (endGame()) {
					JOptionPane.showMessageDialog(null, "Empate");
					playerLabel.setText("");
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	public static void main(String[] args) {
		GraphicTicTacToe g = new GraphicTicTacToe();
		g.execute();
	}
}
