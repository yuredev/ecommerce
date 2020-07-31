package javaweb.springboot.bootcommerce.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "product")
public class Product extends RepresentationModel<Product> {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Size(min = 2, max = 40, message = "O Título deve conter entre 2 e 40 caracteres")
    @NotBlank(message = "O Nome do vendedor não pode estar em branco")
    private String title;

    @NotBlank(message = "O Nome do vendedor não pode estar em branco")
    @Size(min = 2, max = 40, message = "O Nome do vendedor deve conter entre 2 e 40 caracteres")
    private String seller;

    @NotNull(message = "O Produto deve ter um preço definido")
    private Double price;
    private String description;
    
    @NotBlank
    private String category;

    public Long getId() {
        return this.id;
    }
}
