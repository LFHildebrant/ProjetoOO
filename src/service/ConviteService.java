package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.ConviteDAO;
import entities.Convite;

public class ConviteService {
	
	public ConviteService() {
		
	}
	
	public void cadastrarConvite(Convite convite) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).cadastrar(convite);
	}
	
	public void atualizarConvite(Convite convite) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		new ConviteDAO(conn).atualizar(convite);
	}

	public Convite buscarConvite(String loginRemetente,String loginDestinatario,int idCompromisso) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		//return new ConviteDAO(conn).buscar(loginRemetente, loginDestinatario, idCompromisso);
		return null;
	}
	
	public List<Convite> buscarConvitesPorRemetente(int idRemetente) throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		//return new ConviteDAO(conn).buscarConvitesPorRemetente(idRemetente);
		return null;
	}
}
