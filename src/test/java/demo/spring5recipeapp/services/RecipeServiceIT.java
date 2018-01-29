package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.RecipeCommand;
import demo.spring5recipeapp.converters.RecipeCommandToRecipe;
import demo.spring5recipeapp.converters.RecipeToRecipeCommand;
import demo.spring5recipeapp.domain.Recipe;
import demo.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RecipeServiceIT {

	private static String NEW_DESCRIPTION = "New Description";

	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Transactional
	@Test
	public void testSaveOfDescription() throws Exception {
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe recipe = recipes.iterator().next();
		RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

		// when
		recipeCommand.setDescription(NEW_DESCRIPTION);
		recipeService.saveRecipeCommand(recipeCommand);

		// then
		Recipe savedRecipe = recipeService.findById(recipeCommand.getId());
		assertEquals(NEW_DESCRIPTION, savedRecipe.getDescription());
	}

	@Test
	@Transactional
	public void getAllRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().forEach(r -> log.warn(r.toString()));
		System.out.println(recipes.size());
		System.out.println(recipes.stream().mapToLong(r -> r.getCookTime()).sum());
	}

}
