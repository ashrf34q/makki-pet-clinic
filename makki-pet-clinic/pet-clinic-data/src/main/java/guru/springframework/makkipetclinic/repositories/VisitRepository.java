package guru.springframework.makkipetclinic.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.makkipetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {

}
