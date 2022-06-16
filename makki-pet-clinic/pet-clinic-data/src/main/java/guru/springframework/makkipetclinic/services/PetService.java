package guru.springframework.makkipetclinic.services;

import java.util.Set;

import guru.springframework.makkipetclinic.model.Pet;

public interface PetService {

	Pet findById(Long id);
	
	Pet save(Pet pet);
	
	Set<Pet> findAll();
}
