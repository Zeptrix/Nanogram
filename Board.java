import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{

	private int[][] board;
	private boolean win;
	private String[] rowText;
	private String[] columnText;
	private double accuracy;
	public static boolean initialRowPrint;
	public static boolean initialColumnPrint;

	public Board(){
		setPreferredSize(new Dimension(605, 605));
		this.setFocusable(true);
		this.addMouseListener(this);

		board = new int[5][5];
		initializeBoard(board);

		rowText = new String[5];
		columnText = new String[5];
		accuracy = 0;
		initialRowPrint = true;
		initialColumnPrint = true;
	}
	public void initializeBoard(int[][] board) {
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board.length; j++) {
				if(Math.random() < 0.6)
					board[i][j] = 1;
				else
					board[i][j] = -1;
			}
	}
	public void calculateAccuracy(int[][] board) {
		int count1 = 0;
		int count2 = 0;
		
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == 0)
					count1++;
				if(board[i][j] == -2)
					count2++;
			}
		accuracy = (double)count1 / (count1 + count2) * 100;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLUE);

		ImageIcon blueBox;
		ImageIcon redX;
		ImageIcon victoryBox;

		ClassLoader cldr = this.getClass().getClassLoader();
		String imagePath = "images/BlueBox.png";
		URL imageURL = cldr.getResource(imagePath);
		blueBox = new ImageIcon(imageURL);

		imagePath = "images/RedX.png";
		imageURL = cldr.getResource(imagePath);
		redX = new ImageIcon(imageURL);

		imagePath = "images/VictoryBox.png";
		imageURL = cldr.getResource(imagePath);
		victoryBox = new ImageIcon(imageURL);
		
		calculateAccuracy(board);

		drawBoardRow(g, board, rowText);
		drawBoardColumn(g, board, columnText);
		
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.setColor(Color.BLUE);
		g2.drawString("Accuracy: " + String.valueOf((int)accuracy) + "%", 15, 30);

		if(checkWinner()) {
			for(int row = 0; row < board.length; row++) {
				for(int column = 0; column < board[row].length; column++) {
					if(board[row][column] == 0)
						victoryBox.paintIcon(this, g2, column * 100 + 100, row * 100 + 100);
					if(board[row][column] == -2)
						redX.paintIcon(this, g2, column * 100 + 100, row * 100 + 100);
				}
			}
		}
		else {
			for(int row = 0; row < board.length; row++)
				for(int column = 0; column < board[row].length; column++) {
					if(board[row][column] == 0)
						blueBox.paintIcon(this, g2, column * 100 + 100, row * 100 + 100);
					if(board[row][column] == -2)
						redX.paintIcon(this, g2, column * 100 + 100, row * 100 + 100);
				}
		}
		for (int i = 0; i < 6; i++)
			g2.fillRect((i * 100) + 100, 50, 2, 550);

		g2.fillRect(50, 100, 2, 500);

		for (int i = 0; i < 6; i ++)
			g2.fillRect(50, (i * 100) + 100, 550, 2);

		g2.fillRect(100, 50, 500, 2);
	}

	public boolean checkWinner(){
		for(int[] row : board)
			for(int column : row)
				if(column != 0)
					return false;

		return true;
	}

	public static void drawBoardRow(Graphics g, int[][] board, String[] rowText) {
		Graphics2D g1 = (Graphics2D) g;
		String text = "";
		int countIndex = 0;
		int previousFalse = 0;
		int[] counter = new int[3];

		g1.setFont(new Font("Arial", Font.PLAIN, 30));
		g1.setColor(Color.BLUE);

		if(initialRowPrint) {
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(board[i][j] == 1) {
						counter[countIndex]++;
						previousFalse = 0;
					}
					else {
						if(previousFalse == 0)
							countIndex++;

						previousFalse++;
					}
				}

				for(int z = 0; z < counter.length; z++)
					if(counter[z] > 0)
						text += String.valueOf(counter[z]) + " ";

				if(text.equals(""))
					text = "0";

				g1.drawString(text, 55, 275 + (100 * (i - 1)));
				rowText[i] = text;
				text = "";
				countIndex = 0;

				for(int q = 0; q < counter.length; q++)
					counter[q] = 0;
			}

			initialRowPrint = false;
		}
		else {
			for(int i = 0; i < rowText.length; i++)
				g1.drawString(rowText[i], 55, 275 + (100 * (i - 1)));
		}
	}

	public static void drawBoardColumn(Graphics g, int[][] board, String[] columnText) {
		Graphics2D g1 = (Graphics2D) g;
		String text = "";
		int countIndex = 0;
		int previousFalse = 0;
		int[] counter = new int[3];

		g1.setFont(new Font("Arial", Font.PLAIN, 30));
		g1.setColor(Color.BLUE);

		if(initialColumnPrint) {
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board.length; j++) {
					if(board[j][i] == 1) {
						counter[countIndex]++;
						previousFalse = 0;
					}
					else {
						if(previousFalse == 0)
							countIndex++;

						previousFalse++;
					}
				}

				for(int z = 0; z < counter.length; z++)
					if(counter[z] > 0)
						text += String.valueOf(counter[z]) + " ";

				if(text.equals(""))
					text = "0";

				g1.drawString(text, 230 + (100 * (i - 1)), 85);
				columnText[i] = text;
				text = "";
				countIndex = 0;

				for(int q = 0; q < counter.length; q++)
					counter[q] = 0;
			}

			initialColumnPrint = false;
		}
		else {
			for(int i = 0; i < columnText.length; i++)
				g1.drawString(columnText[i], 230 + (100 * (i - 1)), 85);
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		int r = (e.getY() / 100) - 1;
		int c = (e.getX() / 100) - 1;

		if(r >= 0 && r < 5 && c >= 0 && c < 5) {
			if(!checkWinner() && board[r][c] == 1)
				board[r][c] = 0;
			if(!checkWinner() && board[r][c] == -1)
				board[r][c] = -2;
			this.repaint();
		}
	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent e) {

	}
	public void mouseReleased(MouseEvent e) {

	}
}
