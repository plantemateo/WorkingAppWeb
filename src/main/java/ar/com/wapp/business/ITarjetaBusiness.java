package ar.com.wapp.business;

import java.util.List;

import ar.com.wapp.model.Tarjeta;

public interface ITarjetaBusiness {
	
	public List<Tarjeta> list(int idLista) throws BusinessException;

	public Tarjeta load(int id) throws BusinessException, NotFoundException;

	public Tarjeta add(Tarjeta tarjeta) throws BusinessException;

	public void delete(int id) throws BusinessException;

	public Tarjeta update(Tarjeta tarjeta) throws BusinessException;

}
