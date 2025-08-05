package ec.llima.springcloud.ms.users.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ec.llima.springcloud.ms.users.entities.User;
import ec.llima.springcloud.ms.users.entities.Role;
import ec.llima.springcloud.ms.users.repositories.RoleRepository;
import ec.llima.springcloud.ms.users.repositories.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    final private UserRepository repository; 
    final private RoleRepository roleRepository;
    final private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(getOptionalRole(user));
        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        Optional<User> optionalUser = this.findById(id);
        return optionalUser.map(userFound -> {
            userFound.setLogin(user.getLogin()); 
            userFound.setFirstname(user.getFirstname());
            userFound.setLastname(user.getLastname());
            userFound.setMail(user.getMail());
            if (user.isActive() != null) {
                userFound.setActive(user.isActive());
            }
            userFound.setRoles(getOptionalRole(user));
            return Optional.of(repository.save(userFound));
        }).orElseGet(() -> Optional.empty());
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


    @Override
    public User disabledUser(Long id) {
        Optional<User> optionalUser = this.findById(id);
        return optionalUser.map(userFound -> {
            userFound.setActive(false);
            return repository.save(userFound);
        }).orElseGet(() -> null);
    }
    
    private List<Role> getOptionalRole(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = this.roleRepository.findByName("ROLE_USER");
        optionalRole.ifPresent(role -> {
            roles.add(role);
        });

        if(user.getAdmin() != null && user.getAdmin()) {
            Optional<Role> optionalAdminRole = this.roleRepository.findByName("ROLE_ADMIN");
            optionalAdminRole.ifPresent(role -> {
                roles.add(role);
            });
        }

        return roles;
    }

}

