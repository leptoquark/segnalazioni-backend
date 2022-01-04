package it.anac.segnalazioni.backend.model.health;

public class ResponseHealth {
	private final String STATUS_OK = "OK";
	private final String STATUS_KO = "KO";
	
	// default status OK
	private String status = this.STATUS_OK;
	private boolean protocollo = true;
	
	private String message  = "";
	
	public void setStatusKO()
	{
		status = this.STATUS_KO;
	}

	public void setStatusOK()
	{
		status = this.STATUS_OK;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setProtocollo(boolean protocollo)
	{
		this.protocollo = protocollo;
	}
	
	public boolean getProtocollo()
	{
		return protocollo;
	}
}
