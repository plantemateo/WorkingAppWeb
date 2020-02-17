package ar.com.wapp.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.wapp.model.Tablero;
import ar.com.wapp.model.Usuario;
import ar.com.wapp.model.persistence.TableroRepository;

@Service
public class TableroBusiness implements ITableroBusiness {

	@Autowired
	private TableroRepository tableroDAO;

	@Override
	public List<Tablero> list() throws BusinessException {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return tableroDAO.findByMiembro_Id(((Usuario) auth.getPrincipal()).getIdUser());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Tablero load(int id) throws BusinessException, NotFoundException {
		Optional<Tablero> o;
		try {
			o = tableroDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		if (!o.isPresent())
			throw new NotFoundException("No se encuentra el tablero con id=" + id);
		return o.get();
	}

	@Override
	public Tablero add(Tablero tablero) throws BusinessException {
		try {
			return tableroDAO.save(tablero);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try {
			tableroDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Tablero update(Tablero tablero) throws BusinessException {
		try {
			return tableroDAO.save(tablero);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
