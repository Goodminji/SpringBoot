package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient>{
  //Converter<String, Ingredient> String 변환할 값의 타입,,Ingredient는 변환된 값의 타입 
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepo.findById(id);
	}

}
