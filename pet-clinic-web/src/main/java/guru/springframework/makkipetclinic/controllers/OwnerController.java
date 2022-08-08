package guru.springframework.makkipetclinic.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.services.OwnerService;

@RequestMapping({"/owners"})	//This means this controller class will get /owners requests only
@Controller
public class OwnerController {
	
	private final String VIEWS_OWNER_CREATE_OR_UPDATE = "owners/createOrUpdateOwnerForm";
	
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder webDataBinder) {	//we don't want malicious users to be able to access and modify the id property
		webDataBinder.setDisallowedFields("id");
	}

//	@RequestMapping({"", "/", "/index", "/index.html"})
//	public String listOwners(Model model) {
//		
//		model.addAttribute("owners", ownerService.findAll());
//		
//		return "owners/index";
//	}
	
	@RequestMapping("/find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		
		return "owners/findOwners";
	} //end findOwners
	
	@GetMapping
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		
		
		if(owner.getLastName() == null) {
			owner.setLastName(""); //emppty string signifies broadest possible search
		}
		
		List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%"); //Adding wildcards so now on the backend, SQL will 
																								   //perform a LIKE operation with the last name
		
		if(results.isEmpty()) {
			//Not found
			result.rejectValue("lastName", "notFound" , "not found");
			return "owners/findOwners";
		}
		else if(results.size() == 1) {
			// One Owner found
			owner = results.get(0);
			return "redirect:/owners/" + owner.getId();
		}
		else {
			//many owners found
			model.addAttribute("selections", results);
			return "owners/ownersList";
		}
		
	} //end processFindForm
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable Long ownerId) {
		
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(ownerService.findById(ownerId));
		return mav;
	} //end showoOwner
	
	@GetMapping("/new")
	public String initCreationForm(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		
		return VIEWS_OWNER_CREATE_OR_UPDATE;
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Validated Owner owner, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE;
		}
		else {
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}
	} //end processCreationForm
	
	@GetMapping("/{id}/edit")
	public String initUpdateOwnerForm(@PathVariable Long id, Model model) {
		model.addAttribute("owner", ownerService.findById(id));
		
		return VIEWS_OWNER_CREATE_OR_UPDATE;
	}
	
	@PostMapping("/{id}/edit")
	public String processUpdateOwnerForm(@Validated Owner owner, BindingResult result, @PathVariable Long id) { //explicitly bind the id
		if(result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE;
		}
		else {
			owner.setId(id);
			Owner savedOwner = ownerService.save(owner);
			return "redirect:/owners/" + savedOwner.getId();
		}
	}
	
}
