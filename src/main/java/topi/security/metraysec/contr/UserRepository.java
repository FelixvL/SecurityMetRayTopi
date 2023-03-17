package topi.security.metraysec.contr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import topi.security.metraysec.security.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
