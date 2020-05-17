package b13.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class User {
	
	@Id
	String username;
	String password;
	
	String fullname;
	Date dob;
	
	@ElementCollection
	List<Long> phoneNumber;
	
	@Embedded
	@ElementCollection
	@OrderColumn
	List<Address> userAddresses;
	
	@Embedded
	@ElementCollection
	@OrderColumn
	List<Card> userCards;
	
	@ElementCollection
	List<Role> roles;
	
	public enum Role{
		ROLE_USER, ROLE_ADMIN, ROLE_SELLER, ROLE_DEVELOPER, 
	}
	
}
