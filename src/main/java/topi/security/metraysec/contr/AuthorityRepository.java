package topi.security.metraysec.contr;

import topi.security.metraysec.security.Authority;
import topi.security.metraysec.security.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(AuthorityName name);
}
