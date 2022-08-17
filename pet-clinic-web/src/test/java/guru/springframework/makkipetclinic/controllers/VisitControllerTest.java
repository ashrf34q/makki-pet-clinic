package guru.springframework.makkipetclinic.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.model.Pet;
import guru.springframework.makkipetclinic.model.PetType;
import guru.springframework.makkipetclinic.services.PetService;
import guru.springframework.makkipetclinic.services.VisitService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

	    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
	    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/{ownerId}";

	    @Mock
	    PetService petService;

	    @Mock
	    VisitService visitService;

	    @InjectMocks
	    VisitController visitController;

	    private MockMvc mockMvc;

	    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
	    private final Map<String, String> uriVariables = new HashMap<>();
	    private URI visitsUri;

	    @BeforeEach
	    void setUp() {
	        Long petId = 1L;
	        Long ownerId = 1L;
	        when(petService.findById(anyLong()))
	                .thenReturn(
	                        Pet.builder()
	                            .id(petId)
	                            .birthDate(LocalDate.of(2018,11,13))
	                            .name("Bobby")
	                            .visits(new HashSet<>())
	                            .owner(Owner.builder()
	                                .id(ownerId)
	                                .lastName("Doe")
	                                .firstName("John")
	                                .build())
	                            .petType(PetType.builder()
	                                    .name("Dog").build())
	                            .build()
	                );		//Just initializing our pet object

	        uriVariables.clear();
	        uriVariables.put("ownerId", ownerId.toString());
	        uriVariables.put("petId", petId.toString());
	        visitsUri = visitsUriTemplate.expand(uriVariables);	//here we're expanding the template into a uri using the Map keys and values

	        mockMvc = MockMvcBuilders
	                .standaloneSetup(visitController)
	                .build();
	    }

	    @Test
	    void initNewVisitForm() throws Exception {
	        mockMvc.perform(get(visitsUri))
	                .andExpect(status().isOk())
	                .andExpect(view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
	    }


	    @Test
	    void processNewVisitForm() throws Exception {
	        mockMvc.perform(post(visitsUri)
	                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                            .param("date","2021-12-12")
	                            .param("description", "Any Description"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name(REDIRECT_OWNERS_1))
	                .andExpect(model().attributeExists("visit"));
	    }
}
