package ar.com.wapp.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.wapp.business.BusinessException;
import ar.com.wapp.business.IComentarioBusiness;
import ar.com.wapp.business.NotFoundException;
import ar.com.wapp.model.Comentario;
import ar.com.wapp.model.Usuario;
import ar.com.wapp.model.persistence.ComentarioRepository;

@Service
public class ComentarioBusiness implements IComentarioBusiness {

	@Autowired
	private ComentarioRepository comentarioDAO;

	@Override
	public List<Comentario> list(int idTarjeta) throws BusinessException {
		try {
			return comentarioDAO.findByTarjeta_IdOrderByFechaCreacionDesc(idTarjeta);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Comentario load(int id) throws BusinessException, NotFoundException {
		Optional<Comentario> o;
		try {
			o = comentarioDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		if (!o.isPresent())
			throw new NotFoundException("No se encuentra el comentario con id=" + id);
		return o.get();
	}

	@Override
	public Comentario add(Comentario comentario) throws BusinessException {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			comentario.setCreadoPor((Usuario) auth.getPrincipal());
			return comentarioDAO.save(comentario);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try {
			comentarioDAO.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Comentario update(Comentario comentario) throws BusinessException {
		try {
			return comentarioDAO.save(comentario);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}