package topi.security.metraysec.config.jwt;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {
    private String username;
    private String password;
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
