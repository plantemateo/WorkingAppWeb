package ar.com.wapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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

@Entity
@Table(name = "tableros")
public class Tablero implements Serializable {

	private static final long serialVersionUID = 2499231234310909738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 70, nullable = false)
	private String titulo;

	@Column(length = 200, nullable = false)
	private String fondo;

	@Column(nullable = false)
	private int posicion;

	@Column(nullable = false)
	private boolean marcado = false;

	@ManyToMany
	@JoinTable(name = "miembros", joinColumns = {
			@JoinColumn(name = "id_tablero", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_usuario", referencedColumnName = "idUser") })
	private Set<Usuario> miembros = new HashSet<Usuario>();

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario creador;

	@OneToMany(mappedBy = "tablero", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Lista> listas = new ArrayList<Lista>();

	private Date fechaCreacion = new Date();

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public Set<Usuario> getMiembros() {
		return miembros;
	}

	public void setMiembros(Set<Usuario> miembros) {
		this.miembros = miembros;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

}
