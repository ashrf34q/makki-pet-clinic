package guru.springframework.makkipetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"/owners"})	//This means this controller class will get /owners requests only
@Controller
public class OwnerController {

	@RequestMapping({"", "/", "/index", "/index.html"})
	public String listVets() {
		return "owners/index";
	}
}
