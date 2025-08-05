package ec.llima.springcloud.ms.users.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ec.llima.springcloud.ms.users.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
