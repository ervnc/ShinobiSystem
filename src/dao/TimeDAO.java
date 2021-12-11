package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Time;

public class TimeDAO {
	private Connection connection;
	
	public TimeDAO() {
		connection = new FabricaConexoes().getConnection();
	}
	
	// inserir (C do CRUD)
	public int inserirTime(Time time) {
		int inseriu = 0;
		String sql = "INSERT INTO time(numero, quantidade_membros, nome, ep_criacao) VALUES(?, ?, ?, ?)";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, time.getNumero());
			stmt.setInt(2, time.getQuantidadeMembros());
			stmt.setString(3, time.getNome());
			stmt.setInt(4, time.getEpCriacao());
			inseriu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inseriu;
	}
	
	// listar um (R do CRUD)
		public Time consultarTime(int numero) {
			Time time = null;
			String sql = "SELECT * FROM time WHERE numero = ?";
			PreparedStatement stmt;
			try {
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, numero);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					time = new Time();
					time.setNumero(rs.getInt("numero"));
					time.setQuantidadeMembros(rs.getInt("quantidade_membros"));
					time.setNome(rs.getString("nome"));
					time.setEpCriacao(rs.getInt("ep_criacao"));
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return time;
		}
	
	// listar todos (R do CRUD)
	public ArrayList<Time> getTimes() {
		String sql = "SELECT * FROM time";
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
	
	// alterar (U do CRUD)
	public int alterarTime(Time time) {
		int alterou = 0;
		String sql = "UPDATE time SET quantidade_membros = ?, nome = ?, ep_criacao = ? WHERE numero = ?";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, time.getQuantidadeMembros());
			stmt.setString(2, time.getNome());
			stmt.setInt(3, time.getEpCriacao());
			stmt.setInt(4, time.getNumero());
			alterou = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alterou;
	}
	
	// remover (D do CRUD)
	public int removerTime(Time time) {
		int removeu = 0;
		String sql = "DELETE FROM time WHERE numero = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, time.getNumero());
			removeu = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return removeu;
	}
	
}
