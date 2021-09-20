package fr.isika.cdi07.projet3demo.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class MessageInterne {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_message_interne")
	private Long idMessage;
	
	@Column(nullable = false)
	private String titre;
	
	@Column(nullable = false)
	private String contenu;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	@ManyToOne
	@JoinColumn(name="email")
	private Utilisateur emetteur;
	

	
	@OneToOne
	private Notification notification;
	
	public MessageInterne() {

	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Utilisateur getEmetteur() {
		return emetteur;
	}

	public void setEmetteur(Utilisateur emetteur) {
		this.emetteur = emetteur;
	}


	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Long getIdMessage() {
		return idMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageInterne [idMessage=");
		builder.append(idMessage);
		builder.append(", titre=");
		builder.append(titre);
		builder.append(", contenu=");
		builder.append(contenu);
		builder.append(", date=");
		builder.append(date);
		builder.append(", emetteur=");
		builder.append(emetteur.getEmail());
		builder.append("]");
		return builder.toString();
	}

	
}
