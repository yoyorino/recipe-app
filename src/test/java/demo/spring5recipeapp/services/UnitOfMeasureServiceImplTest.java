package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.UnitOfMeasureCommand;
import demo.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import demo.spring5recipeapp.domain.UnitOfMeasure;
import demo.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {
	
	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	private UnitOfMeasureService unitOfMeasureService;
	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		this.unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void listAllUoms() {

		UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
		unitOfMeasure1.setId(1L);
		UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
		unitOfMeasure2.setId(2L);
		UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
		unitOfMeasure3.setId(3L);
		List<UnitOfMeasure> unitOfMeasures = new ArrayList<>(Stream.of(unitOfMeasure1, unitOfMeasure2, unitOfMeasure3).collect(Collectors.toList()));
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

		Set<UnitOfMeasureCommand> savedUoms = unitOfMeasureService.listAllUoms();

		assertEquals(3, savedUoms.size());
	}
}