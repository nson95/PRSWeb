package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;


@Controller
@RequestMapping(path="/Users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/List")
	public @ResponseBody Iterable<User> getAllUsers() {
		Iterable<User> users = userRepository.findAll();
		return users;
	}
	@GetMapping("/Get") //get method calling "users/get..." and then the parameters http://localhost:8080/Users/Get?id=2  saying you are getting the second user in your user table
	public @ResponseBody Optional<User> getUser(@RequestParam int id) { //requesting parameters is indicated by "?"
		Optional<User> user = userRepository.findById(id); //returning an Optional type
		return user; 
		
	}
	
	@PostMapping("/Add") //pass in a JSON object to user and return the object that was created
	public @ResponseBody User addUser(@RequestBody User user) { 
		return userRepository.save(user);
	}
	
	@PostMapping("/Change") //updating a user
	public @ResponseBody User updateUser(@RequestBody User user) { 
		return userRepository.save(user);
	}
	
	@PostMapping("/Remove") //delete a user
	public @ResponseBody String removeUser(@RequestBody User user) { 
		userRepository.delete(user);
		return "user deleted";
	}
}