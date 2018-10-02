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

import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;



@Controller
@RequestMapping(path="/Products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(path="/List")
	public @ResponseBody Iterable<Product> getAllProducts() {
		Iterable<Product> product = productRepository.findAll();
		return product;
	}
	@GetMapping("/Get") 
	public @ResponseBody Optional<Product> getProduct(@RequestParam int id) { 
		Optional<Product> product = productRepository.findById(id);
		return product; 
		
	}
	
	@PostMapping("/Add") 
	public @ResponseBody Product addProduct(@RequestBody Product product) { 
		return productRepository.save(product);
	}
	
	@PostMapping("/Change")
	public @ResponseBody Product updateProduct(@RequestBody Product product) { 
		return productRepository.save(product);
	}
	
	@PostMapping("/Remove")
	public @ResponseBody String removeProduct(@RequestBody Product product) { 
		productRepository.delete(product);
		return product +" deleted";
	}
}
