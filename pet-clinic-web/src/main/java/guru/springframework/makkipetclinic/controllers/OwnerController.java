package guru.springframework.makkipetclinic.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.services.OwnerService;

@RequestMapping({"/owners"})	//This means this controller class will get /owners requests only
@Controller
public class OwnerController {
	
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
	}
	
	@GetMapping
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		
		
		if(owner.getLastName() == null) {
			owner.setLastName(""); //emppty string signifies broadest possible search
		}
		
		List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
		
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
		
	}
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
		
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(ownerService.findById(ownerId));
		return mav;
	}
}
