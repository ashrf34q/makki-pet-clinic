package guru.springframework.makkipetclinic.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.model.Pet;
import guru.springframework.makkipetclinic.model.PetType;
import guru.springframework.makkipetclinic.services.OwnerService;
import guru.springframework.makkipetclinic.services.PetService;
import guru.springframework.makkipetclinic.services.PetTypeService;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
	private static final String VIEWS_PETS_CREATE_OR_UPDATE = "pets/createOrUpdatePetForm";
	private final PetService petService;
	private final OwnerService ownerService;
	private final PetTypeService petTypeService;
	
	public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.petTypeService = petTypeService;
	}
	
	@ModelAttribute("types") 	// bind what gets returned here to model attribute "types"
	public Collection<PetType> populateTypes(){
		return petTypeService.findAll();
	}
	
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
		return ownerService.findById(ownerId);
	}
	
	@InitBinder("owner")
	public void initBindOwner(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, Model model) {
		Pet pet = new Pet();
		owner.getPets().add(pet);
		pet.setOwner(owner);
		model.addAttribute("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE;
	}
	
	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Validated Pet pet, BindingResult result, Model model) {
		if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		owner.getPets().add(pet);
		pet.setOwner(owner);	//trying something
		if(result.hasErrors()) {
			model.addAttribute("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE;
		} else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}	//end processCreationForm
	
	
	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") Long petId, Model model) {
		model.addAttribute("pet", petService.findById(petId));
		
		return VIEWS_PETS_CREATE_OR_UPDATE;
	}
	
	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Validated Pet pet, BindingResult result, Owner owner, Model model) {
		
		
		if(result.hasErrors()) {
			pet.setOwner(owner);
			model.addAttribute("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE;
		}
		else {
//			owner.getPets().add(pet); Adding the updated pet object back into the set of pets is causing a PersistentObjectException, that's
			// because we're adding the same object into the same set (we're duplicating). We just have to set our pet owner as shown below
			pet.setOwner(owner);
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
	
	
}	//end Controller class	
