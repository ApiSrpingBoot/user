package ec.llima.springcloud.ms.users.services;

import java.util.Optional;

import ec.llima.springcloud.ms.users.entities.User;

public interface UserService {

    Iterable<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);
    
    Optional<User> update(User user, Long id);

    void deleteById(Long id);

    Optional<User> findByLogin(String login);

    User disabledUser(Long id);
}