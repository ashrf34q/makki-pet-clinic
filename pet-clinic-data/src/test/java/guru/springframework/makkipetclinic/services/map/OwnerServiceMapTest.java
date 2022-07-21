package guru.springframework.makkipetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.makkipetclinic.model.Owner;
 
class OwnerServiceMapTest {

	OwnerServiceMap ownerMapService;
	
	final Long ownerId = 1L;
	final String lastName = "Makki";
	
	@BeforeEach
	void setUp() throws Exception {
		ownerMapService = new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
		
		ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
	}

	@Test
	void testSaveOwnerExistingId() {
		Long id = 2L;
		
		Owner owner = Owner.builder().id(id).build();
		
		Owner savedOwner = ownerMapService.save(owner);
		
		assertEquals(id, savedOwner.getId());
	}
	
	@Test
	void saveOwnerNoId() {
		Owner savedOwner = ownerMapService.save(Owner.builder().build());
		
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}

	@Test
	void testFindAll() {
		Set<Owner> ownerSet = ownerMapService.findAll();
		
		assertEquals(1, ownerSet.size());
	}

	@Test
	void testDeleteByIdLong() {
		ownerMapService.deleteById(ownerId);
		
		assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void testDeleteOwner() {
		ownerMapService.delete(ownerMapService.findById(ownerId));
		
		assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void testFindByLastName() {
		Owner ashraf = ownerMapService.findByLastName(lastName);
		
		assertNotNull(ashraf);
		assertEquals(ownerId, ashraf.getId());
	}
	
	@Test
	void testFindByIdLong() {
		Owner owner = ownerMapService.findById(ownerId);
		
		assertEquals(ownerId, owner.getId());
	}

}
