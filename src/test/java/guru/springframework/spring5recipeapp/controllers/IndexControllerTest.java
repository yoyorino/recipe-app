package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

	private IndexController indexController;

	@Mock
	private RecipeService recipeService;

	@Mock
	private Model model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.indexController = new IndexController(recipeService);
	}

	@Test
	public void getIndexPage() {
		// given
		Set<Recipe> recipeSet = new HashSet<>();
		recipeSet.add(new Recipe());
		recipeSet.add(new Recipe());

		when(recipeService.getRecipes()).thenReturn(recipeSet);

		// when
		String indexPage = this.indexController.getIndexPage(model);

		// then
		assertEquals("index", indexPage);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute("recipes", recipeSet);
	}

	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}
}