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

import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;


@Controller
@RequestMapping(path="/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {

		@Autowired
		private PurchaseRequestLineItemRepository prliRepository;
		
		@GetMapping(path="/List")
		public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems() {
			Iterable<PurchaseRequestLineItem> purchaseRequestLineItem = prliRepository.findAll();
			return purchaseRequestLineItem;
		}
		@GetMapping("/Get") 
		public @ResponseBody Optional<PurchaseRequestLineItem> getPurchaseRequestLineItem(@RequestParam int id) { 
			Optional<PurchaseRequestLineItem> purchaseRequestLineItem = prliRepository.findById(id);
			return purchaseRequestLineItem; 
		}
		
		@PostMapping("/Add") 
		public @ResponseBody PurchaseRequestLineItem addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			return prliRepository.save(purchaseRequestLineItem);
		}
		
		@PostMapping("/Change")
		public @ResponseBody PurchaseRequestLineItem updatePurchaseRequest(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			return prliRepository.save(purchaseRequestLineItem);
		}
		
		@PostMapping("/Remove")
		public @ResponseBody String removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			prliRepository.delete(purchaseRequestLineItem);
			return "RequestLineItem deleted";
		}
	
}

