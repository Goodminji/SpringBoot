package tacos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {
	@NotBlank(message="Name")
	private String deliveryName;
	
	@NotBlank(message="Street")
	private String deliveryStreet;
	
	@NotBlank(message="City")
	private String deliveryCity;
	
	@NotBlank(message="State")
	private String deliveryState;
	
	@NotBlank(message="Zip")
	private String deliveryZip;
	
	@CreditCardNumber(message="credit card number")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="MM/YY")
	private String ccExpiration;
	
	@Digits(integer=3,fraction=0,message="CVV")
	private String ccCVV;
	
}
