package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Ninja;

public class NinjaDAO {
	private Connection connection;
	
	public NinjaDAO() {
		connection = new FabricaConexoes().getConnection();
	}
	
	// inserir
	public int inserirNinja(Ninja ninja) {
		int inseriu = 0;
		String sql = "INSERT INTO ninja(nome_personagem, patente, titulo) VALUES(?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninja.getNomePersonagem());
			stmt.setString(2, ninja.getPatente());
			stmt.setString(3, ninja.getTitulo());
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	// listar um
	public Ninja consultarNinja(String nomePersonagem) {
		Ninja ninja = null;
		String sql = "SELECT * FROM ninja WHERE nome_personagem = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ninja = new Ninja();
				ninja.setNomePersonagem(rs.getString("nome_personagem"));
				ninja.setPatente(rs.getString("patente"));
				ninja.setTitulo(rs.getString("titulo"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ninja;
	}
	
	// listar todos
	public ArrayList<Ninja> getNinjas() {
		String sql = "SELECT * FROM ninja";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			Ninja ninja;
			ArrayList<Ninja> ninjas = new ArrayList<>();
			while (rs.next()) {
				ninja = new Ninja();
				ninja.setNomePersonagem(rs.getString("nome_personagem"));
				ninja.setPatente(rs.getString("patente"));
				ninja.setTitulo(rs.getString("titulo"));
				ninjas.add(ninja);
			}
			rs.close();
			stmt.close();
			return ninjas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// alterar
	public int alterarNinja(Ninja ninja) {
		int alterou = 0;
		String sql = "UPDATE ninja SET patente = ?, titulo = ? WHERE nome_personagem = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninja.getPatente());
			stmt.setString(2, ninja.getTitulo());
			stmt.setString(3, ninja.getNomePersonagem());
			alterou = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alterou;
	}
	
	// remover
	public int removerNinja(Ninja ninja) {
		int removeu = 0;
		String sql = "DELETE FROM ninja where nome_personagem = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninja.getNomePersonagem());
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}

	// Transformação da natureza //

	// inserir transformação da natureza
	public int inserirTransformacaoNatureza(String nomePersonagem, String transformacaoNatureza) {
		int inseriu = 0;
		String sql = "INSERT INTO transformacao_natureza_ninja(nome_personagem, transformacao_natureza) VALUES(?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			stmt.setString(2, transformacaoNatureza);
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	// listar transformação da natureza
	public List<Object> consultarTransformacaoNatureza(String nomePersonagem, String transformacaoNatureza) {
		String sql = "SELECT * FROM transformacao_natureza_ninja WHERE nome_personagem = ? and transformacao_natureza = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			stmt.setString(2, transformacaoNatureza);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				nomePersonagem = rs.getString("nome_personagem");
				transformacaoNatureza = rs.getString("transformacao_natureza");
				
				return Arrays.asList(nomePersonagem, transformacaoNatureza);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// listar todas as transformações da natureza
	public ArrayList<String> getTransformacaoNatureza() {
		String sql = "SELECT * FROM transformacao_natureza_ninja";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> transformacoesNatureza = new ArrayList<>();
			while (rs.next()) {
				String nomePersonagem = rs.getString("nome_personagem");
				String transformacaoNatureza = rs.getString("transformacao_natureza");
				transformacoesNatureza.add(nomePersonagem);
				transformacoesNatureza.add(transformacaoNatureza);
			}
			rs.close();
			stmt.close();
			return transformacoesNatureza;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// remover transformaçõa da natureza
	public int removerTransformacaoNatureza(String nomePersonagem, String transformacaoNatureza) {
		int removeu = 0;
		String sql = "DELETE FROM transformacao_natureza_ninja where nome_personagem = ? and transformacao_natureza = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			stmt.setString(2, transformacaoNatureza);
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	// selecionar transformações da natureza
	public ArrayList<String> selecionaTransformacoesNatureza(String nomePersonagem) {
		String sql = "SELECT transformacao_natureza from transformacao_natureza_ninja where nome_personagem = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> transformacoesNatureza = new ArrayList<>();
			while (rs.next()) {
				String transformacaoNatureza = rs.getString("transformacao_natureza");
				transformacoesNatureza.add(transformacaoNatureza);
			}
			rs.close();
			stmt.close();
			return transformacoesNatureza;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// selecionar partições
	public ArrayList<Integer> selecionaParticipacoes(String nomeNinja) {
	    String sql = "SELECT numero_time FROM ninja_participa_time WHERE nome_personagem = ?;";
	    PreparedStatement stmt;
	    try {
	        stmt = connection.prepareStatement(sql);
	        stmt.setString(1, nomeNinja);
	        ResultSet rs = stmt.executeQuery();
	        ArrayList<Integer> participacoes = new ArrayList<>();
	        while (rs.next()) {
	            int numeroTime = rs.getInt("numero_time");
	            participacoes.add(numeroTime);
	        }
	        rs.close();
	        stmt.close();
	        return participacoes;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	
	public ArrayList<String> selecionaNomeTransformacoesNatureza() {
		String sql = "SELECT distinct nome_personagem from transformacao_natureza_ninja";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> transformacoesNatureza = new ArrayList<>();
			while (rs.next()) {
				transformacoesNatureza.add(rs.getString("nome_personagem"));
			}
			rs.close();
			stmt.close();
			return transformacoesNatureza;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}








