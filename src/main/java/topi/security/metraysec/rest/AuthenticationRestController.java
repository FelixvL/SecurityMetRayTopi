package topi.security.metraysec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import topi.security.metraysec.config.jwt.JwtAuthenticationRequest;
import topi.security.metraysec.config.jwt.JwtAuthenticationResponse;
import topi.security.metraysec.config.jwt.JwtTokenUtil;
import topi.security.metraysec.config.jwt.JwtUser;
import topi.security.metraysec.contr.UserRepository;
import topi.security.metraysec.security.Authority;
import topi.security.metraysec.security.AuthorityName;
import topi.security.metraysec.security.User;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("${jwt.route.authentication.path}")
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        System.err.println(this.passwordEncoder.encode(authenticationRequest.getPassword()));

        User user = new User();
        user.setUsername(authenticationRequest.getUsername());
        final String encrypedPassword = this.passwordEncoder.encode(authenticationRequest.getPassword());
        user.setPassword(encrypedPassword);

        this.userRepository.save(user);

        for (AuthorityName authorityName : AuthorityName.values()) {
            Authority authority = new Authority();
            authority.setName(authorityName);
            user.getAuthorities().add(authority);
        }

        this.userRepository.save(user);

        System.out.printf("Created user:%s with password:%s which is bcrypted to: %s",
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword(),
                encrypedPassword);

        return ResponseEntity.ok().build();
    }
    
    // try to authenticate
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    // get the logged in user (JwtUser)
    @GetMapping("user")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }
}