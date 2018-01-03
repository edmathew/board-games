package ticTacToe;

@SuppressWarnings("serial")
public class IllegalMove extends Exception {

	private String descricao;

	public IllegalMove(String descricao){
		if(descricao == null || descricao.length()== 0)
			throw new IllegalArgumentException();

		this.descricao = descricao;
	}

	public String toString(){
		return descricao;
	}

}
