package bean;

public class Personagem {
	// atributos
	protected String nome;
	protected char sexo;
	protected String dataNascimento;
	protected String tipoSanguineo;
	protected double peso;
	protected double altura;
	protected String ocupacao;
	
	// construtores
	public Personagem(String nome, char sexo, String dataNascimento, String tipoSanguineo, double peso, double altura,
			String ocupacao) {
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.tipoSanguineo = tipoSanguineo;
		this.peso = peso;
		this.altura = altura;
		this.ocupacao = ocupacao;
	}
	
	public Personagem() {
		
	}
	
	// métodos
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public char getSexo() {
		return this.sexo;
	}
	
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	
	public String getDataNascimento() {
		return this.dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getTipoSanguineo() {
		return this.tipoSanguineo;
	}
	
	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}
	
	public double getPeso() {
		return this.peso;
	}
	
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public double getAltura() {
		return this.altura;
	}
	
	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	public String getOcupacao() {
		return this.ocupacao;
	}
	
	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}

	@Override
	public String toString() {
		return "Personagem [nome=" + this.nome + ", sexo=" + this.sexo + ", dataNascimento=" + this.dataNascimento + ", tipoSanguineo="
				+ this.tipoSanguineo + ", peso=" + this.peso + ", altura=" + this.altura + ", ocupacao=" + this.ocupacao + "]";
	}
	
}
