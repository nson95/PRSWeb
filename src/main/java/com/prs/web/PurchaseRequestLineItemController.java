package com.prs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.prs.business.product.Product;
import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;

@CrossOrigin
@Controller
@RequestMapping(path="/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {

		@Autowired
		private PurchaseRequestLineItemRepository prliRepository;
		@Autowired
		private PurchaseRequestRepository prRepository;
		
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
			try {
				updateRequestTotal(purchaseRequestLineItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return prliRepository.save(purchaseRequestLineItem);
		}
		
		@PostMapping("/Change")
		public @ResponseBody PurchaseRequestLineItem updatePurchaseRequest(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			try {
				updateRequestTotal(purchaseRequestLineItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return prliRepository.save(purchaseRequestLineItem);
		}
		
		@GetMapping(path="/Remove") 
		public @ResponseBody String deletePurchaseRequestLineItem(@RequestParam int id) {
			Optional<PurchaseRequestLineItem> prli = prliRepository.findById(id);
			prliRepository.delete(prli.get());
			return "Line item was deleted";
		}
		private void updateRequestTotal(PurchaseRequestLineItem prli) throws Exception {
			Optional<PurchaseRequest> purReq = prRepository.findById(prli.getPurchaseRequest().getId());
			
			PurchaseRequest pr = purReq.get();
			List<PurchaseRequestLineItem> lines = new ArrayList<>();
			lines = prliRepository.findAllByPurchaseRequestId(pr.getId());
			double total = 0;
			for (PurchaseRequestLineItem line: lines) {
				Product p = line.getProduct();
				double lineTotal = line.getQuantity()*p.getPrice();
				total += lineTotal;
			}
			pr.setTotal(total);
			prRepository.save(pr);
		}
}

