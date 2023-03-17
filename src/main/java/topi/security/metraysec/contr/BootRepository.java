package topi.security.metraysec.contr;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import topi.security.metraysec.domein.Boot;


@Component
public interface BootRepository extends CrudRepository<Boot, Long>{

}

