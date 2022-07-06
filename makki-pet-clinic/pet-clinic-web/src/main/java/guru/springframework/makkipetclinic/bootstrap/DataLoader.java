package guru.springframework.makkipetclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.makkipetclinic.model.Owner;
import guru.springframework.makkipetclinic.model.Pet;
import guru.springframework.makkipetclinic.model.PetType;
import guru.springframework.makkipetclinic.model.Speciality;
import guru.springframework.makkipetclinic.model.Vet;
import guru.springframework.makkipetclinic.services.OwnerService;
import guru.springframework.makkipetclinic.services.PetTypeService;
import guru.springframework.makkipetclinic.services.SpecialtyService;
import guru.springframework.makkipetclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner{
	
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
	private final SpecialtyService specialtyService;
	
	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
		this.specialtyService = specialtyService;
	}

	@Override
	public void run(String... args) throws Exception {
		
		int count = petTypeService.findAll().size();

		if(count == 0) {
		loadData();
		}
	}

	private void loadData() {
		PetType cat = new PetType();
		cat.setName("cat");
		PetType savedCatType = petTypeService.save(cat);
		
		PetType dog = new PetType();
		dog.setName("dog");
		PetType savedDogPetType = petTypeService.save(dog);
		
		PetType horse = new PetType();
		horse.setName("horse");
		PetType savedHorsePetType = petTypeService.save(horse);
		
		Speciality radiology = new Speciality();
		radiology.setDescription("Radiology");
		Speciality savedRadiology = specialtyService.save(radiology); 
		
		Speciality dentistry = new Speciality();
		dentistry.setDescription("Dentistry");
		Speciality savedDentistry = specialtyService.save(dentistry);
		
		Speciality surgery = new Speciality();
		surgery.setDescription("Surgery");
		Speciality savedSurgery = specialtyService.save(surgery);
		
		Owner owner1 = new Owner();
		owner1.setFirstName("Thomas");
		owner1.setLastName("Shelby");
		owner1.setAddress("1832 Brickell");
		owner1.setCity("Birmingham");
		owner1.setPhone("3132340121");
		
		Pet TommysPet = new Pet();
		TommysPet.setName("Grace's Secret");
		TommysPet.setPetType(savedHorsePetType);
		TommysPet.setOwner(owner1);
		TommysPet.setBirthDate(LocalDate.of(1910, 04, 14));
		owner1.getPets().add(TommysPet);
		
		ownerService.save(owner1);
		
		Owner owner2 = new Owner();
		owner2.setFirstName("Arthur");
		owner2.setLastName("Shelby");
		owner2.setAddress("1832 Brickell");
		owner2.setCity("Birmingham");
		owner2.setPhone("3133345681");
		
		Pet arthursPet = new Pet();
		arthursPet.setName("Baby");
		arthursPet.setPetType(savedCatType);
		arthursPet.setBirthDate(LocalDate.of(1919, 07, 04));
		arthursPet.setOwner(owner2);
		owner2.getPets().add(arthursPet);
		
		ownerService.save(owner2);
		
		System.out.println("Loaded owners");
		
		Vet vet1 = new Vet();
		vet1.setFirstName("Polly");
		vet1.setLastName("Gray");
		vet1.getSpecialities().add(savedSurgery);
		vetService.save(vet1);
		
		Vet vet2 = new Vet();
		vet2.setFirstName("Jessie");
		vet2.setLastName("Porter");
		vet2.getSpecialities().add(savedDentistry);
		vetService.save(vet2);
		
		System.out.println("Loaded vets");
	}
}
