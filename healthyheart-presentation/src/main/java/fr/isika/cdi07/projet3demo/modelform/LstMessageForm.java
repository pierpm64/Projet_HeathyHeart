package fr.isika.cdi07.projet3demo.modelform;

import java.util.List;

import fr.isika.cdi07.projet3demo.model.MessageRecu;

public class LstMessageForm {
	
	private List<MessageRecu> lstMsgRecu;
	
	private String critere;
	
	private String etatMsg;

	public String getEtatMsg() {
		return etatMsg;
	}

	public void setEtatMsg(String etatMsg) {
		this.etatMsg = etatMsg;
	}

	public List<MessageRecu> getLstMsgRecu() {
		return lstMsgRecu;
	}

	public void setLstMsgRecu(List<MessageRecu> lstMsgRecu) {
		this.lstMsgRecu = lstMsgRecu;
	}

	public String getCritere() {
		return critere;
	}

	public void setCritere(String critere) {
		this.critere = critere;
	}
	
	

}
