package ar.com.wapp.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.com.wapp.business.BusinessException;
import ar.com.wapp.business.IComentarioBusiness;
import ar.com.wapp.business.IListaBusiness;
import ar.com.wapp.business.ITableroBusiness;
import ar.com.wapp.business.ITarjetaBusiness;
import ar.com.wapp.business.NotFoundException;
import ar.com.wapp.model.Comentario;
import ar.com.wapp.model.Lista;
import ar.com.wapp.model.Tablero;
import ar.com.wapp.model.Tarjeta;
import ar.com.wapp.web.Constantes;

@RestController
public class TableroRestService extends BaseRestService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ITableroBusiness tableroBusiness;

	@Autowired
	private IListaBusiness listaBusiness;

	@Autowired
	private ITarjetaBusiness tarjetaBusiness;

	@Autowired
	private IComentarioBusiness comentarioBusiness;

	// Tablero
	@GetMapping(Constantes.URL_TABLEROS)
	public ResponseEntity<List<Tablero>> list() {
		try {
			return new ResponseEntity<List<Tablero>>(tableroBusiness.list(), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@GetMapping(Constantes.URL_TABLEROS + "/{id}")
	public ResponseEntity<Tablero> load(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Tablero>(tableroBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		} catch (NotFoundException e) {
			return new ResponseEntity<Tablero>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(Constantes.URL_TABLEROS)
	public ResponseEntity<Tablero> add(@Valid @RequestBody Tablero tablero) {
		try {
			return new ResponseEntity<Tablero>(tableroBusiness.add(tablero), HttpStatus.CREATED);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@PutMapping(Constantes.URL_TABLEROS)
	public ResponseEntity<Tablero> update(@Valid @RequestBody Tablero tablero) {
		try {
			return new ResponseEntity<Tablero>(tableroBusiness.update(tablero), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@DeleteMapping(Constantes.URL_TABLEROS + "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		try {
			tableroBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	// Lista
	@GetMapping(Constantes.URL_LIST_LISTAS)
	public ResponseEntity<List<Lista>> listListas(@PathVariable("idTablero") int idTablero) {
		try {
			return new ResponseEntity<List<Lista>>(listaBusiness.list(idTablero), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@GetMapping(Constantes.URL_LISTAS + "/{id}")
	public ResponseEntity<Lista> loadLista(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Lista>(listaBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		} catch (NotFoundException e) {
			return new ResponseEntity<Lista>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(Constantes.URL_LISTAS)
	public ResponseEntity<Lista> addLista(@Valid @RequestBody Lista lista) {
		try {
			return new ResponseEntity<Lista>(listaBusiness.add(lista), HttpStatus.CREATED);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@PutMapping(Constantes.URL_LISTAS)
	public ResponseEntity<Lista> updateLista(@Valid @RequestBody Lista lista) {
		try {
			return new ResponseEntity<Lista>(listaBusiness.update(lista), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@DeleteMapping(Constantes.URL_LISTAS + "/{id}")
	public ResponseEntity<String> deleteLista(@PathVariable("id") int id) {
		try {
			listaBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	// Tarjeta
	@GetMapping(Constantes.URL_LIST_TARJETAS)
	public ResponseEntity<List<Tarjeta>> listTarjetas(@PathVariable("idLista") int idLista) {
		try {
			return new ResponseEntity<List<Tarjeta>>(tarjetaBusiness.list(idLista), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@GetMapping(Constantes.URL_TARJETAS + "/{id}")
	public ResponseEntity<Tarjeta> loadTarjeta(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Tarjeta>(tarjetaBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Tarjeta>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@PostMapping(Constantes.URL_TARJETAS)
	public ResponseEntity<Tarjeta> addTarjeta(@Valid @RequestBody Tarjeta tarjeta) {
		try {
			return new ResponseEntity<Tarjeta>(tarjetaBusiness.add(tarjeta), HttpStatus.CREATED);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@PutMapping(Constantes.URL_TARJETAS)
	public ResponseEntity<Tarjeta> updateTarjeta(@Valid @RequestBody Tarjeta tarjeta) {
		try {
			return new ResponseEntity<Tarjeta>(tarjetaBusiness.update(tarjeta), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@DeleteMapping(Constantes.URL_TARJETAS + "/{id}")
	public ResponseEntity<String> deleteTarjeta(@PathVariable("id") int id) {
		try {
			tarjetaBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	// Comentario
	@GetMapping(Constantes.URL_LIST_COMENTARIOS)
	public ResponseEntity<List<Comentario>> listComentarios(@PathVariable("idTarjeta") int idTarjeta) {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioBusiness.list(idTarjeta), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@GetMapping(Constantes.URL_COMENTARIOS + "/{id}")
	public ResponseEntity<Comentario> loadComentario(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<Comentario>(comentarioBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		} catch (NotFoundException e) {
			return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(Constantes.URL_COMENTARIOS)
	public ResponseEntity<Comentario> addComentario(@Valid @RequestBody Comentario comentario) {
		try {
			return new ResponseEntity<Comentario>(comentarioBusiness.add(comentario), HttpStatus.CREATED);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@PutMapping(Constantes.URL_COMENTARIOS)
	public ResponseEntity<Comentario> updateComentario(@Valid @RequestBody Comentario comentario) {
		try {
			return new ResponseEntity<Comentario>(comentarioBusiness.update(comentario), HttpStatus.OK);
		} catch (BusinessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}

	@DeleteMapping(Constantes.URL_COMENTARIOS + "/{id}")
	public ResponseEntity<String> deleteComentario(@PathVariable("id") int id) {
		try {
			comentarioBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), e);
		}
	}
}
