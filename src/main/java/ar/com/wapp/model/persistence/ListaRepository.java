package ar.com.wapp.model.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.wapp.model.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {
	List<Lista> findByTablero_IdOrderByPosicionAsc(int id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE listas SET posicion = posicion - 1 WHERE id_tablero = ? AND posicion >= ?;", nativeQuery = true)
	public void actualizarPosicionesOnDelete(int id_tablero, int posicion);

	@Modifying
	@Transactional
	@Query(value = "UPDATE listas SET posicion = posicion + 1 WHERE id_tablero = ? AND posicion >= ?;", nativeQuery = true)
	public void actualizarPosicionesOnSave(int id_tablero, int posicion);

}
