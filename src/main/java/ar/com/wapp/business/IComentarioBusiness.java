package ar.com.wapp.business;

import java.util.List;
import ar.com.wapp.model.Comentario;

public interface IComentarioBusiness {

	public List<Comentario> list(int idTarjeta) throws BusinessException;

	public Comentario load(int id) throws BusinessException, NotFoundException;

	public Comentario add(Comentario comentario) throws BusinessException;

	public void delete(int id) throws BusinessException;

	public Comentario update(Comentario comentario) throws BusinessException;
}
