package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Personagem;

public class PersonagemDAO {
	private Connection connection;
	
	public PersonagemDAO() {
		connection = new FabricaConexoes().getConnection();
	}
	
	// inserir (C do CRUD)
	public int inserirPersonagem(Personagem personagem) {
		int inseriu = 0;
		String sql = "insert into personagem(nome, sexo, data_nascimento, tipo_sanguineo, peso, altura, ocupacao) values(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, personagem.getNome());
			stmt.setString(2, String.valueOf(personagem.getSexo()));
			stmt.setString(3, personagem.getDataNascimento());
			stmt.setString(4, personagem.getTipoSanguineo());
			stmt.setDouble(5, personagem.getPeso());
			stmt.setDouble(6, personagem.getAltura());
			stmt.setString(7, personagem.getOcupacao());
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	// listar personagem (R do CRUD)
	public Personagem consultarPersonagem(String nome) {
		Personagem personagem = null;
		String sql = "select * from personagem where nome = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				personagem = new Personagem();
				personagem.setNome(rs.getString("nome"));
				personagem.setSexo(rs.getString("sexo").charAt(0));
				personagem.setDataNascimento(rs.getString("data_nascimento"));
				personagem.setTipoSanguineo(rs.getString("tipo_sanguineo"));
				personagem.setPeso(rs.getDouble("peso"));
				personagem.setAltura(rs.getDouble("altura"));
				personagem.setOcupacao(rs.getString("ocupacao"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return personagem;
	}
	
	// listar personagens (R do CRUD)
	public ArrayList<Personagem> getPersonagens() {
		String sql = "select * from personagem";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Personagem> personagens = new ArrayList<>();
			Personagem personagem;
			while (rs.next()) {
				personagem = new Personagem();
				personagem.setNome(rs.getString("nome"));
				personagem.setSexo(rs.getString("sexo").charAt(0));
				personagem.setDataNascimento(rs.getString("data_nascimento"));
				personagem.setTipoSanguineo(rs.getString("tipo_sanguineo"));
				personagem.setPeso(rs.getDouble("peso"));
				personagem.setAltura(rs.getDouble("altura"));
				personagem.setOcupacao(rs.getString("ocupacao"));
				personagens.add(personagem);
			}
			rs.close();
			stmt.close();
			return personagens;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	// alterar (U do CRUD)
	public int alterarPersonagem(Personagem personagem) {
		int alterou = 0;
		String sql = "UPDATE personagem SET sexo = ?, data_nascimento = ?, tipo_sanguineo = ?, peso = ?, altura = ?, ocupacao = ? WHERE nome = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, String.valueOf(personagem.getSexo()));
			stmt.setString(2, personagem.getDataNascimento());
			stmt.setString(3, personagem.getTipoSanguineo());
			stmt.setDouble(4, personagem.getPeso());
			stmt.setDouble(5, personagem.getAltura());
			stmt.setString(6, personagem.getOcupacao());
			stmt.setString(7, personagem.getNome());
			alterou = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alterou;
	}
	
	// remover (D do CRUD)
	public int removerPersonagem(Personagem personagem) {
		int removeu = 0;
		String sql = "DELETE FROM personagem WHERE nome = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, personagem.getNome());
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	// Frase personagem //
	
	// inserir frase personagem	
	public int inserirFrasePersonagem(String nome, String texto, String ocasiao) {
		int inseriu = 0;
		String sql = "insert into frase_personagem(nome_personagem, texto, ocasiao) values(?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setString(2, texto);
			stmt.setString(3, ocasiao);
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	
	// listar frase personagem
	public List<Object> consultarFrasePersonagem(String nome, String texto, String ocasiao) {
		String sql = "select * from frase_personagem where nome_personagem = ? and texto = ? and ocasiao = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setString(2, texto);
			stmt.setString(3, ocasiao);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				nome = rs.getString("nome_personagem");
				texto = rs.getString("texto");
				ocasiao = rs.getString("ocasiao");
				return Arrays.asList(nome, texto, ocasiao);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// listar todas as frases 
	public ArrayList<String> getFrasePersonagem() {
		String sql = "select * from frase_personagem";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> frases = new ArrayList<>();
			while (rs.next()) {
				String nome = rs.getString("nome_personagem");
				String texto = rs.getString("texto");
				String ocasiao = rs.getString("ocasiao");
				frases.add(nome);
				frases.add(texto);
				frases.add(ocasiao);
			}
			rs.close();
			stmt.close();
			return frases;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// remover frase personagem
	public int removerFrasePersonagem(String nome, String texto, String ocasiao) {
		int removeu = 0;
		String sql = "DELETE FROM frase_personagem WHERE nome_personagem = ? and texto = ? and ocasiao = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setString(2, texto);
			stmt.setString(3, ocasiao);
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	// selecionar frases
	public ArrayList<String> selecionaFrases(String nome) {
		String sql = "SELECT texto, ocasiao FROM frase_personagem WHERE nome_personagem = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> frases = new ArrayList<>();
			while (rs.next()) {
				String texto = rs.getString("texto");
				String ocasiao = rs.getString("ocasiao");
				frases.add(texto);
				frases.add(ocasiao);
			}
			rs.close();
			stmt.close();
			return frases;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<String> selecionaNomesFrases() {
	    String sql = "SELECT DISTINCT nome_personagem FROM frase_personagem;";
	    PreparedStatement stmt;
	    try {
	        stmt = connection.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        ArrayList<String> nomes = new ArrayList<>();
	        while (rs.next()) {
	            nomes.add(rs.getString("nome_personagem"));
	        }
	        rs.close();
	        stmt.close();
	        return nomes;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}








