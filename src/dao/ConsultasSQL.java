package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Missao;
import bean.Ninja;
import bean.Time;

public class ConsultasSQL {
	private Connection connection;
	
	public ConsultasSQL() {
		connection = new FabricaConexoes().getConnection();
	}
	
	// selecionar ninjas por patente
	public ArrayList<Ninja> ninjasPatente(String patente) {
		String sql = "select * from ninja where patente = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, patente);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Ninja> ninjas = new ArrayList<>();
			Ninja ninja;
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

	// selecionar missões por tipo
	public ArrayList<Missao> missoesTipo(String tipo) {
		String sql = "select * from missao where tipo = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tipo);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Missao> missoes = new ArrayList<>();
			Missao missao;
			while (rs.next()) {
				missao = new Missao();
				missao.setNumeroTime(rs.getInt("numero_time"));
				missao.setObjetivo(rs.getString("objetivo"));
				missao.setEpInicio(rs.getInt("ep_inicio"));
				missao.setEpFim(rs.getInt("ep_fim"));
				missao.setTipo(rs.getString("tipo"));
				if (rs.getString("ranking") == null) {
	                missao.setRanking(Character.MIN_VALUE);
	            } else {
	                missao.setRanking(rs.getString("ranking").charAt(0));
	            }
				missao.setResultado(rs.getString("resultado"));
				missoes.add(missao);
			}
			rs.close();
			stmt.close();
			return missoes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// selecionar missões que estão em um determinado intervalo de episódios
	public ArrayList<Missao> missoesEpisodios(int epInicio, int epFim) {
		String sql = "select * from missao where ep_inicio > ? and ep_fim < ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, epInicio);
			stmt.setInt(2, epFim);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Missao> missoes = new ArrayList<>();
			Missao missao;
			while (rs.next()) {
				missao = new Missao();
				missao.setNumeroTime(rs.getInt("numero_time"));
				missao.setObjetivo(rs.getString("objetivo"));
				missao.setEpInicio(rs.getInt("ep_inicio"));
				missao.setEpFim(rs.getInt("ep_fim"));
				missao.setTipo(rs.getString("tipo"));
				if (rs.getString("ranking") == null) {
	                missao.setRanking(Character.MIN_VALUE);
	            } else {
	                missao.setRanking(rs.getString("ranking").charAt(0));
	            }
				missao.setResultado(rs.getString("resultado"));
				missoes.add(missao);
			}
			rs.close();
			stmt.close();
			return missoes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// selecionar time e seu número de missões de sucesso
	public int sucessosTime(int numero) {
		int sucessos = 0;
		String sql = "select count(resultado) from missao where numero_time = ? and resultado like 'sucesso' group by numero_time;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numero);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				sucessos = rs.getInt("count(resultado)");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sucessos;
	}
	
	// selecionar estatísticas dos personagens cadastrados
	public ArrayList<Double> estatisticasPersonagens() {
		double mediaPeso = 0;
		double mediaAltura = 0;
		double alturaMaxima = 0;
		double alturaMinima = 0;
		double pesoMaximo = 0;
		double pesoMinimo = 0;
		String sql = "select avg(peso), avg(altura), max(altura), min(altura), max(peso), min(peso) from personagem;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Double> estatisticas = new ArrayList<>();
			while (rs.next()) {
				mediaPeso = rs.getDouble("avg(peso)");
				mediaAltura = rs.getDouble("avg(altura)");
				alturaMaxima = rs.getDouble("max(altura)");
				alturaMinima = rs.getDouble("min(altura)");
				pesoMaximo = rs.getDouble("max(peso)");
				pesoMinimo = rs.getDouble("min(peso)");
				estatisticas.add(mediaPeso);
				estatisticas.add(mediaAltura);
				estatisticas.add(alturaMaxima);
				estatisticas.add(alturaMinima);
				estatisticas.add(pesoMaximo);
				estatisticas.add(pesoMinimo);
			}
			rs.close();
			stmt.close();
			return estatisticas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// selecionar times por episódio de criação
	public ArrayList<Time> timesCriacao() {
		String sql = "select * from time order by ep_criacao;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Time> times = new ArrayList<>();
			Time time;
			while (rs.next()) {
				time = new Time();
				time.setNumero(rs.getInt("numero"));
				time.setQuantidadeMembros(rs.getInt("quantidade_membros"));
				time.setNome(rs.getString("nome"));
				time.setEpCriacao(rs.getInt("ep_criacao"));
				times.add(time);
			}
			rs.close();
			stmt.close();
			return times;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
