package it.anac.segnalazioni.backend.model.appalto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bando{
	 @JsonProperty("LUOGO_ISTAT") 
	 public String lUOGO_ISTAT;
	 @JsonProperty("CIG_ACCORDO_QUADRO") 
	 public String cIG_ACCORDO_QUADRO;
	 @JsonProperty("STATO") 
	 public String sTATO;
	 @JsonProperty("TIPO_CIG") 
	 public String tIPO_CIG;
	 @JsonProperty("COD_TIPO_SCELTA_CONTRAENTE") 
	 public int cOD_TIPO_SCELTA_CONTRAENTE;
	 @JsonProperty("OGGETTO_LOTTO") 
	 public String oGGETTO_LOTTO;
	 @JsonProperty("OGGETTO_GARA") 
	 public String oGGETTO_GARA;
	 @JsonProperty("CPV") 
	 public List<CPV> cPV;
	 @JsonProperty("FLAG_ESCLUSO") 
	 public int fLAG_ESCLUSO;
	 @JsonProperty("NUMERO_GARA") 
	 public String nUMERO_GARA;
	 @JsonProperty("IMPORTO_LOTTO") 
	 public double iMPORTO_LOTTO;
	 @JsonProperty("CONDIZIONI_NEGOZIATA") 
	 public List<CONDIZIONINEGOZIATA> cONDIZIONI_NEGOZIATA;
	 @JsonProperty("SETTORE") 
	 public String sETTORE;
	 @JsonProperty("LUOGO_NUTS") 
	 public String lUOGO_NUTS;
	 @JsonProperty("CUP") 
	 public List<CUP> cUP;
	 @JsonProperty("N_LOTTI_COMPONENTI") 
	 public int n_LOTTI_COMPONENTI;
	 @JsonProperty("CIG") 
	 public String cIG;
	 @JsonProperty("IMPORTO_COMPLESSIVO_GARA") 
	 public double iMPORTO_COMPLESSIVO_GARA;
	 @JsonProperty("COD_MODALITA_REALIZZAZIONE") 
	 public Object cOD_MODALITA_REALIZZAZIONE;
	 @JsonProperty("MODALITA_REALIZZAZIONE") 
	 public String mODALITA_REALIZZAZIONE;
	 @JsonProperty("MOTIVO_ESCLUSIONE") 
	 public String mOTIVO_ESCLUSIONE;
	 @JsonProperty("DATA_SCADENZA_OFFERTA") 
	 public String dATA_SCADENZA_OFFERTA;
	 @JsonProperty("SIGLA_PROVINCIA") 
	 public String sIGLA_PROVINCIA;
	 @JsonProperty("TIPO_SCELTA_CONTRAENTE") 
	 public String tIPO_SCELTA_CONTRAENTE;
	 @JsonProperty("OGGETTO_PRINCIPALE_CONTRATTO") 
	 public String oGGETTO_PRINCIPALE_CONTRATTO;
}