package demo.spring5recipeapp.repositories;

import demo.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIntTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findByDescription() {
		Optional<Category> tablespoon = categoryRepository.findByDescription("American");

		assertEquals("American", tablespoon.get().getDescription());
	}
}