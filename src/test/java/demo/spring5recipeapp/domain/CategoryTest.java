package demo.spring5recipeapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

	private Category category;

	@Before
	public void setUp() {
		this.category = new Category();
	}

	@Test
	public void getId() {
		Long categoryId = 4L;
		this.category.setId(categoryId);
		assertEquals(categoryId, this.category.getId());
	}

	@Test
	public void getDescription() {
	}

	@Test
	public void getRecipes() {
	}
}