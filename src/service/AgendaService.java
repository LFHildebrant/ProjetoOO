package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.AgendaDAO;
import dao.BancoDados;
import entities.Agenda;
import entities.Compromisso;
import entities.Usuario;

public class AgendaService {

	
	public AgendaService () {
		
	}
	
	public void cadastrar(Agenda agenda) throws IOException, SQLException {
		Connection conn = BancoDados.conectar();
		new AgendaDAO(conn).cadastrar(agenda);
	}
	
	public List<Agenda> listarTodos (String login) throws SQLException, IOException{
		Connection conn = BancoDados.conectar();
		return new AgendaDAO(conn).buscarPorLogin(login);
	}
	
	public Agenda listarPorId(int idAgenda, String login) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new AgendaDAO(conn).buscar(idAgenda, login);
	}
	
	public void editar(Agenda agenda) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new AgendaDAO(conn).atualizar(agenda);
	}
	
	public void excluir (int idAgenda, String loginUsuario) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaDAO(conn).excluir(idAgenda, loginUsuario);
	}
}
