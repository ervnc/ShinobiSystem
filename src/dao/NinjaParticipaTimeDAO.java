package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.NinjaParticipaTime;

public class NinjaParticipaTimeDAO {
	private Connection connection;

	public NinjaParticipaTimeDAO() {
		connection = new FabricaConexoes().getConnection();
	}

	// inserir (C do CRUD)
	public int inserirNPT(NinjaParticipaTime ninjaParticipaTime) {
		int inseriu = 0;
		String sql = "insert into ninja_participa_time(nome_personagem, numero_time, funcao, ep_ingresso, ep_saida) values(?, ?, ?, ?, ?);";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninjaParticipaTime.getNomePersonagem());
			stmt.setInt(2, ninjaParticipaTime.getNumeroTime());
			stmt.setString(3, ninjaParticipaTime.getFuncao());
			stmt.setInt(4, ninjaParticipaTime.getEpIngresso());
			stmt.setInt(5, ninjaParticipaTime.getEpSaida());
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}

	// listar um (R do CRUD)
	public NinjaParticipaTime consultarNPT(String nomePersonagem, int numeroTime) {
		NinjaParticipaTime ninjaParticipaTime = null;
		String sql = "select * from ninja_participa_time where nome_personagem = ? and numero_time = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, nomePersonagem);
			stmt.setInt(2, numeroTime);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ninjaParticipaTime = new NinjaParticipaTime();
				ninjaParticipaTime.setNomePersonagem(rs.getString("nome_personagem"));
				ninjaParticipaTime.setNumeroTime(rs.getInt("numero_time"));
				ninjaParticipaTime.setFuncao(rs.getString("funcao"));
				ninjaParticipaTime.setEpIngresso(rs.getInt("ep_ingresso"));
				ninjaParticipaTime.setEpSaida(rs.getInt("ep_saida"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ninjaParticipaTime;
	}

	// listar todos (R do CRUD)
	public ArrayList<NinjaParticipaTime> getNPTs() {
		String sql = "select * from ninja_participa_time;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<NinjaParticipaTime> npts = new ArrayList<>();
			NinjaParticipaTime ninjaParticipaTime;
			while (rs.next()) {
				ninjaParticipaTime = new NinjaParticipaTime();
				ninjaParticipaTime.setNomePersonagem(rs.getString("nome_personagem"));
				ninjaParticipaTime.setNumeroTime(rs.getInt("numero_time"));
				ninjaParticipaTime.setFuncao(rs.getString("funcao"));
				ninjaParticipaTime.setEpIngresso(rs.getInt("ep_ingresso"));
				ninjaParticipaTime.setEpSaida(rs.getInt("ep_saida"));
				npts.add(ninjaParticipaTime);
			}
			rs.close();
			stmt.close();
			return npts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// alterar (U do CRUD)
	public int alterarNPT(NinjaParticipaTime ninjaParticipaTime) {
		int alterou = 0;
		String sql = "update ninja_participa_time set funcao = ?, ep_ingresso = ?, ep_saida = ? where nome_personagem = ? and numero_time = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninjaParticipaTime.getFuncao());
			stmt.setInt(2, ninjaParticipaTime.getEpIngresso());
			stmt.setInt(3, ninjaParticipaTime.getEpSaida());
			stmt.setString(4, ninjaParticipaTime.getNomePersonagem());
			stmt.setInt(5, ninjaParticipaTime.getNumeroTime());
			alterou = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alterou;
	}

	// remover (D do CRUD)
	public int removerNPT(NinjaParticipaTime ninjaParticipaTime) {
		int removeu = 0;
		String sql = "delete from ninja_participa_time where nome_personagem = ? and numero_time = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, ninjaParticipaTime.getNomePersonagem());
			stmt.setInt(2, ninjaParticipaTime.getNumeroTime());
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	
	public ArrayList<Integer> selecionaNumeroNPT() {
		String sql = "SELECT distinct numero_time FROM ninja_participa_time";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Integer> numerosNPT = new ArrayList<>();
			while (rs.next()) {
				numerosNPT.add(rs.getInt("numero_time"));
			}
			rs.close();
			stmt.close();
			return numerosNPT;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<String> selecionaNomesNPT() {
		String sql = "SELECT distinct nome_personagem FROM ninja_participa_time";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> nomesNPT = new ArrayList<>();
			while (rs.next()) {
				nomesNPT.add(rs.getString("nome_personagem"));
			}
			rs.close();
			stmt.close();
			return nomesNPT;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
}
