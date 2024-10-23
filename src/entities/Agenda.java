package entities;

public class Agenda {
	
	private int idAgenda;
	private String nome;
	private String descricao;
	private Usuario usuario;
	public Agenda () {
		this.usuario = new Usuario();
	}
	
	public Agenda(String nome, String descricao, Usuario usuario,int loginUsuario) {
		this.nome = nome;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	public int getIdAgenda() {
		return this.idAgenda;
	}

	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String toString( ) {
		return this.getNome();
	}
	
	

	
}
	