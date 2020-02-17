package ar.com.wapp.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.wapp.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
	List<Comentario> findByTarjeta_IdOrderByFechaCreacionDesc(int id);

}
