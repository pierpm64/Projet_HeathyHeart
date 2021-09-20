package fr.isika.cdi07.projet3demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi07.projet3demo.dao.PortefeuilleProjetRepository;
import fr.isika.cdi07.projet3demo.model.PortefeuilleProjet;
import fr.isika.cdi07.projet3demo.model.PorteurProjet;
import fr.isika.cdi07.projet3demo.model.Role;

@Service
public class PortefeuilleService {

	private static final Logger LOGGER = Logger.getLogger(PortefeuilleService.class.getSimpleName());

	
	@Autowired
	private PortefeuilleProjetRepository portefeuilleRepo;

	public PortefeuilleProjet ajoutPortefeuille(PortefeuilleProjet portefeuille) {
		return portefeuilleRepo.save(portefeuille);
	}

	public PortefeuilleProjet isPresent(PorteurProjet porteurProjet, String libelle) {

		List<PortefeuilleProjet> portefeuilles = portefeuilleRepo.findAll();

		Optional<PortefeuilleProjet> optPortefeuille = portefeuilles.stream()
				.filter(r -> r.getLibelle().equalsIgnoreCase(libelle) && r.getPorteurprojet().equals(porteurProjet))
				.findFirst();

		if(optPortefeuille.isPresent())
			return optPortefeuille.get();

		PortefeuilleProjet newPortefeuille = new PortefeuilleProjet();
		newPortefeuille.setLibelle(libelle);
		newPortefeuille.setPorteurprojet(porteurProjet);

		return newPortefeuille;
	}

	public List<PortefeuilleProjet> testPortefeuilleByRolePorteurProjet(Role role) {
		List<PortefeuilleProjet> allPortefeuille = portefeuilleRepo.findAll();
		List<PortefeuilleProjet> selectionPortefeuille = new ArrayList<PortefeuilleProjet>();
		allPortefeuille.stream()
		.filter(p -> p.getPorteurprojet().getRole().equals(role))
		.forEach(p -> selectionPortefeuille.add(p));


		return selectionPortefeuille;
	}

	public Optional<PortefeuilleProjet> getPortefeuilleById(Long id) {
		return portefeuilleRepo.findById(id);
	}

	
	
}
