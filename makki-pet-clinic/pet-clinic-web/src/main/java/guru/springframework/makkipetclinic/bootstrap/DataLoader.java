package guru.springframework.makkipetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.model.Vet;
import guru.springframework.makkipetclinic.services.OwnerService;
import guru.springframework.makkipetclinic.services.VetService;
import guru.springframework.makkipetclinic.services.map.OwnerServiceMap;
import guru.springframework.makkipetclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner{
	
	private final OwnerService ownerService;
	private final VetService vetService;
	
	public DataLoader() {
		ownerService = new OwnerServiceMap();
		vetService = new VetServiceMap();
	}

	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setFirstName("Tommy");
		owner1.setLastName("Shelby");
		
		ownerService.save(owner1);
		
		System.out.println("Loaded owners");
		
		Vet vet1 = new Vet();
		vet1.setId(1L);
		vet1.setFirstName("Polly");
		vet1.setLastName("Gray");
	
		vetService.save(vet1);
		
		System.out.println("Loaded vets");
		
	}
}
