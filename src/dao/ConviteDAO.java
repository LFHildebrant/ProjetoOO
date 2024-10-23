package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aceito;
import entities.Convite;

public class ConviteDAO {
	private Connection conn;
	
	public ConviteDAO(Connection conn) {
		this.conn = conn;
	}
	public void cadastrar(Convite convite) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("insert into convite(login_destinatario,login_remetente,id_compromisso,aceito,id_agenda) values (?,?,?,?,?)");
			st.setString(1, convite.getDestinatario().getLogin());
			st.setString(2, convite.getCompromisso().getAgenda().getUsuario().getLogin());
			st.setInt(3,convite.getCompromisso().getIdCompromisso());
			st.setString(4,convite.getAceito().name());
			st.setInt(5,convite.getCompromisso().getAgenda().getIdAgenda());	
			st.executeUpdate();
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int atualizar(Convite convite) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update convite set aceito = ? where login_remetente = ? and login_destinatario = ? and id_compromisso = ? and id_agenda = ?");
			st.setString(1,convite.getAceito().name());
			st.setString(2,convite.getCompromisso().getAgenda().getUsuario().getLogin());
			st.setString(3,convite.getDestinatario().getLogin());
			st.setInt(4,convite.getCompromisso().getIdCompromisso());
			st.setInt(5,convite.getCompromisso().getAgenda().getIdAgenda());	
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public int excluir(String loginRemetente,String loginDestinatario,int idAgenda,int idCompromisso) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("delete from convite where login_destinatario = ? and login_remetente = ? and id_compromisso = ? and id_agenda = ?");
			st.setString(1, loginDestinatario);
			st.setString(2, loginRemetente);
			st.setInt(3, idCompromisso);
			st.setInt(4, idAgenda);
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public Convite buscar(String loginRemetente,String loginDestinatario,int idCompromisso,int idAgenda) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select * from convite where login_remetente =? and login_destinatario = ? and id_compromisso = ? and id_agenda = ?");
			st.setString(1,loginRemetente);
			st.setString(2,loginDestinatario);
			st.setInt(3,idCompromisso);
			st.setInt(4, idAgenda);
			rs = st.executeQuery();
			if(rs.next()) {
				Convite convite = new Convite();
				convite.setAceito(Aceito.valueOf(rs.getString("aceito")));
				convite.getCompromisso().setIdCompromisso(rs.getInt("id_compromisso"));;
				convite.getCompromisso().getAgenda().getUsuario().setLogin(rs.getString("login_remetente"));
				convite.getDestinatario().setLogin(rs.getString("login_destinatario"));
				convite.getCompromisso().getAgenda().setIdAgenda(rs.getInt("id_agenda"));
				return convite;
			}
			return null;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	public List <Convite> buscarPorRemetente(String loginRemetente) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		List <Convite> convites = new ArrayList <Convite>();
		try {
			st = conn.prepareStatement("select * from convite where login_remetente = ?");
			st.setString(1, loginRemetente);
			rs = st.executeQuery();
			while(rs.next()) {
				Convite convite = new Convite();
				convite.setAceito(Aceito.valueOf(rs.getString("aceito")));
				convite.getCompromisso().setIdCompromisso(rs.getInt("id_compromisso"));;
				convite.getCompromisso().getAgenda().getUsuario().setLogin(rs.getString("login_remetente"));
				convite.getDestinatario().setLogin(rs.getString("login_destinatario"));
				convite.getCompromisso().getAgenda().setIdAgenda(rs.getInt("id_agenda"));
				convites.add(convite);
			}
			return convites;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public List <Convite> buscarPendentesPorDestinatario(String loginDestinatario) throws SQLException,IOException{
		PreparedStatement st  = null;
		ResultSet rs = null;
		List <Convite> convites = new ArrayList <Convite>();
		try {
			st = conn.prepareStatement("select * from convite where login_destinatario = ? and aceito ='PENDENTE'");
			st.setString(1, loginDestinatario);
			rs = st.executeQuery();
			while(rs.next()) {
				Convite convite = new Convite();
				convite.setAceito(Aceito.valueOf(rs.getString("aceito")));
				convite.getCompromisso().setIdCompromisso(rs.getInt("id_compromisso"));;
				convite.getCompromisso().getAgenda().getUsuario().setLogin(rs.getString("login_remetente"));
				convite.getDestinatario().setLogin(rs.getString("login_destinatario"));
				convite.getCompromisso().getAgenda().setIdAgenda(rs.getInt("id_agenda"));
				convites.add(convite);
			}
			return convites;
		}
		finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
}