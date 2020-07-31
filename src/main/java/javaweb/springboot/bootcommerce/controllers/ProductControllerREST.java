package javaweb.springboot.bootcommerce.controllers;

import javaweb.springboot.bootcommerce.models.Product;
import javaweb.springboot.bootcommerce.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductControllerREST {
    private final ProductService service;

    public ProductControllerREST(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        List<Product> productList = service.findAll();
        for (Product product: productList) {
            product.add(linkTo(ProductControllerREST.class).slash(product.getId()).withSelfRel());
            product.add(linkTo(ProductControllerREST.class).withRel("all-products"));
        }
        return productList;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        Product productFound = service.findById(id);
        if (productFound == null) {
            return ResponseEntity.notFound().build();
        }
        productFound.add(linkTo(ProductControllerREST.class).slash(id).withSelfRel());
        productFound.add(linkTo(ProductControllerREST.class).withRel("all-products"));
        return ResponseEntity.ok(productFound);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
       service.save(product);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.save(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
