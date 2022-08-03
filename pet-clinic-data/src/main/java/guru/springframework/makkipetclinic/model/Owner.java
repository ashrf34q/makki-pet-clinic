package guru.springframework.makkipetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
	
	@Builder
	public Owner(Long id, String firstName, String lastName, String address, String city, String phone, Set<Pet> pets) {
		super(id, firstName, lastName);
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.pets = pets;
	}
	
	@Column(name = "address")
	private String address;
	


	@Column(name = "city")
	private String city;
	
	@Column(name = "phone")
	private String phone;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Pet> pets = new HashSet<>() ;

}