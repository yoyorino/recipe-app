package demo.spring5recipeapp.services;

import demo.spring5recipeapp.converters.RecipeCommandToRecipe;
import demo.spring5recipeapp.converters.RecipeToRecipeCommand;
import demo.spring5recipeapp.commands.RecipeCommand;
import demo.spring5recipeapp.domain.Recipe;
import demo.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	private final RecipeCommandToRecipe recipeCommandToRecipe;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("getting add recipes");
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);

		return recipeSet;
	}

	@Override
	public Recipe findById(Long recipeId) {
		Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
		if (!recipeOpt.isPresent()) {
			throw new RuntimeException("Recipe not found!");
		}

		return recipeOpt.get();
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
		Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

		recipeRepository.save(recipe);
		log.debug("Saved recipe with id " + recipe.getId());

		return recipeToRecipeCommand.convert(recipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long recipeCommandId) {
		Optional<Recipe> recipeOpt = recipeRepository.findById(recipeCommandId);
		if (!recipeOpt.isPresent()) {
			throw new RuntimeException("Recipe for the recipe command id not found!");
		}

		return recipeToRecipeCommand.convert(recipeOpt.get());
	}

	@Override
	public void deleteById(Long recipeId) {
		recipeRepository.deleteById(recipeId);
	}
}
