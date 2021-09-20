package fr.isika.cdi07.projet3demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MessageRecu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_message_recu")
	private Long idMessageRecu;
	
	@ManyToOne
	@JoinColumn(name="id_message_interne")
	private MessageInterne messageInterne;
	
	@ManyToOne
	@JoinColumn(name="email")
	private Utilisateur utilisateur;
	
	@Column(name="is_read")
	private Boolean isRead;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dateHeure;
	
	public MessageRecu() {
		isRead = false;
	}

	public MessageInterne getMessageInterne() {
		return messageInterne;
	}

	public void setMessageInterne(MessageInterne messageInterne) {
		this.messageInterne = messageInterne;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Boolean isIsRead() {
		return isRead;
	}
	public Boolean getisRead() {
		return isRead;
	}

	public void setisRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Date getDateHeure() {
		return dateHeure;
	}

	public void setDateHeure(Date dateHeure) {
		this.dateHeure = dateHeure;
	}

	public Long getIdMessageRecu() {
		return idMessageRecu;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageRecu [idMessageRecu=");
		builder.append(idMessageRecu);
		builder.append(", messageInterne=");
		builder.append(messageInterne.getTitre());
		builder.append(", utilisateur=");
		builder.append(utilisateur.getEmail());
		builder.append(", isRead=");
		builder.append(isRead);
		builder.append(", dateHeure=");
		builder.append(dateHeure);
		builder.append("]");
		return builder.toString();
	}
	
	

}
