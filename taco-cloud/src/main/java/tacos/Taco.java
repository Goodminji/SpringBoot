package tacos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	@NotNull
	@Size(min = 5,message="at least 5 char")
	private String name;
	
	@Size(min = 1,message="at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	private Long id;
	private Date createdAt;
}
