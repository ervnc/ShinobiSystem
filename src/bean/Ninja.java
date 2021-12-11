package bean;

public class Ninja extends Personagem {
	// atributos
	private String nomePersonagem;
	private String patente;
	private String titulo;

	// construtores
	public Ninja(String nomePersonagem, String patente, String titulo) {
		this.nomePersonagem = nomePersonagem;
		this.patente = patente;
		this.titulo = titulo;
	}

	public Ninja() {

	}

	// métodos
	public String getNomePersonagem() {
		return nomePersonagem;
	}

	public void setNomePersonagem(String nomePersonagem) {
		this.nomePersonagem = nomePersonagem;
	}
	
	public String getPatente() {
		return this.patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Ninja [patente=" + patente + ", titulo=" + titulo + "]";
	}
}
