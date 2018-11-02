package com.prs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.prs.util.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path="/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {

		@Autowired
		private PurchaseRequestLineItemRepository prliRepository;
		@Autowired
		private PurchaseRequestRepository prRepository;
		
		@GetMapping("/List")
		public @ResponseBody JsonResponse getAllPurchaseRequestLineItem() {
			try {
				return JsonResponse.getInstance(prliRepository.findAll());
			} catch (Exception e) {
				return JsonResponse.getErrorInstance("User list failure:" + e.getMessage(), e);
			}
		}
		@GetMapping("/Get/{id}")
		public @ResponseBody JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
			try {
				Optional<PurchaseRequestLineItem> purchaseRequestLineItem = prliRepository.findById(id);
				if (purchaseRequestLineItem.isPresent())
					return JsonResponse.getInstance(purchaseRequestLineItem.get());
				else
					return JsonResponse.getErrorInstance("Purchase Request Line Item not found for id: " + id, null);
			} catch (Exception e) {
				return JsonResponse.getErrorInstance("Error getting purchase request line item:  " + e.getMessage(), null);

			}
		}
		private @ResponseBody JsonResponse savePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
			try {
				prliRepository.save(purchaseRequestLineItem);
				return JsonResponse.getInstance(purchaseRequestLineItem);
			} catch (DataIntegrityViolationException ex) {
				return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
			} catch (Exception ex) {
				return JsonResponse.getErrorInstance(ex.getMessage(), ex);
			}
		}
		@PostMapping("/Add") 
		public @ResponseBody JsonResponse addPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			try {
				updateRequestTotal(purchaseRequestLineItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return savePurchaseRequestLineItem(purchaseRequestLineItem);
		}
		@PostMapping("/Change")
		public @ResponseBody JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) { 
			try {
				updateRequestTotal(purchaseRequestLineItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return savePurchaseRequestLineItem(purchaseRequestLineItem);
		}
		@PostMapping("/Remove")
		public @ResponseBody JsonResponse removePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem purchaseRequestLineItem) {
			try {
				prliRepository.delete(purchaseRequestLineItem);
				return JsonResponse.getInstance(purchaseRequestLineItem);
			} catch (Exception ex) {
				return JsonResponse.getErrorInstance(ex.getMessage(), ex);
			}
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

