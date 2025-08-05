package ec.llima.springcloud.ms.users.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.llima.springcloud.ms.users.entities.User;
import ec.llima.springcloud.ms.users.services.UserService;

@RestController // este es un controlador rest
@RequestMapping("/users")
public class UserController {
    
    final private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getList() {
        return ResponseEntity.ok(this.service.findAll());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<User> getDetails(@PathVariable Long id) {
        Optional<User> optionalUser = this.service.findById(id);
        return optionalUser.map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<User> getDetails(@PathVariable String login) {
        Optional<User> optionalUser = this.service.findByLogin(login);
        return optionalUser.map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<User> create(@RequestBody User user) {
            User userSaved = this.service.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> optionalUser = this.service.findById(id);
        return optionalUser.map(userFound -> {
            userFound.setLogin(user.getLogin()); 
            userFound.setFirstname(user.getFirstname());
            userFound.setLastname(user.getLastname());
            userFound.setMail(user.getMail());
            userFound.setPassword(user.getPassword());
            userFound.setActive(user.isActive());
            // Actualizar otros campos según sea necesario 
            User userUpdated = this.service.update(userFound);
            return ResponseEntity.status(HttpStatus.CREATED).body(userUpdated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<User> optionalUser = this.service.findById(id);
        if (optionalUser.isPresent()){
            this.service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build(); 
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<User> disable(@PathVariable Long id) {
    return this.service.findById(id)
        .map(user -> {
            user.setActive(false);
            return ResponseEntity.ok(this.service.update(user));
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

