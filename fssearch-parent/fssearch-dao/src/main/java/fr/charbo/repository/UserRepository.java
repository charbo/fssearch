package fr.charbo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.charbo.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
