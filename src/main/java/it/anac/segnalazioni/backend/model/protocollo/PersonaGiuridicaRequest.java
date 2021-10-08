package it.anac.segnalazioni.backend.model.protocollo;

public class PersonaGiuridicaRequest
{
	private String denominazioneLike;
	private int page;
	private int size;

	public String getDenominazioneLike() {
		return denominazioneLike;
	}

	public void setDenominazioneLike(String denominazioneLike) {
		this.denominazioneLike = denominazioneLike;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
