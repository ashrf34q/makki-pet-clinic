package guru.springframework.makkipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.makkipetclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
	
}
