package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.IngredientCommand;
import demo.spring5recipeapp.converters.IngredientCommandToIngredient;
import demo.spring5recipeapp.converters.IngredientToIngredientCommand;
import demo.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import demo.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import demo.spring5recipeapp.domain.Recipe;
import demo.spring5recipeapp.repositories.IngredientRepository;
import demo.spring5recipeapp.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

	private IngredientService ingredientService;

	@Mock
	private IngredientRepository ingredientRepository;

	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private IngredientCommandToIngredient ingredientCommandToIngredient;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
		this.ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
	}

	@Test
	public void findByRecipeIdAndIngredientId() {

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		recipe.addIngredient(ingredient1);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		recipe.addIngredient(ingredient2);
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		recipe.addIngredient(ingredient3);

		Long recipeId = recipe.getId();

		Map<Long, Ingredient> ingredientIdToIngredient = recipe.getIngredients().stream().collect(Collectors.toMap(Ingredient::getId, i -> i));
		Ingredient ingredient = ingredientIdToIngredient.get(3L);
		Long ingredientId = ingredient.getId();

		when(ingredientRepository.findAllByRecipeIdAndId(anyLong(), anyLong())).thenReturn(ingredient);

		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

		assertEquals(3L, ingredientCommand.getId().longValue());
	}
}