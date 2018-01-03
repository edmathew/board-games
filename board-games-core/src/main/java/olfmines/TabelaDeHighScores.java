package olfmines;
import java.io.*;
import java.util.*;



public class TabelaDeHighScores {

	private Jogador[] jogadores = new Jogador[5];
	private int n_players;
	private int indice_ultimo= n_players-1; //Ultima posicao utilizada na matriz


	public void insereNaTabela(Jogador jogador){
		try{
			Scanner ficheiro = new Scanner(new File("records.txt"));
			ficheiro.useLocale(Locale.US);
			this.n_players = ficheiro.nextInt();
			int count = 0;
			while(ficheiro.hasNextLine() && count < n_players){
				Jogador aux = new Jogador(ficheiro);
				jogadores[count] = aux;
				count ++;
			}
			ficheiro.close();
			indice_ultimo = n_players-1;
			insereNaPosicao(jogador);

		}catch (IOException e) {
			jogadores [0] = jogador;
			this.n_players = 1;
		}

		gravaTabela();
	}


	public void gravaTabela() {
		try{
			PrintWriter ficheiro = new PrintWriter(new File("records.txt"));
			ficheiro.println(n_players);
			for(int i = 0; i < n_players;i++){
				jogadores[i].gravaPara(ficheiro);
			}
			ficheiro.close();
		}catch (IOException e) {
			System.out.print("Erro de escrita em ficheiro");
		}

	}



	private void insereNaPosicao(Jogador jogador) {
		if(jogador.getTempo()<jogadores[indice_ultimo].getTempo()){
			int i = 0;
			do{
				Jogador aux = jogadores[indice_ultimo-i];
				jogadores[indice_ultimo-i]=jogador;
				if(indice_ultimo - i < 4){
					jogadores[indice_ultimo-i+1] = aux;
				}
				i++;
			}while(indice_ultimo-i>0 && jogador.getTempo()<jogadores[indice_ultimo-i].getTempo());
			if(n_players < 5){
				n_players++;
			}

		}else if(n_players < 5){
			jogadores[n_players]=jogador;
			n_players++;
		}
	}

	public void mostraLista(){
		System.out.println("Hi-Scores:");
		for(int i = 0; i < n_players && jogadores[i]!= null;i++){
			System.out.println((i+1) +"  "+ jogadores[i].getNome()+"  "
					+ jogadores[i].getTempo());
		}
	}
}