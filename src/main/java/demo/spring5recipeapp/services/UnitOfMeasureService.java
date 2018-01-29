package demo.spring5recipeapp.services;

import demo.spring5recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
}
