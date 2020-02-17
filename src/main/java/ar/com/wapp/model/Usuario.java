package ar.com.wapp.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({ "accountNonExpired", "accountNonLocked", "accountNonLocked", "roles", "authorities",
		"credentialsNonExpired" })
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = -2799874789646240324L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUser;

	@NotBlank(message = "Ingrese un nombre")
	@Size(min = 2, max = 25, message = "El nombre debe tener entre 2 y 25 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre no puede contener espacios o numeros.")
	@Column(length = 150, nullable = false)
	private String firstName;

	@NotBlank(message = "Ingrese un apellido")
	@Size(min = 2, max = 25, message = "El apellido debe tener entre 3 y 25 caracteres")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "El apellido no puede contener espacios o numeros.")
	@Column(length = 150, nullable = false)
	private String lastName;

	@Email(message = "Inserte un mail valido")
	@Column(length = 255, unique = true, nullable = false)
	private String email;

	@NotBlank(message = "Ingrese un nombre de usuario")
	@Size(min = 3, max = 25, message = "El nombre de usuario debe tener entre 3 y 25 caracteres")
	@Pattern(regexp = "^\\S*$", message = "El nombre de usuario no puede contener espacios.")
	@Column(length = 70, unique = true, nullable = false)
	private String username;

	@NotBlank(message = "Ingrese una contraseña")
	@Column(length = 255, nullable = false)
	private String password;

	@Column(columnDefinition = "tinyint(4) default '1'")
	private boolean enabled;

	@Column(columnDefinition = "tinyint(4) default '1'")
	private boolean accountNonExpired;
	@Column(columnDefinition = "tinyint(4) default '1'")
	private boolean accountNonLocked;
	@Column(columnDefinition = "tinyint(4) default '1'")
	private boolean credentialsNonExpired;
	

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuariosroles", joinColumns = {
			@JoinColumn(name = "id_user", referencedColumnName = "idUser") }, inverseJoinColumns = {
					@JoinColumn(name = "id_rol", referencedColumnName = "id") })
	private Set<Rol> roles;

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRol())).collect(Collectors.toList());
	}


};
