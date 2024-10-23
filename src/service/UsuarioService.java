package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;

public class UsuarioService {
	
	
	public UsuarioService() {
		
	}
	
	public void cadastrar(Usuario usuario) throws SQLException, IOException {
		Connection conn = new BancoDados().conectar();
		new UsuarioDAO(conn).cadastrar(usuario);
	}
	
	public Usuario buscarPorLogin(String login) throws SQLException, IOException{
		Connection conn = new BancoDados().conectar();
		return new UsuarioDAO(conn).buscarPorLogin(login);
	}
	
	public List<Usuario> buscarTodosUsuarios() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		List <Usuario> alunos = new UsuarioDAO(conn).buscarTodos();
		
		return alunos;
	}
	
	public void editar(Usuario usuario) throws SQLException, IOException{
		Connection conn = new BancoDados().conectar();
		new UsuarioDAO(conn).atualizar(usuario);
	}
	
	public int excluir (String nomeUsuario) throws SQLException, IOException {
		Connection conn = new BancoDados().conectar();
		return new UsuarioDAO(conn).excluir(nomeUsuario);
	}
	
	
}


