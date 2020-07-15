package javaweb.springboot.bootcommerce.controllers;

import javaweb.springboot.bootcommerce.models.Product;
import javaweb.springboot.bootcommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService ps) {
        this.productService = ps;
    }

    @RequestMapping("/")
    public String getHome(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @RequestMapping("product/{id}")
    public ModelAndView getProductPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productService.getOne(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

}
