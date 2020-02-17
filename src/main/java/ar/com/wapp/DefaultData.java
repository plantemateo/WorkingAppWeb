package ar.com.wapp;


import java.util.HashSet;
import java.util.List;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import ar.com.wapp.model.Rol;
import ar.com.wapp.model.Usuario;
import ar.com.wapp.model.persistence.RolRepository;
import ar.com.wapp.model.persistence.UsuarioRepository;

@Service
public class DefaultData {

	@Autowired
	private UsuarioRepository userDAO;
	@Autowired
	private RolRepository roleDAO;
	@Autowired
	private PasswordEncoder pe;

	public Usuario ensureUserIntegration() {
		List<Usuario> l = userDAO.findByUsername("integracion");
		if (l.size() == 0) {
			Usuario u = new Usuario();
			u.setAccountNonExpired(true);
			u.setAccountNonLocked(true);
			u.setCredentialsNonExpired(true);
			u.setEmail("integration@mail.com");
			u.setEnabled(true);
			u.setFirstName("Integracion");
			u.setLastName("System");
			u.setPassword(pe.encode("passintegracion"));
			u.setUsername("integracion");
			Set<Rol> sr = new HashSet<Rol>();
			sr.add(ensureRoleIntegration());
			u.setRoles(sr);
			return userDAO.save(u);
		} else {
			return l.get(0);
		}
	}

	public Usuario ensureUserGetToken() {
		List<Usuario> l = userDAO.findByUsername("gettoken");
		if (l.size() == 0) {
			Usuario u = new Usuario();
			u.setAccountNonExpired(true);
			u.setAccountNonLocked(true);
			u.setCredentialsNonExpired(true);
			u.setEmail("gettoken@mail.com");
			u.setEnabled(true);
			u.setFirstName("OnlyGetToken");
			u.setLastName("System");
			u.setPassword(pe.encode("g3tt0k3n$"));
			u.setUsername("gettoken");
			Set<Rol> sr = new HashSet<Rol>();
			sr.add(ensureRoleTokenRequest());
			u.setRoles(sr);
			return userDAO.save(u);
		} else {
			return l.get(0);
		}

	}

	public void ensureAllRoles() {
		ensureRoleIntegration();
		ensureRoleAdmin();
		ensureRoleUser();
		ensureRoleTokenRequest();
	}

	public Rol ensureRoleIntegration() {
		return ensureRole("ROLE_INTEGRATION", "Solo integración");
	}

	public Rol ensureRoleAdmin() {
		return ensureRole("ROLE_ADMIN", "Administrador");
	}

	public Rol ensureRoleUser() {
		return ensureRole("ROLE_USER", "Usuario");
	}

	public Rol ensureRoleTokenRequest() {
		return ensureRole("ROLE_TOKEN_REQUEST", "Para requerir tokens de integracion");
	}

	private Rol ensureRole(String role, String descripcion) {
		List<Rol> l = roleDAO.findByRol(role);
		if (l.size() == 0) {
			Rol r = new Rol();
			r.setRol(role);
			return roleDAO.save(r);
		} else {
			return l.get(0);
		}
	}

}