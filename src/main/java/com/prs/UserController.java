package com.prs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/prs")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/add") 
	public @ResponseBody String addNewUser (@RequestParam String userName,
											@RequestParam String password,
											@RequestParam String firstName,
											@RequestParam String lastName,
											@RequestParam String phoneNumber,
											@RequestParam String email) {
		
		User u = new User();
		u.setUserName(userName);
		u.setPassword(password);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setPhoneNumber(phoneNumber);
		u.setEmail(email);
		userRepository.save(u);
		return "Saved";
	}
	
	@GetMapping(path="/list")
	public @ResponseBody Iterable<User> getAllUsers() {
	
		return userRepository.findAll();
	}
	


}
