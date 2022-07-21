package guru.springframework.makkipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.makkipetclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long> {

}
