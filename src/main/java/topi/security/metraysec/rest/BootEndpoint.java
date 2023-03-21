package topi.security.metraysec.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import topi.security.metraysec.contr.BootRepository;
import topi.security.metraysec.domein.Boot;

@RestController
public class BootEndpoint {
	@Autowired
	BootRepository br;

	@CrossOrigin
	@GetMapping("hoi")
	public String go() {
		System.out.println("hoi");
		br.save(new Boot());
		return "het is goed gegaan";
	}
}
