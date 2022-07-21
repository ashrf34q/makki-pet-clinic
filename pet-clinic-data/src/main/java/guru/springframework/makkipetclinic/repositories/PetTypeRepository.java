package guru.springframework.makkipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.makkipetclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

}
