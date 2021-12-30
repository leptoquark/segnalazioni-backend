package it.anac.segnalazioni.backend.engine.model;

import java.net.MalformedURLException;
import java.net.URL;

public class FileDocument {
	private String filename;
	private URL url;
	private boolean principale;
	
	public FileDocument(String url, String filename, boolean principale) throws MalformedURLException
	{
		this.principale = principale;
		this.filename = filename;
		if (url!=null && !url.contentEquals(""))
			this.url = new URL(url);
	}
	
	public FileDocument(String filename) throws MalformedURLException
	{
		this.filename = filename;
	}
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	/*
	 * restituisce se principale o allegato
	 */
	public String getTipo()
	{
		if (principale)
			return "P";
		return "A";
	}
}
