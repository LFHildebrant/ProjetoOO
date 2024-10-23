package entities;

import java.sql.Date;


public class Usuario {
	
	private String nomeCompleto;
	private String genero;
	private String fotoPessoal;
	private String email;
	private String login;
	private String senha;
	private Date dataNascimento;
	public Usuario () {
		
	}

	public Usuario(String nomeCompleto, String genero, String fotoPessoal, String email, String login,
			String senha, Date dataNascimento) {
		this.nomeCompleto = nomeCompleto;
		this.genero = genero;
		this.fotoPessoal = fotoPessoal;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getFotoPessoal() {
		return fotoPessoal;
	}

	public void setFotoPessoal(String fotoPessoal) {
		this.fotoPessoal = fotoPessoal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String toString () {
		return this.getNomeCompleto();
	}
}