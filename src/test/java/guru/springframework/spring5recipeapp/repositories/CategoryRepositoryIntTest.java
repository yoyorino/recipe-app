package guru.springframework.spring5recipeapp.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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
//		Optional<Category> tablespoon = categoryRepository.findByDescription("Tablespoon");
//		assertEquals("Tablespoon", tablespoon.get().getDescription());
	}
}