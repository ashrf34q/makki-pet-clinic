package guru.springframework.makkipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.makkipetclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

}
