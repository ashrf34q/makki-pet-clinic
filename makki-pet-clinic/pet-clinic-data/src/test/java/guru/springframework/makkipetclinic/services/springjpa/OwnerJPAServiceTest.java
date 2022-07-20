package guru.springframework.makkipetclinic.services.springjpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.repositories.OwnerRepository;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {
  
	@Mock
	OwnerRepository ownerRepository;
	
	@InjectMocks
	OwnerJPAService service;
	
	final String lastName = "Makki";
	final Long id = 1L;

	 Owner returnOwner;
	
	@BeforeEach
	void setUp() throws Exception {
		returnOwner = Owner.builder().id(id).lastName(lastName).build();
	}

	@Test
	void testFindAll() {
		Set<Owner> ownersSet = new HashSet<>();
		ownersSet.add(Owner.builder().id(id).build());
		ownersSet.add(Owner.builder().id(2l).build());

		when(ownerRepository.findAll()).thenReturn(ownersSet);
		
		Set<Owner> owners = service.findAll();
		
		assertNotNull(owners);
		assertEquals(2, owners.size());
	}

	@Test
	void testFindById() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
		Owner owner = service.findById(id);
	
		assertNotNull(owner);
	}
	
	   @Test
	    void findByIdNotFound() {
	        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

	        Owner owner = service.findById((id));

	        assertNull(owner);
	    }

	@Test
	void testSave() {
		Owner ownerSave = Owner.builder().id(id).build();
		
		when(ownerRepository.save(any())).thenReturn(returnOwner);
		
		Owner owner = service.save(ownerSave);
		
		assertNotNull(owner);
		verify(ownerRepository, times(1)).save(any());
	}

	@Test
	void testDelete() {
		service.delete(returnOwner);
		
		verify(ownerRepository, times(1)).delete(any());
	}

	@Test
	void testDeleteById() {
		service.deleteById(id);
		
		verify(ownerRepository, times(1)).deleteById(anyLong()); //default is 1 time
	}

	@Test
	void testFindByLastName() {
		Owner returnOwner = Owner.builder().id(1L).lastName(lastName).build();
		
		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
		
		Owner ashraf = service.findByLastName(lastName);
		
		assertEquals(lastName, ashraf.getLastName());
	}

}
