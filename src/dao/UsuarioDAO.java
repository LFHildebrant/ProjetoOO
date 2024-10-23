package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Usuario;

public class UsuarioDAO {
	private  Connection conn;
	
	public UsuarioDAO(Connection conn){
		this.conn = conn;
	}
	
	public  void cadastrar(Usuario usuario) throws SQLException{
		PreparedStatement st = null;
		try {
			st= conn.prepareStatement("insert into usuario(login,foto_pessoal,email,senha,genero,data_nascimento,nome_completo) values(?,?,?,?,?,?,?)");
			st.setString(1,usuario.getLogin());
			st.setString(2, usuario.getFotoPessoal());
			st.setString(3, usuario.getEmail());
			st.setString(4, usuario.getSenha());
			st.setString(5,usuario.getGenero());
			st.setDate(6,usuario.getDataNascimento());
			st.setString(7, usuario.getNomeCompleto());
			st.executeUpdate();
		}
		finally {	
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}		
	}
	public  int excluir(String login) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("delete from usuario where login = ?");
			st.setString(1,login);
			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		} 
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public  int atualizar(Usuario usuario) throws SQLException{
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update 	usuario set foto_pessoal = ?, email = ?,senha = ?,genero = ?,data_nascimento = ?,nome_completo = ? where login = ?");
			st.setString(1,usuario.getFotoPessoal());
			st.setString(2,usuario.getEmail());
			st.setString(3,usuario.getSenha());
			st.setString(4,usuario.getGenero());
			st.setDate(5,usuario.getDataNascimento());
			st.setString(6,usuario.getNomeCompleto());
			st.setString(7,usuario.getLogin());

			int linhasManipuladas = st.executeUpdate();
			return linhasManipuladas;
		}
		finally {
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	public Usuario buscarPorLogin(String login) throws SQLException,IOException{
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select * from usuario where login = ?");
			st.setString(1, login);
			rs = st.executeQuery();
			if(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("login"));
				usuario.setDataNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setFotoPessoal(rs.getString("foto_pessoal"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setGenero(rs.getString("genero"));
				return usuario;
			}
			return null;
		}finally {
			BancoDados.finalizarResultSet(rs);
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public List<Usuario> buscarTodos() throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from usuario order by login");

			rs = st.executeQuery();

			List<Usuario> listaUsuarios = new ArrayList<Usuario>();

			while (rs.next()) {

				Usuario usuario = new Usuario();

				usuario.setLogin(rs.getString("login"));
				usuario.setDataNascimento(rs.getDate("data_nascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setFotoPessoal(rs.getString("foto_pessoal"));
				usuario.setNomeCompleto(rs.getString("nome_completo"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setGenero(rs.getString("genero"));

				listaUsuarios.add(usuario);
			}

			return listaUsuarios;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
}
