package ar.com.wapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.wapp.model.Usuario;
import ar.com.wapp.model.Lista;
import ar.com.wapp.model.Comentario;

@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {

	private static final long serialVersionUID = -629220298374330670L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int posicion;

	@Column(length = 2500, nullable = false)
	private String titulo;

	@Column(length = 10000)
	private String cuerpo;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario creadoPor;

	@ManyToMany
	@JoinTable(name = "miembros_asignados", joinColumns = {
			@JoinColumn(name = "id_tarjeta", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_usuario", referencedColumnName = "idUser") })
	private Set<Usuario> miembrosAsignados;

	@ManyToOne
	@JoinColumn(name = "id_lista")
	private Lista lista;

	@OneToMany(mappedBy = "tarjeta", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	private Date fechaCreacion = new Date();
	private Date fechaUltimaModificacion = new Date();

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Set<Usuario> getMiembrosAsignados() {
		return miembrosAsignados;
	}

	public void setMiembrosAsignados(Set<Usuario> miembrosAsignados) {
		this.miembrosAsignados = miembrosAsignados;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

}