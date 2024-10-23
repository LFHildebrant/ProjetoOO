package entities;

public class Convite {

	private Usuario destinatario;
	private Aceito aceito;
	private Compromisso compromisso;

	public Convite () {
		this.destinatario = new Usuario();
		this.compromisso= new Compromisso();
	}

	public Convite(Aceito aceito,Usuario destinatario,Compromisso compromisso) {
		this.destinatario = destinatario;
		this.compromisso= compromisso;
		this.aceito = aceito;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Aceito getAceito() {
		return this.aceito;
	}

	public void setAceito(Aceito aceito) {
		this.aceito = aceito;
	}

	public Compromisso getCompromisso() {
		return compromisso;
	}

	public void setCompromisso(Compromisso compromisso) {
		this.compromisso = compromisso;
	}
	
	
	
	
}