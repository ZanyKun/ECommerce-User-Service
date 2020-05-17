package b13.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
class Card {
	
	@Column(unique = true)
	long cardNumber;
	Type cardType;
	Date expiryDate;
	int cvv;
	
	String cardholderName;
	Address billingAddress;
	
	
	public enum Type{
		CREDIT, DEBIT;
	}
}
