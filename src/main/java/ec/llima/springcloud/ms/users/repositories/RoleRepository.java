package ec.llima.springcloud.ms.users.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import ec.llima.springcloud.ms.users.entities.Role;


public interface RoleRepository extends CrudRepository<Role, Long>{
    Optional<Role> findByName(String name);

}
