package fr.isika.cdi07.projet3demo.modelform;

import java.util.List;

import fr.isika.cdi07.projet3demo.model.Projet;
import fr.isika.cdi07.projet3demo.model.Role;

public class ListProjetForm {

	private String critere;
	private List<Projet> projets;
	
	
	public String getCritere() {
		return critere;
	}
	public void setCritere(String critere) {
		this.critere = critere;
	}
	public List<Projet> getProjets() {
		return projets;
	}
	public void setProjets(List<Projet> projets) {
		this.projets = projets;
	}
	
	
	
}
