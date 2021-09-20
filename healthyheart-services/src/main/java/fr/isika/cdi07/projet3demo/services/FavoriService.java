package fr.isika.cdi07.projet3demo.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

import fr.isika.cdi07.projet3demo.dao.FavoriRepository;
import fr.isika.cdi07.projet3demo.model.Favori;
import fr.isika.cdi07.projet3demo.model.Projet;

@Service
public class FavoriService {

	@Autowired
	private FavoriRepository favoriRepo;
	
	@Autowired
	private ProjetService projetService;

	public boolean estFavori(Long idProjet, String emailUtilisateur) {
		List<Favori> listeFavori = favoriRepo.findAll().stream()
				.filter(p -> p.getProjet().getIdProjet().equals(idProjet) 
				&& p.getUtilisateur().getEmail().equals(emailUtilisateur))
				.collect(Collectors.toList());		
		return !listeFavori.isEmpty();
	}

	public Favori ajouterFavori(Favori favori) {
		favori.setDate(Date.from(Instant.now()));
		return favoriRepo.save(favori);
	}

	public List<Favori> afficherTousLesFavoris(){
		return favoriRepo.findAll();
	}
	
	public List<Favori> afficherMesFavoris(String emailUtilisateur){
		return  favoriRepo.findAll().stream()
				.filter(fav -> fav.getUtilisateur().getEmail().equals(emailUtilisateur))
				.collect(Collectors.toList());
	}

	public Optional<Favori> chercherFavoriParIdprojetEtEmailUtilisateur(Long idProjet, String emailUtilisateur){
		List<Favori> liste = afficherTousLesFavoris();
		return liste.stream().filter(fav -> fav.getProjet().getIdProjet().equals(idProjet) 
					&& fav.getUtilisateur().getEmail().equals(emailUtilisateur))
		.findFirst();
	}

	public void retirerFavori(Favori favori) {
		favoriRepo.delete(favori);
	}
	
	public Long compterNombreFavorisParProjet(Projet projet) {
		return afficherTousLesFavoris()
				.stream()
				.filter(f -> f.getProjet().equals(projet))
				.count();
	}
	
	public List<Projet> getTroisProjetsPreferes(){
		Map<Projet, Long> listeProjetEtFavoris = countNbFavorisForProjects(projetService.afficherAllProjet());
		System.out.println("LISTEPROJETETFAVORI *** MAP : " + listeProjetEtFavoris);
		System.out.println("LISTEPROJETETFAVORI *** values : " + listeProjetEtFavoris.values());
		List<Projet> troisMeilleursProjets = listeProjetEtFavoris
			.entrySet()
			.stream()
			.sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
			.map(Entry::getKey)
			.collect(Collectors.toList());
		
		System.out.println("LIST TRIE PAR FAVORIS *** LIST : " + listeProjetEtFavoris);
		if(listeProjetEtFavoris.size() >= 3) {
			return troisMeilleursProjets.subList(0, 3);
		}
		return Collections.emptyList();
	}

	private Map<Projet, Long> countNbFavorisForProjects(List<Projet> projets) {
		Map<Projet, Long> listeProjetEtFavoris = new HashMap<>();
		for(Projet p : projets)
			listeProjetEtFavoris.put(p, compterNombreFavorisParProjet(p));
		return listeProjetEtFavoris;
	}

}
