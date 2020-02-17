package ar.com.wapp.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.wapp.model.Tarjeta;
import ar.com.wapp.model.persistence.TarjetaRepository;
import ar.com.wapp.model.Usuario;

@Service
public class TarjetaBusiness implements ITarjetaBusiness {

	@Autowired
	private TarjetaRepository tarjetaDAO;

	@Override
	public List<Tarjeta> list(int idLista) throws BusinessException {
		try {
			return tarjetaDAO.findByLista_IdOrderByPosicionAsc(idLista);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Tarjeta load(int id) throws BusinessException, NotFoundException {
		Optional<Tarjeta> o;
		try {
			o = tarjetaDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		if (!o.isPresent())
			throw new NotFoundException("No se encuentra el tablero con id=" + id);
		return o.get();
	}

	@Override
	public Tarjeta add(Tarjeta tarjeta) throws BusinessException {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			tarjeta.setCreadoPor((Usuario) auth.getPrincipal());
			return tarjetaDAO.save(tarjeta);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try {
			tarjetaDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Tarjeta update(Tarjeta tarjeta) throws BusinessException {
		try {
			return tarjetaDAO.save(tarjeta);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}