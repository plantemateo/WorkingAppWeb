package ar.com.wapp.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.wapp.model.AuthToken;

@Repository
public interface AuthTokenRespository extends JpaRepository<AuthToken, String> {

}
