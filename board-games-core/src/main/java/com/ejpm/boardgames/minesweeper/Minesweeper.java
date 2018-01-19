package com.ejpm.boardgames.minesweeper;
import java.util.*;
import com.ejpm.boardgames.common.Coordenada;
import com.ejpm.boardgames.common.Coordinate;
import com.ejpm.boardgames.common.console.ConsoleInputException;
import com.ejpm.boardgames.common.console.ConsoleOutputGridDecorator;
import com.ejpm.boardgames.minesweeper.console.MinesweeperCMD;
import com.ejpm.boardgames.minesweeper.console.MinesweeperConsoleBoardDisplay;
import olfmines.Jogador;

public class Minesweeper {

    private static final int DEFAULT_DIMENTION = 9;
    private static final int DEFAULT_MINES = 10;

	public static final char OCULTO = '-';
	public static final char BANDEIRA = 'F';
	public static final char BANDEIRA_E_BOMBA = 'B';
	public static final char BOMBA = '*';

        protected MinesweeperBoard board;
	protected char[][] tabuleiro;
    
    public Minesweeper() {
        this(DEFAULT_DIMENTION, DEFAULT_DIMENTION, DEFAULT_MINES);
    }

    /**
     *
     * @param boardWidth The board width
     * @param boardHeight The board height
     * @param minesQtd The quantity of bombs to be in the board.
     */
    public Minesweeper(final int boardWidth, final int boardHeight, final int minesQtd) {
        board = new MinesweeperBoard(boardWidth, boardHeight, minesQtd);
        board.spreadBombs();
        tabuleiro = board.getTabuleiro();
    }
    
    public Minesweeper(final MinesweeperBoard board) {
        this.board = board;
        tabuleiro = board.getTabuleiro();
    }

    public MinesweeperBoard getBoard() {
        return board;
    }
    
    public String getConsoleOutput(){
        return new ConsoleOutputGridDecorator(board.getWidth()).apply(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, false));
    }
    
    public String getRevealedConsoleOutput(){
        return new ConsoleOutputGridDecorator(board.getWidth()).apply(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true));
    }

    public String getMoveFromKeyboard() {
        return new Scanner(System.in).nextLine().trim();
    }

	/**
	 * Pede uma jogada ao utilizador e verifica se   uma coloca  o de bandeira,
	 * um pedido de grava  o de jogo ou uma jogada normal.
	 * 
	 * @post cumpreInvariante()
	 */
	public Coordenada pedeJogada(final String aux) {
		Coordenada coord = null;
			if(aux.charAt(0)=='#'){
				System.out.println("Not implemented yet");
			}else if(aux.length() <2){
				System.out.println("Jogada Inv lida");
				coord = null;
			}else if(aux.charAt(0)=='+'){
				markFlag(getCoordinateFromString(aux.substring(2)));
                                System.out.println(getConsoleOutput());
			}else{
				final Coordinate coordinate = getCoordinateFromString(aux);
                                 coord = new Coordenada(coordinate.getLine() ,coordinate.getColumn());
			}
                
		return coord;

	}	

    public void markFlag(final Coordinate coordinate) {
        if (board.insideTheBoard(coordinate)) {
            board.toggleFlag(coordinate);
        }
    }
    

    public Coordinate getCoordinateFromString(final String coord3) {
        final int l = (int) coord3.toUpperCase().charAt(0) - (int) 'A';
        final String t = coord3.substring(1).trim().toUpperCase();
        int c = coord3.substring(1).trim().length();
        if (c == 1) {
            c = (int) t.charAt(0) - (int) '0' - 1;
        } else {
            c = (int) t.charAt(0) - (int) '0';
            c = c * 10;
            final int j = (int) t.charAt(1) - (int) '0';
            c = c + j - 1;
        }
        return new Coordinate(l, c);
    }
    

	/**
	 * Verifica se a coordenada dada est  dentro dos limites
	 * do tabuleiro em uso
	 * 
	 * @pre c!=null
	 */
	public boolean dentroDoTabuleiro(final Coordenada c) {
		return board.insideTheBoard(new Coordinate(c.getLinha(), c.getColuna()));
	}

	/**
	 * 
	 */
	public void revela(final Coordenada coord) {
            final int n_bombas = board.getBombsArround(new Coordinate(coord.getLinha(), coord.getColuna()));


		tabuleiro[coord.getLinha()][coord.getColuna()] = (char)('0'+n_bombas);
		if(n_bombas ==0){
			for(int i = coord.getLinha()-1;i<=coord.getLinha()+1;i++){
				for(int j = coord.getColuna()-1; j <= coord.getColuna() +1;j++){
					if (dentroDoTabuleiro(new Coordenada(i, j))){
						if (tabuleiro[i][j] == OCULTO || tabuleiro[i][j] == BANDEIRA||
								tabuleiro[i][j] == BANDEIRA_E_BOMBA)
							revela (new Coordenada (i,j));
					}
				}
			}

		}
	}


	public boolean jogoTerminado(final Coordenada coordenada) {
		if(board.isBomb(new Coordinate(coordenada.getLinha(), coordenada.getColuna()))){
			return true;
		}
                
                return !jogoContinua() ;
	}


	/**
	 * Verifica se ainda ha posi oes por revelar
	 * @return
	 */
	public boolean jogoContinua(){
		for(int i = 0; i < tabuleiro.length; i++){
			for(int j = 0; j < tabuleiro[i].length; j ++){
				if (tabuleiro[i][j]==OCULTO)
					return true;
			}
		}

		return false;
	}




	public static void main(final String[] args) throws ConsoleInputException {
            play(new MinesweeperCMD().generateGame(args));
	}

    public static void play(final Minesweeper jogo) {
        Scanner teclado = new Scanner (System.in);
        System.out.println(jogo.getConsoleOutput());
        final Date inicio = new Date();
        
        Coordenada jogada;
        
        do{
            jogada = jogo.pedeJogada(jogo.getMoveFromKeyboard());
            if(jogo.getBoard().isBomb(new Coordinate(jogada.getLinha(), jogada.getColuna()))){
                System.out.println("GAME OVER");
                System.out.println(jogo.getRevealedConsoleOutput());
                
            }else{
                jogo.revela(jogada);
                System.out.println(jogo.getConsoleOutput());
            }
            if(!jogo.jogoContinua()){
                System.out.println("Ganhou.");
                final Date fim = new Date();
                final double tempo = (fim.getTime() - inicio.getTime())/1000;
                
                
                System.out.println("Terminou em "+tempo+ " s");
                System.out.println(jogo.getRevealedConsoleOutput());

                
                String nome;
                do{
                    System.out.print("Introduza o seu nome: ");
                    nome = teclado.nextLine();
                    
                    if(nome.length()<1)
                        System.out.print("Erro 1");
                    
                }while(nome.length()<1);
                
                final Jogador player = new Jogador (nome,tempo);
                player.gravaRecord(player);
            }
            
        }while(!jogo.jogoTerminado(jogada));
    }
}