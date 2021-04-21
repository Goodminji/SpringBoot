package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;
//JPA 리퍼지터리 CrudRepository 사용
public interface IngredientRepository extends CrudRepository<Ingredient, String>{
	/*
	 * Iterable<Ingredient> findAll(); 
	 * Ingredient findById(String id); 
	 * Ingredient save(Ingredient ingredient);
	 */
}
