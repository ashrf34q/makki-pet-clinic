package guru.springframework.makkipetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.model.PetType;
import guru.springframework.makkipetclinic.model.Vet;
import guru.springframework.makkipetclinic.services.OwnerService;
import guru.springframework.makkipetclinic.services.PetTypeService;
import guru.springframework.makkipetclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner{
	
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
	
	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
	}

	@Override
	public void run(String... args) throws Exception {
		
		PetType cat = new PetType();
		cat.setName("Baby");
		PetType savedCatType = petTypeService.save(cat);
		
		PetType dog = new PetType();
		dog.setName("Obi-Wan");
		PetType savedPetType = petTypeService.save(dog);
		
		Owner owner1 = new Owner();
		owner1.setFirstName("Tommy");
		owner1.setLastName("Shelby");
		
		ownerService.save(owner1);
		
		System.out.println("Loaded owners");
		
		Vet vet1 = new Vet();
		vet1.setFirstName("Polly");
		vet1.setLastName("Gray");
	
		vetService.save(vet1);
		
		System.out.println("Loaded vets");
		
	}
}
