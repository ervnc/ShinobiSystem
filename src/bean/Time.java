package bean;

public class Time {
	// atributos
	private int numero;
	private int quantidadeMembros;
	private String nome;
	private int epCriacao;
	
	// construtores
	public Time(int numero, int quantidadeMembros, String nome, int epCriacao) {
		this.numero = numero;
		this.quantidadeMembros = quantidadeMembros;
		this.nome = nome;
		this.epCriacao = epCriacao;
	}
	
	public Time() {
		
	}
	
	// métodos
	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getQuantidadeMembros() {
		return this.quantidadeMembros;
	}

	public void setQuantidadeMembros(int quantidadeMembros) {
		this.quantidadeMembros = quantidadeMembros;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEpCriacao() {
		return this.epCriacao;
	}

	public void setEpCriacao(int epCriacao) {
		this.epCriacao = epCriacao;
	}

	@Override
	public String toString() {
		return "Time [numero=" + this.numero + ", quantidadeMembros=" + this.quantidadeMembros + ", nome=" + this.nome + ", epCriacao="
				+ this.epCriacao + "]";
	}
	
}
