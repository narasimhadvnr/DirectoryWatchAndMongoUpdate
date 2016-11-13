package com.indix.dbupdater.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.indix.dbupdater.model.ProductDetails;

/**
 * Created by venkat on 12/11/16.
 */
public interface ProductRepository extends MongoRepository<ProductDetails,String> {

  /*  public List<Product> findByCategoryId(long category);

    public List<Product> findByUpcs(String upcs);

    public List<Product> findByProductId(String productId);*/

}
