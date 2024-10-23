package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Compromisso;
import entities.Usuario;

public class CompromissoDAO {
	private Connection conn;
	
	public CompromissoDAO(Connection conn) {
		this.conn = conn;
	}
	public void cadastrar(Compromisso compromisso) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into compromisso(local,titulo,descricao,data_inicio,data_termino,horario_inicio,horario_termino,id_agenda,login_usuario,momento_notificacao) values(?,?,?,?,?,?,?,?,?,?)");
			st.setString(1,compromisso.getLocal());
			st.setString(2,compromisso.getTitulo());
			st.setString(3,compromisso.getDescricao());
			st.setDate(4,compromisso.getDataInicio());
			st.setDate(5,compromisso.getDataTermino());
			st.setTime(6,compromisso.getHoraInicio());
			st.setTime(7,compromisso.getHoraTermino());
			st.setInt(8,compromisso.getAgenda().getIdAgenda());
			st.setString(9, compromisso.getAgenda().getUsuario().getLogin());
			st.setTimestamp(10,compromisso.getMomentoNotificacao());
			st.executeUpdate();
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int excluir(int idCompromisso,String loginUsuario,int idAgenda) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("delete from compromisso where id_compromisso = ? and id_agenda = ? and login_usuario = ?");
			st.setInt(1, idCompromisso);
			st.setInt(2, idAgenda);
			st.setString(3, loginUsuario);
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int atualizar(Compromisso compromisso) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update compromisso set  local =?,titulo=?,descricao=?,data_inicio=?,data_termino=?,horario_inicio=?,horario_termino=?,momento_notificacao=? where login_usuario =? and id_agenda = ? and id_compromisso = ?");
			st.setString(1,compromisso.getLocal());
			st.setString(2,compromisso.getTitulo());
			st.setString(3,compromisso.getDescricao());
			st.setDate(4,compromisso.getDataInicio());
			st.setDate(5,compromisso.getDataTermino());
			st.setTime(6,compromisso.getHoraInicio());
			st.setTime(7,compromisso.getHoraTermino());
			st.setTimestamp(8,compromisso.getMomentoNotificacao());
			st.setString(9, compromisso.getAgenda().getUsuario().getLogin());
			st.setInt(10,compromisso.getAgenda().getIdAgenda());
			st.setInt(11,compromisso.getIdCompromisso());
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public List <Usuario> buscarUsuariosPorCompromisso(int idCompromisso) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		List <Usuario> usuarios = new ArrayList <Usuario>();
		try {
			st = conn.prepareStatement("select distinct login_usuario from compromisso where id_compromisso = ? order by login_usuario");
			st.setInt(1,idCompromisso);
			rs= st.executeQuery();
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("login_usuario"));
				usuarios.add(usuario);
			}
			return usuarios;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public List <Compromisso> buscarPorAgenda(String loginUsuario,int idAgenda) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		List <Compromisso> compromissos = new ArrayList <Compromisso>();
		try {
			st = conn.prepareStatement("select * from compromisso where id_agenda =? and login_usuario = ?");
			st.setInt(1, idAgenda);
			st.setString(2, loginUsuario);
			rs= st.executeQuery();
			while(rs.next()) {
				Compromisso compromisso = new Compromisso();
				compromisso.setIdCompromisso(rs.getInt("id_compromisso"));
				compromisso.setLocal(rs.getString("local"));
				compromisso.setDataInicio(rs.getDate("data_inicio"));
				compromisso.setDataTermino(rs.getDate("data_termino"));
				compromisso.setHoraInicio(rs.getTime("horario_inicio"));
				compromisso.setHoraTermino(rs.getTime("horario_termino"));
				compromisso.setDescricao(rs.getString("descricao"));	
				compromisso.setTitulo(rs.getString("titulo"));
				compromisso.getAgenda().setIdAgenda(rs.getInt("id_agenda"));
				compromisso.getAgenda().getUsuario().setLogin(rs.getString("login_usuario"));
				compromisso.setMomentoNotificacao(rs.getTimestamp("momento_notificacao"));
				compromissos.add(compromisso);
			}
			return compromissos;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
