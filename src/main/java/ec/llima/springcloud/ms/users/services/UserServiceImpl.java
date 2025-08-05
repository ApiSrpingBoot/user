package ec.llima.springcloud.ms.users.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ec.llima.springcloud.ms.users.entities.User;
import ec.llima.springcloud.ms.users.repositories.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    final private UserRepository repository; 

    public UserServiceImpl(UserRepository repository){
        this.repository=repository;
    }

    @Override
    @Transactional(readOnly = true) 
    public Iterable<User> findAll() {
        return ((List<User>) repository.findAll()).stream().map(user ->{
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(user->{
            return user;
        });
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }
    

}

