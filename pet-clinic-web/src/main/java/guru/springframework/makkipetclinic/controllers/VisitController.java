package guru.springframework.makkipetclinic.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.makkipetclinic.model.Pet;
import guru.springframework.makkipetclinic.model.Visit;
import guru.springframework.makkipetclinic.services.PetService;
import guru.springframework.makkipetclinic.services.VisitService;

@Controller
public class VisitController {
	
	private final String VIEWS_ADD_OR_UPDATE_VISITS = "pets/createOrUpdateVisitForm";
	
	private final PetService petService;
	private final VisitService visitService;
	
	public VisitController(PetService petService, VisitService visitService) {
		this.petService = petService;
		this.visitService = visitService;
	}
	
	@InitBinder
	public void initBindOwner(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@ModelAttribute("visit")
	public Visit loadVetWithVisits(@PathVariable("petId") Long petId, Map<String, Object> model) {
		Pet pet = petService.findById(petId);
		model.put("pet", pet);
		Visit visit = new Visit();
		pet.getVisits().add(visit);
		visit.setPet(pet);
		return visit;
	}
	
	// Spring MVC calls loadVetWithVisits(...) before any of the below methods is called
	
	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm() {
		return VIEWS_ADD_OR_UPDATE_VISITS;
	}
	
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Validated Visit visit, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_ADD_OR_UPDATE_VISITS;
		} else {
			visitService.save(visit);
			
			return "redirect:/owners/{ownerId}";
		}
	}
	
	
}
