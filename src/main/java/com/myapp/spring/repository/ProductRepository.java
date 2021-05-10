package com.myapp.spring.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Product;



//Annotation is to identify that this is a spring managed bean
//this is a data repository class

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    
	// select * from products where price>
	Optional<List<Product>> findByPriceGreaterthanEqual(Double price);
	
	Optional<List<Product>> findByProductNameOrPrice(Object object,Object object2);
	
	Optional<List<Product>> findByProductNameLike(String product,Double price);
	
	Optional<List<Product>> findByPriceIn(Collection<Double> prices);
	
	Optional<List<Product>> findByProductNameIgnoreCase(String productName);

	//void findByAll();

	
}
