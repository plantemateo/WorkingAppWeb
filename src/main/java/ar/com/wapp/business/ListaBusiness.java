package ar.com.wapp.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.wapp.model.Lista;
import ar.com.wapp.model.persistence.ListaRepository;

@Service
public class ListaBusiness implements IListaBusiness {

	@Autowired
	private ListaRepository listaDAO;

	@Override
	public List<Lista> list(int idTablero) throws BusinessException {
		try {
			return listaDAO.findByTablero_IdOrderByPosicionAsc(idTablero);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Lista load(int id) throws BusinessException, NotFoundException {
		Optional<Lista> o;
		try {
			o = listaDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		if (!o.isPresent())
			throw new NotFoundException("No se encuentra la lista con id=" + id);
		return o.get();
	}

	@Override
	public Lista add(Lista lista) throws BusinessException {
		try {
			listaDAO.actualizarPosicionesOnSave(lista.getTablero().getId(), lista.getPosicion());
			return listaDAO.save(lista);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try {
			Lista l = listaDAO.findById(id).get();
			listaDAO.deleteById(id);
			listaDAO.actualizarPosicionesOnDelete(l.getTablero().getId(), l.getPosicion());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Lista update(Lista lista) throws BusinessException {
		try {
			return listaDAO.save(lista);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
