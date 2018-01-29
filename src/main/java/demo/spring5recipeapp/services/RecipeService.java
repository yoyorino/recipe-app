package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.RecipeCommand;
import demo.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long recipeId);

	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

	RecipeCommand findCommandById(Long recipeCommandId);

	void deleteById(Long recipeId);
}
