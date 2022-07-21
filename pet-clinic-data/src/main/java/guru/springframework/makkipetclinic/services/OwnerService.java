package guru.springframework.makkipetclinic.services;

import guru.springframework.makkipetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
	Owner findByLastName(String lastName);
	
}
