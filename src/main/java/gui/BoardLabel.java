package gui;

import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BoardLabel extends JLabel {

	private char[][] board;

	private static ImageIcon x = null;
	private static ImageIcon o = null;
	
	private static final String xLocation = "/images/x.gif";
	private static final String oLocation = "/images/o.gif";

	public BoardLabel(char[][] board) {
		loadImages();
		this.board = board;
	}

	public int getCellWidth() {
		return getWidth() / 3;
	}

	public int getCellHeight() {
		return getHeight() / 3;
	}
	
	@SuppressWarnings("rawtypes")
	private void loadImages(){
		java.lang.Class metaObject =  this.getClass();
		URL url = metaObject.getResource(xLocation);
		x = new ImageIcon(url);
		
		url = metaObject.getResource(oLocation);
		o = new ImageIcon(url);
	}


	public void paint(Graphics g) {
		super.paint(g);

		int cellWidth = getWidth() / 3;
		int cellHeight = getHeight() / 3;

		// Linhas Horizontais
		g.drawLine(0, cellHeight, getWidth(), cellHeight);
		g.drawLine(0, 2 * cellHeight, getWidth(), 2 * cellHeight);

		// Linhas Verticais
		g.drawLine(cellWidth, 0, cellWidth, getHeight());
		g.drawLine(2 * cellWidth, 0, 2 * cellWidth, getHeight());

		int upBorder = (cellHeight - x.getIconHeight()) / 2;
		int leftBorder = (cellWidth - x.getIconWidth()) / 2;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 'X')
					g.drawImage(x.getImage(), leftBorder + j * cellWidth,
							upBorder + i * cellHeight, null);

				if (board[i][j] == 'O')
					g.drawImage(o.getImage(), leftBorder + j * cellWidth,
							upBorder + i * cellHeight, null);
			}
		}

	}

}
