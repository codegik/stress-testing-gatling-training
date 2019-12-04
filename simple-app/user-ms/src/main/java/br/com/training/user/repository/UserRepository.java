package br.com.training.user.repository;

import br.com.training.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, Serializable> {

  public User findByEmail(String email);

  public User findByFirstNameAndLastName(String firstName, String lastName);
}
