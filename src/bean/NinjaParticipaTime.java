package bean;

public class NinjaParticipaTime {

	// atributos
	private String nomePersonagem;
	private int numeroTime;
	private String funcao;
	private int epIngresso;
	private int epSaida;

	// construtores
	public NinjaParticipaTime(String nomePersonagem, int numeroTime, String funcao, int epIngresso, int epSaida) {
		this.nomePersonagem = nomePersonagem;
		this.numeroTime = numeroTime;
		this.funcao = funcao;
		this.epIngresso = epIngresso;
		this.epSaida = epSaida;
	}

	public NinjaParticipaTime() {

	}

	// métodos
	public String getNomePersonagem() {
		return this.nomePersonagem;
	}

	public void setNomePersonagem(String nomePersonagem) {
		this.nomePersonagem = nomePersonagem;
	}

	public int getNumeroTime() {
		return this.numeroTime;
	}

	public void setNumeroTime(int numeroTime) {
		this.numeroTime = numeroTime;
	}

	public String getFuncao() {
		return this.funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public int getEpIngresso() {
		return this.epIngresso;
	}

	public void setEpIngresso(int epIngresso) {
		this.epIngresso = epIngresso;
	}

	public int getEpSaida() {
		return this.epSaida;
	}

	public void setEpSaida(int epSaida) {
		this.epSaida = epSaida;
	}

	@Override
	public String toString() {
		return "NinjaParticipaTime [nomePersonagem=" + this.nomePersonagem + ", numeroTime=" + this.numeroTime
				+ ", funcao=" + this.funcao + ", epIngresso=" + this.epIngresso + ", epSaida=" + this.epSaida + "]";
	}

}
