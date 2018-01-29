package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.UnitOfMeasureCommand;
import demo.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import demo.spring5recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private UnitOfMeasureRepository unitOfMeasureRepository;
	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		Set<UnitOfMeasureCommand> unitOfMeasureSet = unitOfMeasureRepository.findAll().stream().map(unitOfMeasure -> unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)).collect(Collectors.toSet());
		return unitOfMeasureSet;
	}
}
