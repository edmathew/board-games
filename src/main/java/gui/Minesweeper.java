package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Minesweeper {

	private JFrame window;
	private BoardLabel boardLabel;

	public Minesweeper() {
		window = new JFrame("Minesweeper");
		window.setSize(new Dimension(200, 300));
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void execute() {
		window.setVisible(true);
	}

	public static void main(String[] args) {
		new Minesweeper().execute();
	}

}
