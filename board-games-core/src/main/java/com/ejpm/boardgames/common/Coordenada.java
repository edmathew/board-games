package com.ejpm.boardgames.common;
import java.io.PrintWriter;

public class Coordenada {

	private int linha;
	private int coluna;
	private boolean flag;
	
	public Coordenada(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		flag = false;
	}
	
	public Coordenada(int linha, int coluna, boolean flag) {
		this.linha = linha;
		this.coluna = coluna;
		this.flag = flag;
	}
	
	public int getLinha(){
		return linha;
	}
	
	public int getColuna(){
		return coluna;
	}
	public boolean getFlag(){
		return flag;
	}

	public void gravaPara(PrintWriter ficheiro) {
		ficheiro.println(this.linha + " " + this.coluna);
		
	}
}
