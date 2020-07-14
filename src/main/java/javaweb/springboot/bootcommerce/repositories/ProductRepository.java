package javaweb.springboot.bootcommerce.repositories;

import javaweb.springboot.bootcommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}