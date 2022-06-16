package guru.springframework.makkipetclinic.services;

import java.util.Set;

import guru.springframework.makkipetclinic.model.Vet;

public interface VetService {

	Vet findById(Long id);
	
	Vet save(Vet vet);
	
	Set<Vet> findAll();
}
