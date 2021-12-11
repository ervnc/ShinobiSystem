package bean;

public class Missao {
	// atributos
	private int numeroTime;
	private String objetivo;
	private int epInicio;
	private int epFim;
	private char ranking;
	private String tipo;
	private String resultado;
	
	// construtores
	public Missao(int numeroTime, String objetivo, int epInicio, int epFim, char ranking, String tipo, String resultado) {
		this.numeroTime = numeroTime;
		this.objetivo = objetivo;
		this.epInicio = epInicio;
		this.epFim = epFim;
		this.ranking = ranking;
		this.tipo = tipo;
		this.resultado = resultado;
	}
	
	public Missao() {
		
	}
	
	// métodos
	public int getNumeroTime() {
		return this.numeroTime;
	}

	public void setNumeroTime(int numeroTime) {
		this.numeroTime = numeroTime;
	}

	public String getObjetivo() {
		return this.objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public int getEpInicio() {
		return this.epInicio;
	}

	public void setEpInicio(int epInicio) {
		this.epInicio = epInicio;
	}

	public int getEpFim() {
		return this.epFim;
	}

	public void setEpFim(int epFim) {
		this.epFim = epFim;
	}

	public char getRanking() {
		return this.ranking;
	}

	public void setRanking(char ranking) {
		this.ranking = ranking;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "Missao [numeroTime=" + this.numeroTime + ", objetivo=" + this.objetivo + ", epInicio=" + this.epInicio + ", epFim="
				+ this.epFim + ", ranking=" + this.ranking + ", tipo=" + this.tipo + ", resultado=" + this.resultado + "]";
	}
	
}
