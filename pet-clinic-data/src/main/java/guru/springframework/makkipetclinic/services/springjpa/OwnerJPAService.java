package guru.springframework.makkipetclinic.services.springjpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.repositories.OwnerRepository;
import guru.springframework.makkipetclinic.services.OwnerService;

@Service
@Profile("springdatajpa")
public class OwnerJPAService implements OwnerService{

	private final OwnerRepository ownerRepository;
	
	public OwnerJPAService(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Set<Owner> findAll() {
		
		Set<Owner> owners = new HashSet<>();
		
		ownerRepository.findAll().forEach(owners::add);
		
		return owners;
	}//end findAll

	@Override
	public Owner findById(Long id) {
		return ownerRepository.findById(id).orElse(null);
	}//end findbyid

	@Override
	public Owner save(Owner object) {
		return ownerRepository.save(object);
	}//end save

	@Override
	public void delete(Owner object) {
		ownerRepository.delete(object);
	}//end delete

	@Override
	public void deleteById(Long id) {
		ownerRepository.deleteById(id);
	}//end deleteById

	@Override
	public Owner findByLastName(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}//end findByLastName

	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		
		return ownerRepository.findAllByLastNameLike(lastName);
	}
	
}
