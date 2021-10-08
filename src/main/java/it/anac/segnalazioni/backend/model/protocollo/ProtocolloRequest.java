package it.anac.segnalazioni.backend.model.protocollo;

public class ProtocolloRequest
{
	private String identificazioneAoo;
	private String identificazioneUfficio;
	private String protocolloTipoProtocollo;
	private String protocolloOggetto;
	private String protocolloMittente;
	private String protcolloTipoDocumento;
	private String assegnatarioUfficio;
	private int    assegnatarioCompetenza;
	private String documentoTipoDocumento;
	private String documentoNomeFile;
	private String documentoUrlDocumento;
	
	public String getIdentificazioneAoo() {
		return identificazioneAoo;
	}
	public void setIdentificazioneAoo(String identificazioneAoo) {
		this.identificazioneAoo = identificazioneAoo;
	}
	public String getIdentificazioneUfficio() {
		return identificazioneUfficio;
	}
	public void setIdentificazioneUfficio(String identificazioneUfficio) {
		this.identificazioneUfficio = identificazioneUfficio;
	}
	public String getProtocolloTipoProtocollo() {
		return protocolloTipoProtocollo;
	}
	public void setProtocolloTipoProtocollo(String protocolloTipoProtocollo) {
		this.protocolloTipoProtocollo = protocolloTipoProtocollo;
	}
	public String getProtocolloOggetto() {
		return protocolloOggetto;
	}
	public void setProtocolloOggetto(String protocolloOggetto) {
		this.protocolloOggetto = protocolloOggetto;
	}
	public String getProtocolloMittente() {
		return protocolloMittente;
	}
	public void setProtocolloMittente(String protocolloMittente) {
		this.protocolloMittente = protocolloMittente;
	}
	public String getProtcolloTipoDocumento() {
		return protcolloTipoDocumento;
	}
	public void setProtcolloTipoDocumento(String protcolloTipoDocumento) {
		this.protcolloTipoDocumento = protcolloTipoDocumento;
	}
	public String getAssegnatarioUfficio() {
		return assegnatarioUfficio;
	}
	public void setAssegnatarioUfficio(String assegnatarioUfficio) {
		this.assegnatarioUfficio = assegnatarioUfficio;
	}
	public int getAssegnatarioCompetenza() {
		return assegnatarioCompetenza;
	}
	public void setAssegnatarioCompetenza(int assegnatarioCompetenza) {
		this.assegnatarioCompetenza = assegnatarioCompetenza;
	}
	public String getDocumentoTipoDocumento() {
		return documentoTipoDocumento;
	}
	public void setDocumentoTipoDocumento(String documentoTipoDocumento) {
		this.documentoTipoDocumento = documentoTipoDocumento;
	}
	public String getDocumentoNomeFile() {
		return documentoNomeFile;
	}
	public void setDocumentoNomeFile(String documentoNomeFile) {
		this.documentoNomeFile = documentoNomeFile;
	}
	public String getDocumentoUrlDocumento() {
		return documentoUrlDocumento;
	}
	public void setDocumentoUrlDocumento(String documentoUrlDocumento) {
		this.documentoUrlDocumento = documentoUrlDocumento;
	}
}
