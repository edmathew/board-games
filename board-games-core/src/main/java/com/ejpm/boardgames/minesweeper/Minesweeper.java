package com.ejpm.boardgames.minesweeper;
import java.io.*;
import java.util.*;
import com.ejpm.boardgames.common.Coordenada;
import com.ejpm.boardgames.common.Coordinate;
import olfmines.Jogador;

public class Minesweeper {

    private static final int DEFAULT_DIMENTION = 9;
    private static final int DEFAULT_MINES = 10;

	public static final char OCULTO = '-';
	public static final char BANDEIRA = 'F';
	public static final char BANDEIRA_E_BOMBA = 'B';
	public static final char BOMBA = '*';

        protected MinesweeperBoard board;
	protected char[][] tabuleiro = null;
 	protected int contadorBandeiras;
    
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
    
 
	/**
	 * Contrutor de um tabuleiro tendo por base
	 * um ficheiro
	 */
	public Minesweeper(final String nome_ficheiro) {
		assert nome_ficheiro != null;

		try {
			final Scanner ficheiro = new Scanner(new File(nome_ficheiro));
			final int dim_x = ficheiro.nextInt();
			final int dim_y = ficheiro.nextInt();

			this.tabuleiro = new char [dim_x][dim_y];

			final int n_bombas = ficheiro.nextInt();
			int count = 0;

			while (ficheiro.hasNextInt() && count <= n_bombas){
				final int coordx = ficheiro.nextInt();
				final int coordy = ficheiro.nextInt();
				tabuleiro [coordx][coordy]=BOMBA;
				count ++;
			}

			for(int i = 0; i < tabuleiro.length; i++){
				for(int j = 0; j < tabuleiro[i].length; j++){
					if(tabuleiro[i][j]!= BOMBA)
						tabuleiro [i][j] = OCULTO;
				}
			}


		} catch (IOException e) {
                    System.err.println("Outstanding error - TO be refactored");
		}
	}






 


	public void mostrarTabuleiro() {
		for(int x= 0;x<=tabuleiro[0].length;x++){ // Componente Horizontal das coordenadas
			if(x==0){
				System.out.print("   ");
			}else if(x<9){
				System.out.print(x+"  ");
			}else if(x>=9){
				System.out.print(x+" ");
			}
		}
		System.out.println();

		for(int i=0, y=(int)'A'; i < tabuleiro.length; i++){
			//Componente vertical das coordenadas

			System.out.print((char)y+"  ");
			for(int j=0; j < tabuleiro[i].length; j++){
				if(tabuleiro[i][j] == BOMBA){
					System.out.print(OCULTO+ "  ");
				}else if(tabuleiro[i][j]== BANDEIRA_E_BOMBA){
					System.out.print(BANDEIRA+ "  ");
				}else{
					System.out.print(tabuleiro[i][j]+ "  ");
				}
			}System.out.println();
			y++;
		}
		System.out.println("Bandeiras Colocadas: "+ board.getFlagsCount());

	}

	public void revelaTabuleiro(){
		for(int x= 0;x<=tabuleiro[0].length;x++){ // Componente Horizontal das coordenadas
			if(x==0){
				System.out.print("   ");
			}else if(x<9){
				System.out.print(x+"  ");
			}else if(x>=9){
				System.out.print(x+" ");
			}
		}
		System.out.println();

		for(int i=0, y=(int)'A'; i < tabuleiro.length; i++){
			//Componente vertical das coordenadas

			System.out.print((char)y+"  ");
			for(int j=0; j < tabuleiro[i].length; j++){
				if (tabuleiro[i][j] == BANDEIRA_E_BOMBA){
					System.out.print(BOMBA+ "  ");
				}else{
					System.out.print(tabuleiro[i][j]+"  ");
				}
			}
			System.out.println();
			y++;
		}
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
	public Coordenada pedeJogada() {
		Coordenada coord = null;
		int l;
		int c;
		do{

			System.out.println("Jogada?");

			final String aux = getMoveFromKeyboard();

			if(aux.charAt(0)=='#'){
				gravaJogo();
			}else if(aux.length() <2 || aux.equals("")){
				System.out.println("Jogada Inv lida");
				coord = null;

			}else if(aux.charAt(0)=='+'){

				final String coord3 = aux.substring(2);

				l = (int)coord3.toUpperCase().charAt(0) - (int)'A';

				final String t = coord3.substring(1).trim().toUpperCase();

				c = coord3.substring(1).trim().length();
				if (c == 1)
					c = (int)t.charAt(0) - (int)'0'-1;
				else {
					c =  (int) t.charAt(0) - (int)'0';
					c = c* 10;
					final int j = (int)t.charAt(1)-(int)'0';
					c = c+j-1;
				}

				coord = new Coordenada(l,c);

				if(!dentroDoTabuleiro(coord)||!coordenadaValida(coord)){
					System.out.println("Jogada Inv lida");
				}else{
					contadorBandeiras ++;
					selecciona(l,c,true);
					coord=null;
				}

			}else{
				l = (int)aux.toUpperCase().charAt(0) - (int)'A';

				final String t = aux.substring(1).trim().toUpperCase();

				c = aux.substring(1).trim().length();
				if(c==1)
					c = (int)t.charAt(0) - (int)'0'-1;
				else{
					c = (int)t.charAt(0)-(int)'0';
					c = c*10;
					final int j = (int)t.charAt(1)-(int)'0';
					c = c+j-1;
				}

				coord = new Coordenada(l ,c);

				if(!dentroDoTabuleiro(coord)||!coordenadaValida(coord)){
					System.out.println("Jogada Inv lida");
				}else if(tabuleiro[l][c]==BANDEIRA || 
						tabuleiro[l][c] == BANDEIRA_E_BOMBA){

					char escolha;
					do{
						System.out.print("Quer mesmo jogar na coordenada? (Y/N) "
								+((char)((int)l+ 'A'))+" "+(c+1)+" (est  assinalada com uma bandeira)");
						escolha = getMoveFromKeyboard().toUpperCase().charAt(0);
					}while(escolha != 'N' && escolha != 'Y');

					if(escolha == 'Y'){
						contadorBandeiras --;
					}
					if (escolha == 'N'){
						mostrarTabuleiro();
						coord = null;
					}
				}
			}
		}while(coord==null || !dentroDoTabuleiro(coord)||!coordenadaValida(coord));
                
                System.out.println("coordinate: " + coord.getLinha() + " - " + coord.getColuna());
                
		return coord;

	}	

	/**
	 *Selecciona uma posi  o com uma bandeira
	 *@pre dentroDoTabuleiro(new Coordenada(x,y)) && coordenadaValida(new Coordenada(x,y) 
	 */
	public void selecciona(final int x, final int y, final boolean bandeira){
		assert dentroDoTabuleiro(new Coordenada(x,y));
		assert coordenadaValida(new Coordenada(x,y));

		if(tabuleiro [x][y] == BANDEIRA){
			tabuleiro[x][y]=OCULTO;
		}else if(tabuleiro [x][y] == BOMBA){
			tabuleiro[x][y]=BANDEIRA_E_BOMBA;
		}else if(tabuleiro[x][y]==OCULTO){
			tabuleiro [x][y] = BANDEIRA;
		}else if (tabuleiro [x][y]==BANDEIRA_E_BOMBA ){
			tabuleiro[x][y]=BOMBA;
		}


		mostrarTabuleiro();
	}

	/**
	 * Verifica se a coordenada dada est  dentro dos limites
	 * do tabuleiro em uso
	 * 
	 * @pre c!=null
	 */
	public boolean dentroDoTabuleiro(final Coordenada c) {
		assert c !=null;

		if(c.getLinha()>=tabuleiro.length||c.getLinha()<0||
				c.getColuna()>=tabuleiro[0].length||c.getColuna()<0){
			return false;	
		}
		return true;
	}


	/**
	 * Verifica se a coordenada   valida para ser jogada
	 * 
	 */
	private boolean coordenadaValida(Coordenada coordenada){
		if(tabuleiro[coordenada.getLinha()][coordenada.getColuna()]!= OCULTO &&
				tabuleiro[coordenada.getLinha()][coordenada.getColuna()]!= BANDEIRA_E_BOMBA &&
				tabuleiro[coordenada.getLinha()][coordenada.getColuna()]!= BOMBA&&
				tabuleiro[coordenada.getLinha()][coordenada.getColuna()]!= BANDEIRA)
			return false;
		else
			return true;
	}



	/**
	 * 
	 */
	public void revela(final Coordenada coord) {

		assert dentroDoTabuleiro(coord);
		int n_bombas = nBombas(coord.getLinha(), coord.getColuna());


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


	/**
	 * 
	 * Devolve o numero de bombas em volta de uma coordenada
	 * 
	 * @pre tabuleiro[x][y] != BOMBA && dentroDoTabuleiro(coordenada_dada);
	 * @post cumpreInvariante();
	 */
	public int nBombas(int x, int y) {
		int n_bombas = 0;

		for(int a = x-1; (a<=(x+1))&& a <=tabuleiro.length; a++){
			for(int b = y-1; (b<=(y+1))&& b <= tabuleiro[1].length; b++){

				if(dentroDoTabuleiro(new Coordenada (a,b))){

					if(tabuleiro [a][b] == BOMBA || tabuleiro[a][b]== BANDEIRA_E_BOMBA)
						n_bombas = n_bombas +1;
				}
			}
		}

		return n_bombas;

	}

	/**
	 * Verifica se o jogo j  acabou
	 * 
	 */
	public boolean jogoTerminado(final Coordenada coordenada) {
		if(board.cellIsBomb(new Coordinate(coordenada.getLinha(), coordenada.getColuna()))){
			return true;
		}else if(!jogoContinua()){
			return true;
		}else{
			return false;
		}
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

	/**
	 * Grava as posi oes das bombas para retomar
	 * o mesmo tabuleiro mais tarde
	 * (Foi adoptada esta estrategia com base no que nos foi transmitido
	 * pelo professor Abilio.)
	 * 
	 */
	public void gravaJogo(){

		int n_bombas = 0;

		for(int i = 0; i < tabuleiro.length; i ++){
			for(int j = 0 ; j < tabuleiro[i].length; j ++){
				if (tabuleiro[i][j] == BOMBA)
					n_bombas ++;
			}
		}

		final Coordenada []memoria = new Coordenada [n_bombas];

		for(int i = 0, count = 0; i < tabuleiro.length; i ++){
			for(int j = 0; j < tabuleiro[i].length; j ++){
				if(tabuleiro [i][j]==BOMBA){
					memoria [count]= new Coordenada(i,j);
					count ++;
				}
			}
		}

		try {
			final PrintWriter ficheiro = new PrintWriter(new File("save.txt"));
			ficheiro.println(tabuleiro.length+" "+tabuleiro[0].length);
			ficheiro.println(n_bombas);
			for(int i = 0; i < memoria.length;i++){
				memoria[i].gravaPara(ficheiro);
			}
			ficheiro.close();
			System.out.println("O jogo foi gravado em save.txt");

		} catch (FileNotFoundException e) {
			System.out.println("Erro de escrita no ficheiro");
		}
	}




 
 
 


	//Programa MinesSweeper
	public static void main(final String[] args) {

		System.out.println(
				"_______________________________________________________\n"+
				"|.....................................................|\n"+
				"|...000.......000..00..000.......00..000000..000000...|\n"+
				"|...0000.....0000..00..0000......00..000000..000000...|\n"+
				"|...00.00...00.00..00..00.00.....00..00......00.......|\n"+
				"|...00..00.00..00..00..00..00....00..0000....000000...|\n"+
				"|...00...000...00..00..00...00...00..0000....000000...|\n"+
				"|...00.........00..00..00....00..00..00..........00...|\n"+
				"|...00.........00..00..00.....00.00..00..........00...|\n"+
				"|...00.........00..00..00......0000..000000..000000...|\n"+
				"|___00_________00__00__00_______000__000000__000000___|\n"+
				":::::::::::::::::::::::::::::::::::::::::::::::::::::::\n"+
				"                                                       ");



		Minesweeper jogo = null;

		int escolha;
		do{
			final Scanner teclado = new Scanner (System.in);
			System.out.println("Main Menu:" +
					"\n1 - Usar tabuleiro Standard (9x9 com 10 minas)" +
					"\n2 - Personalizar o tabuleiro" +
					"\n3 - Carregar um ficheiro com a disposi  o do tabuleiro" +
			"\n4 - Sair");

			escolha = teclado.nextInt();


			switch (escolha){
			case 1:
				jogo = new Minesweeper();
				break;
			case 2:
				System.out.print("Altura? ");
				final int x = teclado.nextInt();

				System.out.print("Largura? ");
				final int y = teclado.nextInt();

				System.out.print("N  de bombas? ");
				final int n_bombas = teclado.nextInt();

				jogo = new Minesweeper(x,y,n_bombas);
				break;
			case 3:
				System.out.print("Nome do ficheiro(.txt): ");
				final String ficheiro = teclado.next();
				jogo = new Minesweeper(ficheiro);
				break;
			case 4:
				return;
			default:
				System.out.println("Op  o Inv lida.");
			break;
			}

		}while(escolha > 4 || escolha < 1);

		Scanner teclado = new Scanner (System.in);
		jogo.mostrarTabuleiro();
		final Date inicio = new Date();

		Coordenada jogada;

		do{
			jogada = jogo.pedeJogada();
			if(jogo.getBoard().cellIsBomb(new Coordinate(jogada.getLinha(), jogada.getColuna()))){
				System.out.println("GAME OVER");
				jogo.revelaTabuleiro();

			}else{
				jogo.revela(jogada);
				jogo.mostrarTabuleiro();
			}
			if(!jogo.jogoContinua()){
				System.out.println("Ganhou.");
				final Date fim = new Date();
				double tempo = (fim.getTime() - inicio.getTime())/1000;


				System.out.println("Terminou em "+tempo+ " s");
				jogo.revelaTabuleiro();

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