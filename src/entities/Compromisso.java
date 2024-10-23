package entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
public class Compromisso {
	
	private String titulo;
	private String descricao;
	private String local;
	private Date dataTermino;
	private Date dataInicio;
	private Time horaInicio;
	private Time horaTermino;
	private int idCompromisso;
	private Agenda agenda;
	private Timestamp momentoNotificacao;
	 public Compromisso () {	
		 this.agenda = new Agenda();
	 }

	public Compromisso(int idCompromisso,String titulo, String descricao, String local, Date dataTermino, Date dataInicio,
			Time horaInicio, Time horaTermino,Timestamp momentoNotificacao) {
		this.idCompromisso = idCompromisso;
		this.titulo = titulo;
		this.descricao = descricao;
		this.local = local;
		this.dataTermino = dataTermino;
		this.dataInicio = dataInicio;
		this.horaInicio = horaInicio;
		this.horaTermino = horaTermino;
		this.momentoNotificacao = momentoNotificacao;
		this.agenda = new Agenda();
		this.agenda.setUsuario(new Usuario());
	}
	public void setMomentoNotificacao(Timestamp momentoNotificacao) {
		this.momentoNotificacao = momentoNotificacao;
	}
	public Timestamp getMomentoNotificacao() {
		return this.momentoNotificacao;
	}
	public void setAgenda(Agenda agenda) {
		this.agenda= agenda;
	}
	public Agenda getAgenda() {
		return this.agenda;
	}
	public int getIdCompromisso() {
		return idCompromisso;
	}

	public void setIdCompromisso(int idCompromisso) {
		this.idCompromisso = idCompromisso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Time getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Time getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(Time horaTermino) {
		this.horaTermino = horaTermino;
	}


	public String toString() {
		return this.titulo;
	}
	 
	
	

}