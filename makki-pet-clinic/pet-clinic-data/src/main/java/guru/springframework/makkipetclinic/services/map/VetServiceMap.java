package guru.springframework.makkipetclinic.services.map;

import java.util.Set;

import guru.springframework.makkipetclinic.model.Vet;
import guru.springframework.makkipetclinic.services.CrudService;

public class VetServiceMap extends AbstractMapService<Vet, Long> implements CrudService<Vet, Long> {

	@Override
	public Vet save(Vet object) {
		return super.save(object.getId(), object);
	}

	@Override
	public Set<Vet> findAll() {
		return super.findAll();
	}

	@Override
	public Vet findById(Long id) {
		return super.findById(id);
	}


	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}

	@Override
	public void delete(Vet object) {
		super.delete(object);
	}
	
	
}
