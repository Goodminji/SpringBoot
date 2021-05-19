package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistractionController {
	
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	
	public RegistractionController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistraiton(RegistrationForm form) {
		System.out.println(passwordEncoder);
		System.out.println(form.getUsername());
		System.out.println(form.getPassword());
		System.out.println(form.getFullname());
		System.out.println(form.getStreet());
		System.out.println(form.getState());
		System.out.println(form.getCity());
		System.out.println(form.getPhone());
		System.out.println(form.getZip());
		
		userRepo.save(form.toUser(passwordEncoder));
		return "redirect:/login";
	}

}
