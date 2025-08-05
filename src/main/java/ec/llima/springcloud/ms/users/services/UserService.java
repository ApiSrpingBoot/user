package ec.llima.springcloud.ms.users.services;

import java.util.Optional;

import ec.llima.springcloud.ms.users.entities.User;

public interface UserService {

    Iterable<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);
    
    User update(User user);

    void deleteById(Long id);

    Optional<User> findByLogin(String login);


}