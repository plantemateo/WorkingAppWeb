package ar.com.wapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.com.wapp.model.Tablero;

@Entity
@Table(name = "listas")
public class Lista implements Serializable {

	private static final long serialVersionUID = 435251326740500384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int posicion;

	@Column(length = 70, nullable = false)
	private String titulo;

	@ManyToOne
	@JoinColumn(name = "id_tablero")
	private Tablero tablero;

	@OneToMany(mappedBy = "lista", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();

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

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

}