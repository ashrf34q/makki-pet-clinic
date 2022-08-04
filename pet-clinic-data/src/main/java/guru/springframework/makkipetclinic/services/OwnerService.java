package guru.springframework.makkipetclinic.services;

import java.util.List;

import guru.springframework.makkipetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
	Owner findByLastName(String lastName);

	List<Owner> findAllByLastNameLike(String lastName);
	
}
