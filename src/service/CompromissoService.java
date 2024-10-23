package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.CompromissoDAO;
import entities.Compromisso;

public class CompromissoService {
	
	public CompromissoService () {
		
	}
	
	public void cadastrarCompromisso(Compromisso compromisso) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).cadastrar(compromisso);
	}
	
	public void atualizarCompromisso(Compromisso compromisso) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).atualizar(compromisso);
	}
	
	public void excluirCompromisso(int idCompromisso,String loginUsuario,int idAgenda) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new CompromissoDAO(conn).excluir(idCompromisso, loginUsuario, idAgenda);
	}
	
	public Compromisso buscarCompromisso(int idCompromisso) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		//return new CompromissoDAO(conn).buscarCompromisso(idCompromisso);
		return null;
	}
	public List<Compromisso> buscarTodosCompromissos(String loginUsuario,int idAgenda) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		return new CompromissoDAO(conn).buscarPorAgenda(loginUsuario,idAgenda);
	}
	
}
