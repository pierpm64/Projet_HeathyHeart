package fr.isika.cdi07.projet3demo.services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.TerritoireReposistory;
import fr.isika.cdi07.projet3demo.model.Territoire;

@Service

public class TerritoireService {

	@Autowired
	private TerritoireReposistory territoireRepo;
	
	public Territoire ajoutTerritoire(Territoire territoire) {
		territoire.setDate(Date.from(Instant.now()));
		return territoireRepo.save(territoire);
	}
	
	public List<Territoire> afficherAllTerritoire(){
		return territoireRepo.findAll();
	}

	public Optional<Territoire> getTerritoireById(Long id) {
		return territoireRepo.findById(id);
	}

	public Territoire getTerritoireByIdNoOptional(Long id) {
		return territoireRepo.findByIdTerritoire(id);
	}
}
