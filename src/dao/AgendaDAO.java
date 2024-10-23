package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Agenda;

public class AgendaDAO {
	private Connection conn;
	
	public AgendaDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void cadastrar(Agenda agenda) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into agenda(nome,descricao,login_usuario) values(?,?,?)");
			st.setString(1, agenda.getNome());
			st.setString(2, agenda.getDescricao());
			st.setString(3,agenda.getUsuario().getLogin());
			st.executeUpdate();
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int excluir(int idAgenda,String loginUsuario) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("delete from agenda where id_agenda = ? and login_usuario = ?");
			st.setInt(1, idAgenda);
			st.setString(2, loginUsuario);
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int atualizar(Agenda agenda) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update agenda set nome = ?, descricao = ?,login_usuario = ? where id_agenda = ?");
			st.setString(1,agenda.getNome());
			st.setString(2, agenda.getDescricao());
			st.setString(3,agenda.getUsuario().getLogin());
			st.setInt(4,agenda.getIdAgenda());

			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public Agenda buscar(int idAgenda,String loginUsuario) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select * from agenda where id_agenda = ? and login_usuario  = ?");
			st.setInt(1, idAgenda);
			st.setString(2, loginUsuario);
			rs = st.executeQuery();
			if(rs.next()) {
				Agenda agenda = new Agenda();
				agenda.setIdAgenda(rs.getInt("id_agenda"));
				agenda.getUsuario().setLogin(rs.getString("login_usuario"));
				agenda.setNome(rs.getString("nome"));
				agenda.setDescricao(rs.getString("descricao"));
				return agenda;
			}
			return  null;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public List <Agenda> buscarPorLogin(String login) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;	
		List <Agenda> agendas = new ArrayList <Agenda>();
		try {
			st = conn.prepareStatement("select * from agenda where login_usuario = ? order by nome");
			st.setString(1, login);
			rs = st.executeQuery();
			while(rs.next()) {
				Agenda agenda = new Agenda();
				agenda.setIdAgenda(rs.getInt("id_agenda"));
				agenda.getUsuario().setLogin(rs.getString("login_usuario"));
				agenda.setNome(rs.getString("nome"));
				agenda.setDescricao(rs.getString("descricao"));
				agendas.add(agenda);
			}
			return agendas;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
