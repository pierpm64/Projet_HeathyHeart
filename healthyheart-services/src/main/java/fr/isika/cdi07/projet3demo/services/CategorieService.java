package fr.isika.cdi07.projet3demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.CategorieRepository;
import fr.isika.cdi07.projet3demo.model.Categorie;
import fr.isika.cdi07.projet3demo.model.Territoire;
import fr.isika.cdi07.projet3demo.model.TypeProjet;

@Service

public class CategorieService {

	@Autowired
	private CategorieRepository categorieRepo;
	
	public Optional<Categorie> getCategorieByTerritoireAndType
		(Territoire territoire, TypeProjet typeProjet) {
		
		List<Categorie> categories = categorieRepo.findAll();
		Optional<Categorie> maCategorie;
		maCategorie = categories.stream()
				.filter(t -> t.getTerritoire().getIdTerritoire().equals(territoire.getIdTerritoire()) 
						&& t.getTypeProjet().getIdTypeProjet().equals(typeProjet.getIdTypeProjet()))
				.findFirst();
		
		return maCategorie;
	}
//	
//	public Categorie testFindBy(Territoire territoire, TypeProjet typeProjet){
//		return categorieRepo.findByTerritoireAndTypeProjet(territoire, typeProjet);
//	}
	
	public Categorie ajoutCategorie(Categorie categorie) {
		return categorieRepo.save(categorie);
	}

	public Optional<Categorie> getCategorieById(Long id) {
		return categorieRepo.findById(id);
	}

	public Categorie getCategorieByIdNoOptional(Long id) {
		return categorieRepo.findByIdCategorie(id);
	}
}
