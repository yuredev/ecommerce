package javaweb.springboot.bootcommerce.services;

import javaweb.springboot.bootcommerce.models.Product;
import javaweb.springboot.bootcommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public void save(Product product) {
        productRepository.save(product);
    }
    public Product getOne(Long id) {
        return this.productRepository.getOne(id);
    }
}
