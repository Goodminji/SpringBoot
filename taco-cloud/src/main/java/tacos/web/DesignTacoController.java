package tacos.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")// 세션에 다수로 사용 - 여러개 타코 주문할 수 있도록
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;
	private TacoRepository tacoRepo;
	private UserRepository userRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo,TacoRepository tacoRepo,UserRepository userRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
		this.userRepo = userRepo;
	}
	
	@GetMapping
	public String showDesignForm(Model model, Principal principal) {
		/*
		 * List<Ingredient> ingredients = Arrays.asList( new
		 * Ingredient("FLTO","Flour Tortilla", Type.WRAP), new
		 * Ingredient("COTO","Corn Tortilla", Type.WRAP), new Ingredient("BEEF","Beef",
		 * Type.PROTEIN), new Ingredient("CARN","Carnitas", Type.PROTEIN), new
		 * Ingredient("TOMA","Tomatoes", Type.VEGGIES), new Ingredient("LETC","Lettuce",
		 * Type.VEGGIES), new Ingredient("CHED","Cheddar", Type.CHEESE), new
		 * Ingredient("JACK","Jack", Type.CHEESE), new Ingredient("SLSA","Salsa",
		 * Type.SAUCE), new Ingredient("Cream","Cream", Type.SAUCE) );
		 */
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for (Type type:types) {
			model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
		}
		//model.addAttribute("taco",new Taco());
		String username = principal.getName();
		User user = userRepo.findByUsername(username);
		model.addAttribute("user", user);
		System.out.println(model.getAttribute("cheese"));
		return "design";
		
	}
	//@ModelAttribute 각 객체가 생성이 되도록
	@ModelAttribute(name = "order")
	// 세션에 다수로 사용
	public Order order() {
		return new Order();
	}
	//@ModelAttribute 각 객체가 생성이 되도록
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	// @ModelAttribute Order order 매개변수의 값이 모델로부터 전달되어야 한다는것과 스프링MVC가 이 매개변수에 요청 매개변수를 바인딩X(??)
	@PostMapping
	public String processDesign(@Valid Taco disign,Errors errors,@ModelAttribute Order order) {
		if(errors.hasErrors()) {
			return "design";
		}
		/* log.info("ProcessDesign : " + disign); */
		Taco saved = tacoRepo.save(disign);
		order.addDesign(saved);
		
		return "redirect:/orders/current";
	}
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
}
