package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bean.Missao;

public class MissaoDAO {
	private Connection connection;
	
	public MissaoDAO() {
		connection = new FabricaConexoes().getConnection();
	}
	
	// inserir (C do CRUD)
	public int inserirMissao(Missao missao) {
		int inseriu = 0;
		String sql = "INSERT INTO missao(numero_time, objetivo, ep_inicio, ep_fim, ranking, tipo, resultado) VALUES(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, missao.getNumeroTime());
			stmt.setString(2, missao.getObjetivo());
			stmt.setInt(3, missao.getEpInicio());
			stmt.setInt(4, missao.getEpFim());
			stmt.setString(5, String.valueOf(missao.getRanking()));
			stmt.setString(6, missao.getTipo());
			stmt.setString(7, missao.getResultado());
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	// listar um (R do CRUD)
	public Missao consultarMissao(int numero_time, String objetivo, int ep_inicio, int ep_fim) {
		Missao missao = null;
		String sql = "SELECT * FROM missao WHERE numero_time = ? and objetivo = ? and ep_inicio = ? and ep_fim = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numero_time);
			stmt.setString(2, objetivo);
			stmt.setInt(3, ep_inicio);
			stmt.setInt(4, ep_fim);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				missao = new Missao();
				missao.setNumeroTime(rs.getInt("numero_time"));
				missao.setObjetivo(rs.getString("objetivo"));
				missao.setEpInicio(rs.getInt("ep_inicio"));
				missao.setEpFim(rs.getInt("ep_fim"));
				missao.setRanking(rs.getString("ranking").charAt(0));
				missao.setTipo(rs.getString("tipo"));
				missao.setResultado(rs.getString("resultado"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return missao;
	}
	
	public ArrayList<Missao> selecionaMissoes(int numero_time) {
        String sql = "select * from missao where numero_time = ?;";
        PreparedStatement stmt;
        Missao missao;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, numero_time);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Missao> missoes = new ArrayList<>();
            while (rs.next()) {
                    missao = new Missao();
                    missao.setNumeroTime(rs.getInt("numero_time"));
                    missao.setObjetivo(rs.getString("objetivo"));
                    missao.setEpInicio(rs.getInt("ep_inicio"));
                    missao.setEpFim(rs.getInt("ep_fim"));
                    missao.setRanking(rs.getString("ranking").charAt(0));
                    missao.setTipo(rs.getString("tipo"));
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
	
	// listar todos (R do CRUD)
	public ArrayList<Missao> getMissoes() {
		String sql = "SELECT * FROM missao";
		PreparedStatement stmt;
		Missao missao;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Missao> missoes = new ArrayList<>();
			while (rs.next()) {
				missao = new Missao();
				missao.setNumeroTime(rs.getInt("numero_time"));
				missao.setObjetivo(rs.getString("objetivo"));
				missao.setEpInicio(rs.getInt("ep_inicio"));
				missao.setEpFim(rs.getInt("ep_fim"));
				if (rs.getString("ranking") == null) {
					missao.setRanking(Character.MIN_VALUE);
				} else {
					missao.setRanking(rs.getString("ranking").charAt(0));
				}
				missao.setTipo(rs.getString("tipo"));
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
	
	// alterar (U do CRUD)
	public int alterarMissao(Missao missao) {
		int alterou = 0;
		String sql = "UPDATE missao SET ranking = ?, tipo = ?, resultado = ? WHERE numero_time = ? and objetivo = ? and ep_inicio = ? and ep_fim = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, String.valueOf(missao.getRanking()));
			stmt.setString(2, missao.getTipo());
			stmt.setString(3, missao.getResultado());
			stmt.setInt(4, missao.getNumeroTime());
			stmt.setString(5, missao.getObjetivo());
			stmt.setInt(6, missao.getEpInicio());
			stmt.setInt(7, missao.getEpFim());
			alterou = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alterou;
	}
	
	// remover (D do CRUD)
	public int removerMissao(Missao missao) {
		int removeu = 0;
		String sql = "DELETE FROM missao WHERE numero_time = ? and objetivo = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, missao.getNumeroTime());
			stmt.setString(2, missao.getObjetivo());
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	
	
	
	
	public int inserirObstaculo(int numeroTime, String objetivoMissao, int epInicioMissao, int epFimMissao, String obstaculo) {
		int inseriu = 0;
		String sql = "INSERT INTO obstaculo_missao(numero_time, objetivo_missao, ep_inicio_missao, ep_fim_missao, obstaculo) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numeroTime);
			stmt.setString(2, objetivoMissao);
			stmt.setInt(3, epInicioMissao);
			stmt.setInt(4, epFimMissao);
			stmt.setString(5, obstaculo);
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	
	
	
	public List<Object> consultarObstaculo(int numeroTime, String objetivoMissao, int epInicioMissao, int epFimMissao, String obstaculo) {
		String sql = "SELECT * FROM obstaculo_missao WHERE numero_time = ? and objetivo_missao = ? and ep_inicio_missao = ? and ep_fim_missao = ? and obstaculo = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numeroTime);
			stmt.setString(2, objetivoMissao);
			stmt.setInt(3, epInicioMissao);
			stmt.setInt(4, epFimMissao);
			stmt.setString(5, obstaculo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				numeroTime = rs.getInt("numero_time");
				objetivoMissao = rs.getString("objetivo_missao");
				epInicioMissao = rs.getInt("ep_inicio_missao");
				epFimMissao = rs.getInt("ep_fim_missao");
				obstaculo = rs.getString("obstaculo");
				return Arrays.asList(numeroTime, objetivoMissao, epInicioMissao, epFimMissao, obstaculo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Object> getObstaculos() {
		String sql = "SELECT * FROM obstaculo_missao";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Object> obstaculos = new ArrayList<>();
			while (rs.next()) {
				int numeroTime = rs.getInt("numero_time");
				String objetivoMissao = rs.getString("objetivo_missao");
				int epInicioMissao = rs.getInt("ep_inicio_missao");
				int epFimMissao = rs.getInt("ep_fim_missao");
				String obstaculo = rs.getString("obstaculo");
				obstaculos.add(numeroTime);
				obstaculos.add(objetivoMissao);
				obstaculos.add(epInicioMissao);
				obstaculos.add(epFimMissao);
				obstaculos.add(obstaculo);
			}
			rs.close();
			stmt.close();
			return obstaculos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int removerObstaculo(int numeroTime, String objetivoMissao, int epInicioMissao, int epFimMissao, String obstaculo) {
		int removeu = 0;
		String sql = "DELETE FROM obstaculo_missao WHERE numero_time = ? and objetivo_missao = ? and ep_inicio_missao = ? and ep_fim_missao = ? and obstaculo = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numeroTime);
			stmt.setString(2, objetivoMissao);
			stmt.setInt(3, epInicioMissao);
			stmt.setInt(4, epFimMissao);
			stmt.setString(5, obstaculo);
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
	
	
	public ArrayList<String> selecionaObstaculos(int numeroTime, String objetivoMissao, int epInicioMissao, int epFimMissao) {
		String sql = "SELECT obstaculo FROM obstaculo_missao WHERE numero_time = ? and objetivo_missao = ? and ep_inicio_missao = ? and ep_fim_missao = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, numeroTime);
			stmt.setString(2, objetivoMissao);
			stmt.setInt(3, epInicioMissao);
			stmt.setInt(4, epFimMissao);
			ResultSet rs = stmt.executeQuery();
			ArrayList<String> obstaculos = new ArrayList<>();
			if (rs.next()) {
				String obstaculo = rs.getString("obstaculo");
				obstaculos.add(obstaculo);
			}
			rs.close();
			stmt.close();
			return obstaculos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<Integer> selecionaNumeroMissao() {
		String sql = "SELECT distinct numero_time FROM missao";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Integer> numerosMissoes = new ArrayList<>();
			while (rs.next()) {
				numerosMissoes.add(rs.getInt("numero_time"));
			}
			rs.close();
			stmt.close();
			return numerosMissoes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ArrayList<Integer> selecionaNumeroObstaculo() {
		String sql = "SELECT distinct numero_time FROM obstaculo_missao";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Integer> numerosObstaculos = new ArrayList<>();
			while (rs.next()) {
				numerosObstaculos.add(rs.getInt("numero_time"));
			}
			rs.close();
			stmt.close();
			return numerosObstaculos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
