package guru.springframework.makkipetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//this abstract class will be used to implement CrudService interface methods.

public abstract class AbstractMapService<T, ID>{	// An abstract class is a restricted class that annot be used to 
													//create objects(to access it it must be inherited from another class)
	protected Map<ID,T> map = new HashMap<>();
	
	Set<T> findAll(){
		return new HashSet<>(map.values());	//return everything in the map
	}
	
	T findById(ID id) {
		return map.get(id);
	}
	
	T save(ID id, T object) {
		map.put(id, object);
		return object;
	}
	
	void deleteById(ID id) {
		map.remove(id);
	}
	
	void delete(T object) {
		map.entrySet().removeIf(entry -> entry.getValue().equals(object));
	}

}
