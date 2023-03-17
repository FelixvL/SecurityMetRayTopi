package topi.security.metraysec.contr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import topi.security.metraysec.security.Authority;
import topi.security.metraysec.security.AuthorityName;
import topi.security.metraysec.security.User;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class InitService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @PostConstruct
    public void initUsers() {


        this.userRepository.deleteAll();
        this.authorityRepository.deleteAll();

        User user = new User();
        user.setUsername("johndoe"); // not to be confused with the user accessing the DB
        user.setPassword("$2a$04$mOcweZoue3.bVKiRrpPU8u1e734k2v1C0F5r8yOKYj2x5a1RrjR/O"); // password2019 bcrypted
        this.userRepository.save(user);

        user = new User();
        user.setUsername("janedoe");
        user.setPassword("$2a$04$mOcweZoue3.bVKiRrpPU8u1e734k2v1C0F5r8yOKYj2x5a1RrjR/O"); // password2019 bcrypted
        this.userRepository.save(user);

        initAuthorities();
    }

    private void initAuthorities() {
        // John has authority USER and ADMIN
        User user = this.userRepository.findByUsername("johndoe");
        for (AuthorityName authorityName : AuthorityName.values()) {
            Authority authority = new Authority();
            authority.setName(authorityName);
            user.getAuthorities().add(authority);
        }
        this.userRepository.save(user);
        // Jane has authority USER
        user = this.userRepository.findByUsername("janedoe");
        Authority authority = this.authorityRepository.findByName(AuthorityName.USER);
        user.getAuthorities().add(authority);
        this.userRepository.save(user);
    }
}
