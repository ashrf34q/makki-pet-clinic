package guru.springframework.makkipetclinic.services.springjpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.makkipetclinic.model.Visit;
import guru.springframework.makkipetclinic.repositories.VisitRepository;
import guru.springframework.makkipetclinic.services.VisitService;

@Service
@Profile("springdatajpa")
public class VisitJPAService implements VisitService{
	
	private final VisitRepository visitRepository;
	
	public VisitJPAService(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	@Override
	public Set<Visit> findAll() {
		Set<Visit> visits = new HashSet<>();
		visitRepository.findAll().forEach(visits::add);
		return visits;
	}

	@Override
	public Visit findById(Long id) {
		return visitRepository.findById(id).orElse(null);
	}

	@Override
	public Visit save(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public void delete(Visit visit) {
		visitRepository.delete(visit);
	}

	@Override
	public void deleteById(Long id) {
		visitRepository.deleteById(id);
	}

}
