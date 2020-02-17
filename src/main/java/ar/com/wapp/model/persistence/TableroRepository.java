package ar.com.wapp.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.wapp.model.Tablero;

@Repository
public interface TableroRepository extends JpaRepository<Tablero, Integer> {
	public List<Tablero> findByTitulo(String titulo);

	public List<Tablero> findByCreador_Username(String username);

	@Query(value = "SELECT t.* FROM tableros t INNER JOIN miembros m ON t.id = m.id_tablero WHERE m.id_usuario = ?;", nativeQuery = true)
	public List<Tablero> findByMiembro_Id(long id_usuario);
}
