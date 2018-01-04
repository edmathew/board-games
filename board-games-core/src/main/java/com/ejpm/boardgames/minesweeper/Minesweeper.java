package com.ejpm.boardgames.minesweeper;
import java.io.*;
import java.util.*;
import com.ejpm.boardgames.common.Coordenada;
import olfmines.Jogador;

public class Minesweeper {
    
    private static final int DEFAULT_DIMENTION  = 9;
    private static final int DEFAULT_MINES      = 10;
    
	public static final char OCULTO = '-';
	public static final char BANDEIRA = 'F';
	public static final char BANDEIRA_E_BOMBA = 'B';
	public static final char BOMBA = '*';

        protected MinesweeperBoard board;
	protected char[][] tabuleiro = null;
 	protected int contadorBandeiras=0;


	public Minesweeper(){
            board = new MinesweeperBoard(DEFAULT_DIMENTION, DEFAULT_DIMENTION, DEFAULT_MINES);
		this.tabuleiro = new char [9][9];
		preencher(10);
	}
        
	/**
	 * Contrutor de um tabuleiro tendo por base
	 * um ficheiro
	 */
	public Minesweeper(String nome_ficheiro) {
		assert nome_ficheiro != null;

		try {
			Scanner ficheiro = new Scanner(new File(nome_ficheiro));
			int dim_x = ficheiro.nextInt();
			int dim_y = ficheiro.nextInt();

			this.tabuleiro = new char [dim_x][dim_y];

			int n_bombas = ficheiro.nextInt();
			int count = 0;

			while (ficheiro.hasNextInt() && count <= n_bombas){
				int coordx = ficheiro.nextInt();
				int coordy = ficheiro.nextInt();
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
			System.out.println("Erro de leitura no ficheiro." +
			"\nO jogo vai continuar com as defini  es standard.\n");

			this.tabuleiro = new char [9][9];
			preencher(10);
		}
	}





	/**
	 * Contrutor de um tabuleiro com parametros
	 * definidos pelo utilizador
	 * 
	 * @pre tamanho_x > 0 && tamanho_y > 0 &&  
	 * numero_de_minas <= (tamanho_x * tamanho_y)
	 */
	public Minesweeper(int tamanho_x, int tamanho_y, int numero_de_minas){
		assert tamanho_x > 0;
		assert tamanho_y > 0;
		assert numero_de_minas <= (tamanho_x * tamanho_y);


		this.tabuleiro = new char [tamanho_x][tamanho_y];
		preencher(numero_de_minas);
	}


	/**
	 * Prenche o tabuleiro com nbombas e o restante com OCULTO
	 * 
	 * @pre n_bombas >=0
	 * @post cumpreInvariante();
	 * 
	 */
	private void preencher(int n_bombas) {
		assert n_bombas >=0;

		// "Limpar" o tabuleiro,
		for(int i=0; i<tabuleiro.length;i++){
			for(int j=0; j<tabuleiro[i].length;j++){
				tabuleiro[i][j]= OCULTO ;
			}
		}
		// Preencher o n mero adequado de bombas 
		preencherBombas(n_bombas);

		assert cumpreInvariante();

	}

	/**
	 * Preenche o tabuleiro com o n mero de bombas dado como argumento
	 * em posi oes aleatorias
	 * 
	 */
	private void preencherBombas(int n_bombas) {
		assert n_bombas >=0;
		Random gerador = new Random();

		for(int i=0;i<n_bombas;i++){
			int x = gerador.nextInt(tabuleiro.length);
			int y = gerador.nextInt(tabuleiro[0].length); 
			if (tabuleiro[x][y]==BOMBA){
				i--;
			}else{
				tabuleiro[x][y] = BOMBA;
			}
		}
	}


	void mostrarTabuleiro() {
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
		System.out.println("Bandeiras Colocadas: "+this.contadorBandeiras);

	}

	void revelaTabuleiro(){
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

			Scanner teclado = new Scanner(System.in);
			String aux = teclado.nextLine().trim();

			if(aux.charAt(0)=='#'){
				gravaJogo();
			}else if(aux.length() <2 || aux.equals("")){
				System.out.println("Jogada Inv lida");
				coord = null;

			}else if(aux.charAt(0)=='+'){

				String coord3 = aux.substring(2);

				l = (int)coord3.toUpperCase().charAt(0) - (int)'A';

				String t = coord3.substring(1).trim().toUpperCase();

				c = coord3.substring(1).trim().length();
				if (c == 1)
					c = (int)t.charAt(0) - (int)'0'-1;
				else {
					c =  (int) t.charAt(0) - (int)'0';
					c = c* 10;
					int j = (int)t.charAt(1)-(int)'0';
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

				String t = aux.substring(1).trim().toUpperCase();

				c = aux.substring(1).trim().length();
				if(c==1)
					c = (int)t.charAt(0) - (int)'0'-1;
				else{
					c = (int)t.charAt(0)-(int)'0';
					c = c*10;
					int j = (int)t.charAt(1)-(int)'0';
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
						escolha = teclado.next().toUpperCase().charAt(0);
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

		assert cumpreInvariante();
		return coord;

	}	

	/**
	 *Selecciona uma posi  o com uma bandeira
	 *@pre dentroDoTabuleiro(new Coordenada(x,y)) && coordenadaValida(new Coordenada(x,y) 
	 *@post cumpreInvariante();
	 */
	public void selecciona(int x, int y, boolean bandeira){
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

		assert cumpreInvariante();
		mostrarTabuleiro();
	}

	/**
	 * Verifica se a coordenada dada est  dentro dos limites
	 * do tabuleiro em uso
	 * 
	 * @pre c!=null
	 */
	public boolean dentroDoTabuleiro(Coordenada c) {
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
	 * @pre cumpreInvariante() && dentroDoTabuleiro(coord)
	 * @post cumpreInvariante()
	 */
	public void revela(Coordenada coord) {

		assert dentroDoTabuleiro(coord);
		assert cumpreInvariante();
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
		assert cumpreInvariante();	
	}


	/**
	 * 
	 * Devolve o numero de bombas em volta de uma coordenada
	 * 
	 * @pre tabuleiro[x][y] != BOMBA && dentroDoTabuleiro(coordenada_dada);
	 * @post cumpreInvariante();
	 */
	public int nBombas(int x, int y) {
		Coordenada coordenada_dada = new Coordenada (x,y);
		assert tabuleiro[x][y] != BOMBA;
		assert dentroDoTabuleiro(coordenada_dada);

		int n_bombas = 0;

		for(int a = x-1; (a<=(x+1))&& a <=tabuleiro.length; a++){
			for(int b = y-1; (b<=(y+1))&& b <= tabuleiro[1].length; b++){

				if(dentroDoTabuleiro(new Coordenada (a,b))){

					if(tabuleiro [a][b] == BOMBA || tabuleiro[a][b]== BANDEIRA_E_BOMBA)
						n_bombas = n_bombas +1;
				}
			}
		}

		assert cumpreInvariante();
		return n_bombas;

	}


	public boolean inspectorDeBomba(Coordenada jogada) {
		if(tabuleiro[jogada.getLinha()][jogada.getColuna()]!=BOMBA)
			return false;
		else
			return true;
	}

	/**
	 * Verifica se o jogo j  acabou
	 * 
	 */
	public boolean jogoTerminado(Coordenada coordenada) {
		if(inspectorDeBomba(coordenada)){
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

		Coordenada []memoria = new Coordenada [n_bombas];

		for(int i = 0, count = 0; i < tabuleiro.length; i ++){
			for(int j = 0; j < tabuleiro[i].length; j ++){
				if(tabuleiro [i][j]==BOMBA){
					memoria [count]= new Coordenada(i,j);
					count ++;
				}
			}
		}

		try {
			PrintWriter ficheiro = new PrintWriter(new File("save.txt"));
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



	public boolean cumpreInvariante() {
		if(tabuleiro == null){ 
			return false;
		}else if(procurarNull()){
			return false;
		}else if (detectarErros()){
			return false;
		}else if (!linhasPerfeitas()){
			return false;
		}else{
			return true;
		}
	}


	public boolean procurarNull(){
		for(int i = 0; i < tabuleiro.length; i++){
			if(tabuleiro[i]== null)
				return true;
		}
		return false;
	}


	public boolean detectarErros(){
		assert tabuleiro != null;

		for(int i=0; i<tabuleiro.length; i++){
			for(int j = 0; j<tabuleiro[i].length; j++){
				if(tabuleiro[i][j] == OCULTO || tabuleiro[i][j] == BANDEIRA ||
						tabuleiro[i][j] == BANDEIRA_E_BOMBA || tabuleiro[i][j] == BOMBA){

				}else if(tabuleiro[i][j]< (char)48 || tabuleiro[i][j] > (char)56){
					return true;
				}
			}
		}
		return false;
	}


	public boolean linhasPerfeitas(){
		int n_colunas = tabuleiro[0].length;

		for(int i = 0; i < tabuleiro.length;i++){
			if(tabuleiro[i].length != n_colunas)
				return false;
		}

		return true;
	}



	//Programa MinesSweeper
	public static void main(String[] args) {

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
			Scanner teclado = new Scanner (System.in);
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
				int x = teclado.nextInt();

				System.out.print("Largura? ");
				int y = teclado.nextInt();

				System.out.print("N  de bombas? ");
				int n_bombas = teclado.nextInt();

				jogo = new Minesweeper(x,y,n_bombas);
				break;
			case 3:
				System.out.print("Nome do ficheiro(.txt): ");
				String ficheiro = teclado.next();
				jogo = new Minesweeper(ficheiro);
				break;
			case 4:
				System.exit(0);
				break;
			default:
				System.out.println("Op  o Inv lida.");
			break;
			}

		}while(escolha > 4 || escolha < 1);

		Scanner teclado = new Scanner (System.in);
		jogo.mostrarTabuleiro();
		Date inicio = new Date();

		Coordenada jogada;

		do{
			jogada = jogo.pedeJogada();
			if(jogo.inspectorDeBomba(jogada)){
				System.out.println("GAME OVER");
				jogo.revelaTabuleiro();

			}else{
				jogo.revela(jogada);
				jogo.mostrarTabuleiro();
			}
			if(!jogo.jogoContinua()){
				System.out.println("Ganhou.");
				Date fim = new Date();
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

				Jogador player = new Jogador (nome,tempo);
				player.gravaRecord(player);
			}

		}while(!jogo.jogoTerminado(jogada));
	}
}