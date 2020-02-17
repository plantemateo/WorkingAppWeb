package ar.com.wapp.business;

import java.util.List;

import ar.com.wapp.model.Tablero;

public interface ITableroBusiness {

	public List<Tablero> list() throws BusinessException;

	public Tablero load(int id) throws BusinessException, NotFoundException;

	public Tablero add(Tablero tablero) throws BusinessException;

	public void delete(int id) throws BusinessException;

	public Tablero update(Tablero tablero) throws BusinessException;

}
