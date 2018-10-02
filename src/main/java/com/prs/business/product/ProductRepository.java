package com.prs.business.product;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.product.Product;;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {

}