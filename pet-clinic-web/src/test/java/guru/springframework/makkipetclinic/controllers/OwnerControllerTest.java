package guru.springframework.makkipetclinic.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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

//	@Test
//	void testListOwners() throws Exception {
//		when(ownerService.findAll()).thenReturn(owners);
//		
//		mockMvc.perform(get("/owners/index"))
//		.andExpect(status().isOk())
//		.andExpect(view().name("owners/index"))
//		.andExpect(model().attribute("owners", hasSize(2)));
//	}

	@Test
	void testFindOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/findOwners"))
		.andExpect(model().attributeExists("owner"));

		verifyNoInteractions(ownerService);
	}
	
	@Test
	void processFindFormReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString()))
        .thenReturn(owners.stream().collect(Collectors.toList()));

		mockMvc.perform(get("/owners"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownersList"))
		.andExpect(model().attribute("selections", hasSize(2)));
	}
	
	@Test
	void processFindFormReturnOne() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Collections.singletonList(Owner.builder().id(1l).build()));
		
		mockMvc.perform(get("/owners"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"));
		
	}
	
	@Test
	void processEmptyFindByLastName() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(owners.stream().collect(Collectors.toList()));
		
		mockMvc.perform(get("/owners")
				.param("lastName", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownersList"))
		.andExpect(model().attribute("selections", hasSize(2)));
		
	}
		
	@Test
	void showOwnerstest() throws Exception {
		
		when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
		
		mockMvc.perform(get("/owners/31"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/ownerDetails"))
		.andExpect(model().attribute("owner", hasProperty("id", is(1L))));
		
	}
	
	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/createOrUpdateOwnerForm"))
		.andExpect(model().attributeExists("owner"));
		
		verifyNoInteractions(ownerService);
	}
	
	@Test
	void processCreationForm() throws Exception {
		when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());
		
		mockMvc.perform(post("/owners/new"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"))
		.andExpect(model().attributeExists("owner"));
		
		verify(ownerService).save(ArgumentMatchers.any());
		
	} //end processCreationForm
	
	@Test
	void initUpdateOwnerForm() throws Exception {
		when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());
		
		mockMvc.perform(get("/owners/1/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("owners/createOrUpdateOwnerForm"))
		.andExpect(model().attributeExists("owner"));
		
//		verifyNoInteractions(ownerService);
	}
	
	@Test
	void processUpdateOwnerForm() throws Exception {
		when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());
		
		mockMvc.perform(post("/owners/1/edit"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/1"))
		.andExpect(model().attributeExists("owner"));
		
		verify(ownerService).save(ArgumentMatchers.any());
	}

}
