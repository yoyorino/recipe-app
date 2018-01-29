package demo.spring5recipeapp.controllers;

import demo.spring5recipeapp.services.UnitOfMeasureService;
import demo.spring5recipeapp.commands.IngredientCommand;
import demo.spring5recipeapp.converters.RecipeCommandToRecipe;
import demo.spring5recipeapp.services.IngredientService;
import demo.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

	private IngredientService ingredientService;
	private RecipeService recipeService;
	private RecipeCommandToRecipe recipeCommandToRecipe;
	private UnitOfMeasureService unitOfMeasureService;

	public IngredientController(IngredientService ingredientService, RecipeService recipeService, RecipeCommandToRecipe recipeCommandToRecipe, UnitOfMeasureService unitOfMeasureService) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.unitOfMeasureService = unitOfMeasureService;
	}


	@GetMapping("recipe/{recipeId}/ingredients")
	public String getIngredientsForRecipe(@PathVariable Long recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand saveCommand = ingredientService.saveIngredientCommand(ingredientCommand);

		log.debug("Saved recipe id: " + saveCommand.getRecipeId());
		log.debug("save ingredient id: " + saveCommand.getId());

		return "redirect:/recipe/" + saveCommand.getRecipeId() + "/ingredient/" +saveCommand.getId() + "/show";
	}
}
