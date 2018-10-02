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

import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;


@Controller
@RequestMapping(path="/PurchaseRequests")
public class PurchaseRequestController {
	@Autowired
	private PurchaseRequestRepository prRepository;
	
	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
		Iterable<PurchaseRequest> purchaseRequest = prRepository.findAll();
		return purchaseRequest;
	}
	@GetMapping("/Get") 
	public @ResponseBody Optional<PurchaseRequest> getPurchaseRequest(@RequestParam int id) { 
		Optional<PurchaseRequest> purchaseRequest = prRepository.findById(id);
		return purchaseRequest; 
		
	}
	
	@PostMapping("/Add") 
	public @ResponseBody PurchaseRequest addPurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) { 
		return prRepository.save(purchaseRequest);
	}
	
	@PostMapping("/Change")
	public @ResponseBody PurchaseRequest updatePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) { 
		return prRepository.save(purchaseRequest);
	}
	
	@PostMapping("/Remove")
	public @ResponseBody String removePurchaseRequest(@RequestBody PurchaseRequest purchaseRequest) { 
		prRepository.delete(purchaseRequest);
		return "Request deleted";
	}
}