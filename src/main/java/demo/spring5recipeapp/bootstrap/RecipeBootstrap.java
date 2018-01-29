package demo.spring5recipeapp.bootstrap;

import demo.spring5recipeapp.repositories.CategoryRepository;
import demo.spring5recipeapp.repositories.RecipeRepository;
import demo.spring5recipeapp.repositories.UnitOfMeasureRepository;
import demo.spring5recipeapp.domain.Category;
import demo.spring5recipeapp.domain.Difficulty;
import demo.spring5recipeapp.domain.Ingredient;
import demo.spring5recipeapp.domain.Notes;
import demo.spring5recipeapp.domain.Recipe;
import demo.spring5recipeapp.domain.UnitOfMeasure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Iterable<Recipe> recipes = recipeRepository.saveAll(getRecipes());
		log.debug("Loading bootstrap data");
	}

	private List<Recipe> getRecipes() {

		List<Recipe> recipes = new ArrayList<>(2);

		// get units of measure
		Optional<UnitOfMeasure> each = unitOfMeasureRepository.findByDescription("Each");
		if (!each.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
		if (!tablespoon.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
		if (!teaspoon.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<UnitOfMeasure> dash = unitOfMeasureRepository.findByDescription("Dash");
		if (!dash.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<UnitOfMeasure> pint = unitOfMeasureRepository.findByDescription("Pint");
		if (!pint.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");
		if (!cup.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		// get optionals
		UnitOfMeasure eachOum = each.get();
		UnitOfMeasure tablespoonOum = tablespoon.get();
		UnitOfMeasure teaspoonOum = teaspoon.get();
		UnitOfMeasure dashOum = dash.get();
		UnitOfMeasure pintOum = pint.get();
		UnitOfMeasure cupOum = cup.get();

		// get categories
		Optional<Category> americanOpt = categoryRepository.findByDescription("American");
		if (!americanOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Optional<Category> mexicanOpt = categoryRepository.findByDescription("Mexican");
		if (!mexicanOpt.isPresent()) {
			throw new RuntimeException("Expected UOM not found");
		}

		Category american = americanOpt.get();
		Category mexican = mexicanOpt.get();

		// Yummy Guacamole
		Recipe guacamoleRecipe = new Recipe();
		guacamoleRecipe.setDescription("Perfect Guacamole");
		guacamoleRecipe.setPrepTime(10);
		guacamoleRecipe.setCookTime(0);
		guacamoleRecipe.setServings(4);
		guacamoleRecipe.setSource("Simply Recipes");
		guacamoleRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
		guacamoleRecipe.setDirections(getDirectionsForGuacamole());

		// set ingredients
		Stream<Ingredient> ingredientForGuacamole = getIngredientsForGuacamole(eachOum, tablespoonOum, teaspoonOum, dashOum);
		ingredientForGuacamole.forEach(guacamoleRecipe::addIngredient);

//		guacamoleRecipe.setImage(guacamoleImage);

		guacamoleRecipe.setDifficulty(Difficulty.EASY);
		guacamoleRecipe.setNotes(getNotesForGuacamole(guacamoleRecipe));

		// set categories
		guacamoleRecipe.getCategories().add(american);
		guacamoleRecipe.getCategories().add(mexican);

		// add to recipes
		recipes.add(guacamoleRecipe);

		/////////////////////////////////////////////////////////////
		// yummy tacos
		Recipe tacosRecipe = new Recipe();
		tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setCookTime(9);
		tacosRecipe.setServings(4);
		tacosRecipe.setSource("Simply Recipes");
		tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		tacosRecipe.setDirections(getDirectionsForTacos());

		// set ingredients
		Stream<Ingredient> ingredientsForTacos = getIngredientsForTacos(eachOum, tablespoonOum, teaspoonOum, dashOum, cupOum, pintOum);
		ingredientsForTacos.forEach(tacosRecipe::addIngredient);

//		tacosRecipe.setImage(tacosImage);

		tacosRecipe.setDifficulty(Difficulty.MODERATE);
		tacosRecipe.setNotes(getNotesForTacos(tacosRecipe));


		// set categories
		tacosRecipe.getCategories().add(american);
		tacosRecipe.getCategories().add(mexican);

		// add to recipes
		recipes.add(tacosRecipe);

		return recipes;
	}

	private Stream<Ingredient> getIngredientsForGuacamole(UnitOfMeasure eachOum, UnitOfMeasure tablespoonOum, UnitOfMeasure teaspoonOum, UnitOfMeasure dashOum) {
		return Stream.of(new Ingredient("ripe avocados", new BigDecimal(2), eachOum),
				new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoonOum),
				new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoonOum),
				new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoonOum),
				new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachOum),
				new Ingredient("Cilantro", new BigDecimal(2), tablespoonOum),
				new Ingredient("freshly grated black pepper", new BigDecimal(2), dashOum),
				new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachOum)
		);
	}

	private Stream<Ingredient> getIngredientsForTacos(UnitOfMeasure eachOum, UnitOfMeasure tablespoonOum, UnitOfMeasure teaspoonOum, UnitOfMeasure dashOum, UnitOfMeasure cupsOum, UnitOfMeasure pintOum) {
		return Stream.of(
				new Ingredient("Sugar", new BigDecimal(1), teaspoonOum),
				new Ingredient("Salt", new BigDecimal(".5"), teaspoonOum),
				new Ingredient("Olive Oil", new BigDecimal(2), tablespoonOum),
				new Ingredient("packed baby arugula", new BigDecimal(3), cupsOum),
				new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachOum),
				new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachOum),
				new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintOum),
				new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachOum),
				new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachOum),
				new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsOum),
				new Ingredient("lime, cut into wedges", new BigDecimal(4), eachOum));
	}

	private String getDirectionsForGuacamole() {
		return "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
				"\n" +
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
				"\n" +
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
				"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
				"\n" +
				"\n" +
				"Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd";
	}

	private String getDirectionsForTacos() {
		return "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
				"\n" +
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
				"\n" +
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
				"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
				"\n" +
				"\n" +
				"Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd";
	}

	private Notes getNotesForGuacamole(Recipe guacamoleRecipe) {
		Notes guacamoleNotes = new Notes();
		guacamoleNotes.setRecipe(guacamoleRecipe);
		guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
				"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
				"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
				"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
				"\n" +
				"\n" +
				"Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
		return guacamoleNotes;
	}

	private Notes getNotesForTacos(Recipe tacosRecipe) {
		Notes tacosNotes = new Notes();
		tacosNotes.setRecipe(tacosRecipe);
		tacosNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
				"Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
				"Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
				"First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
				"Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
				"\n" +
				"\n" +
				"Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
		return tacosNotes;
	}
}
