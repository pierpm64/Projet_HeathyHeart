package fr.isika.cdi07.projet3demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.AdresseRepository;
import fr.isika.cdi07.projet3demo.model.Adresse;

@Service
public class AdresseService {
	
	@Autowired
	private AdresseRepository adresseRepo;
	
	public void ajoutAdresse(Adresse adresse) {
		adresseRepo.save(adresse);
	}

}
