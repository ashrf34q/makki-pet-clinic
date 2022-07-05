package guru.springframework.makkipetclinic.services.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import guru.springframework.makkipetclinic.model.BaseEntity;

//this abstract class will be used to implement CrudService interface methods.

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {	// An abstract class is a restricted class that annot be used to 
													//create objects(to access it it must be inherited from another class)
	protected Map<Long,T> map = new HashMap<>();
	
	Set<T> findAll(){
		return new HashSet<>(map.values());	//return everything in the map
	}
	
	T findById(ID id) {
		return map.get(id);
	}
	
	T save(T object) {
		if(object != null) {
			if(object.getId() == null) {
				object.setId(getNextID());
			}
			map.put(object.getId(), object);
		} else {
		throw new RuntimeException("Object cannot be null!");	
		}
		return object;
	}
	 
	void deleteById(ID id) {
		map.remove(id);
	}
	
	void delete(T object) {
		map.entrySet().removeIf(entry -> entry.getValue().equals(object));
	}
	
	private Long getNextID() {
		Long nextId = null;
		
		try {
			nextId = Collections.max(map.keySet()) + 1;
		} catch (NoSuchElementException e) {
			nextId = 1L;
		}
		return nextId;
	}
}
