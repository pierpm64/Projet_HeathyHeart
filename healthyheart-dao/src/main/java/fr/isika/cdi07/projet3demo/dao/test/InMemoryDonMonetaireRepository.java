package fr.isika.cdi07.projet3demo.dao.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import fr.isika.cdi07.projet3demo.dao.DonMonetaireRepository;
import fr.isika.cdi07.projet3demo.model.DonMonetaire;

public class InMemoryDonMonetaireRepository implements DonMonetaireRepository{
	
	private static List<DonMonetaire> temporaryDonMonetaireList = new ArrayList<>();
	private static Long lastId;
	
	static {
		temporaryDonMonetaireList.add(new DonMonetaire());
		temporaryDonMonetaireList.add(new DonMonetaire());
	}
	
	@Override
	public List<DonMonetaire> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DonMonetaire> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DonMonetaire> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DonMonetaire> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends DonMonetaire> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<DonMonetaire> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DonMonetaire getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DonMonetaire> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DonMonetaire> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<DonMonetaire> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DonMonetaire> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<DonMonetaire> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(DonMonetaire entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends DonMonetaire> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends DonMonetaire> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends DonMonetaire> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends DonMonetaire> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends DonMonetaire> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

}
