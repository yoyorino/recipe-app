package demo.spring5recipeapp.repositories;

import demo.spring5recipeapp.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	Ingredient findAllByRecipeIdAndId(Long recipeId, Long ingredientId);
}
