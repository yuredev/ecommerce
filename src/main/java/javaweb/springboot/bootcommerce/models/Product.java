package javaweb.springboot.bootcommerce.models;

import javaweb.springboot.bootcommerce.utils.ErrorMessage;
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

    @Size(min = 2, max = 40, message = ErrorMessage.NAME_OUT_OF_SIZE)
    @NotBlank(message = ErrorMessage.NAME_BLANK)
    private String title;

    @NotBlank(message = ErrorMessage.SELLER_NAME_BLANK)
    @Size(min = 2, max = 40, message = ErrorMessage.SELLER_NAME_OUT_OF_SIZE)
    private String seller;

    @NotNull(message = ErrorMessage.NULL_PRICE)
    private Double price;
    private String description;
    
    @NotBlank
    private String category;

    public Long getId() {
        return this.id;
    }
}
