package b13.dto;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
	
	String houseNumber;
	String street;
	String state;
	String city;
	String country;
	int zipcode;
	
}
