package olfmines;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Jogador {

	private String nome;
	private double tempo;

	public Jogador(String nome, double tempo){
		this.nome = nome;
		this.tempo = tempo;
	}

	public double getTempo(){
		return tempo;
	}

	public String getNome(){
		return nome;
	}

	public Jogador(Scanner ficheiro){
		tempo = ficheiro.useLocale(Locale.US).nextDouble();
		nome = ficheiro.nextLine().trim();
	}

	public void gravaPara(PrintWriter ficheiro) {
			ficheiro.println(this.tempo + " " +this.nome);
	}
	
	public void gravaRecord(Jogador jogador){
		TabelaDeHighScores tabela = new TabelaDeHighScores();
		tabela.insereNaTabela(jogador);	
		tabela.mostraLista();
	}

}
