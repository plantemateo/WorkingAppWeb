package ar.com.wapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.wapp.business.BusinessException;
import ar.com.wapp.business.IUsuarioBusiness;
import ar.com.wapp.business.NotFoundException;

@Service
public class PersistenceUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioBusiness usuarioBusiness;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			return usuarioBusiness.load(username);
		} catch (BusinessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}

	}

}
