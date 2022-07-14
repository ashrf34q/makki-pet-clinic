package guru.springframework.makkipetclinic.services.springjpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.makkipetclinic.model.Speciality;
import guru.springframework.makkipetclinic.repositories.SpecialtyRepository;
import guru.springframework.makkipetclinic.services.SpecialtyService;

@Service
@Profile("springdatajpa")
public class SpecialtyJPAService implements SpecialtyService {
	
	private final SpecialtyRepository specialtyRepository;

	public SpecialtyJPAService(SpecialtyRepository specialtyRepository) {
		this.specialtyRepository = specialtyRepository;
	}

	@Override
	public Set<Speciality> findAll() {
		
		Set<Speciality> specialities = new HashSet<>();
		
		specialtyRepository.findAll().forEach(specialities::add);
		
		return specialities;
	}

	@Override
	public Speciality findById(Long id) {
		return specialtyRepository.findById(id).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return specialtyRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		specialtyRepository.delete(object);
	}

	@Override
	public void deleteById(Long id) {
		specialtyRepository.deleteById(id);
	}
	
}
