package demo.spring5recipeapp.services;

import demo.spring5recipeapp.repositories.IngredientRepository;
import demo.spring5recipeapp.commands.IngredientCommand;
import demo.spring5recipeapp.converters.IngredientCommandToIngredient;
import demo.spring5recipeapp.converters.IngredientToIngredientCommand;
import demo.spring5recipeapp.domain.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private IngredientRepository ingredientRepository;
	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.ingredientRepository = ingredientRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Ingredient ingredient = ingredientRepository.findAllByRecipeIdAndId(recipeId, ingredientId);
		return ingredientToIngredientCommand.convert(ingredient);
	}

	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
		Ingredient savedIngredient = ingredientRepository.save(ingredient);
		return ingredientToIngredientCommand.convert(savedIngredient);
	}
}
