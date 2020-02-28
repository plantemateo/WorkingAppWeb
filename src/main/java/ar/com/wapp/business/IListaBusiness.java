package ar.com.wapp.business;

import java.util.List;

import ar.com.wapp.model.Lista;

public interface IListaBusiness {
	
	public List<Lista> list(int idTablero) throws BusinessException;

    public Lista load(int id) throws BusinessException, NotFoundException;

    public Lista add(Lista lista) throws BusinessException;

    public void delete(int id) throws BusinessException;

    public Lista update(Lista lista) throws BusinessException;

}
