package guru.springframework.makkipetclinic.services;

import java.util.Set;

import guru.springframework.makkipetclinic.model.Owner;

public interface OwnerService {
	Owner findByLastName(String lastName);
	
	Owner findById(Long id);
	
	Owner save(Owner owner);
	
	Set<Owner> findAll();
	
}
