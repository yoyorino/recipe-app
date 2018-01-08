package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

	private final RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping(value = {"/", "", "/index"})
	public String getIndexPage(Model model) {
		log.debug("Received GET request onto index page");

		model.addAttribute("recipes", recipeService.getRecipes());
		System.out.println();
		return "index";
	}
}
