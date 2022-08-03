package guru.springframework.makkipetclinic.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.services.OwnerService;

@ExtendWith(SpringExtension.class)
class OwnerControllerTest {
	
	@Mock
	OwnerService ownerService;
	
	@InjectMocks
	OwnerController ownerController;

	Set<Owner> owners;
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
	owners = new HashSet<>();
	owners.add(Owner.builder().id(1L).build());
	owners.add(Owner.builder().id(2L).build());
	
	mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}

	@Test
	void testListOwners() throws Exception {
		when(ownerService.findAll()).thenReturn(owners);
		
		mockMvc.perform(get("/owners/index"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/index"))
		.andExpect(model().attribute("owners", hasSize(2)));
	}

	@Test
	void testFindOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
		.andExpect(status().isOk())
		.andExpect(view().name("NotImplemented"));

		verifyNoInteractions(ownerService);
	}
	
	@Test
	void showOwnerstest() throws Exception {
		
		when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
		
		mockMvc.perform(get("/owners/31"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownerDetails"))
		.andExpect(model().attribute("owner", hasProperty("id", is(1L))));
		
	}

}
