package com.prs.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	@PostMapping(path="/SubmitForReview") 
	public @ResponseBody PurchaseRequest submitForReview (@RequestBody PurchaseRequest pr) {
		if (pr.getTotal()<=50)
			pr.setStatus(PurchaseRequest.STATUS_APPROVED);
		else
			pr.setStatus(PurchaseRequest.STATUS_REVIEW);
		pr.setSubmittedDate(LocalDate.now());
		return prRepository.save(pr);
	}
	@PostMapping(path="/ApprovePR") 
	public @ResponseBody PurchaseRequest approvePR(@RequestBody PurchaseRequest pr) {
		pr.setStatus(PurchaseRequest.STATUS_APPROVED);
		return prRepository.save(pr);
	}
	@PostMapping(path="/RejectPR") 
	public @ResponseBody PurchaseRequest rejectPR (@RequestBody PurchaseRequest pr) {
		pr.setStatus(PurchaseRequest.STATUS_REJECTED);
		return prRepository.save(pr);
	}
	@GetMapping(path="/Remove") 
	public @ResponseBody String deletePurchaseRequest(@RequestParam int id) {
		Optional<PurchaseRequest> pr = prRepository.findById(id);
		prRepository.delete(pr.get());
		return "Purchase Request Deleted";
	}
}