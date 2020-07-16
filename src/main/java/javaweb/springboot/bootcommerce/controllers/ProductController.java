package javaweb.springboot.bootcommerce.controllers;

import javaweb.springboot.bootcommerce.models.Product;
import javaweb.springboot.bootcommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService ps) {
        this.productService = ps;
    }

    @RequestMapping("/")
    public String getHome(Model model, HttpServletRequest request, @RequestParam(required = false) Long insertInCartId, @RequestParam(required = false) Long removeFromCartId) {
        HttpSession session = request.getSession();

        List<Product> products = productService.findAll();
        ArrayList<Product> shoppingCartList = (ArrayList<Product>) session.getAttribute("shopping-cart-list");

        if (shoppingCartList == null) {
            shoppingCartList = new ArrayList<>();
        }

        // se o parametro para remover do carrinho foi enviado
        if (removeFromCartId != null) {
            // remove o produto com o id igual ao do parametro removeFromCartId
            shoppingCartList.removeIf(product -> product.getId().equals(removeFromCartId));
            // coloca lista alterada na sessão
            session.setAttribute("shopping-cart-list", shoppingCartList);
        }

        if (insertInCartId != null) {
            Product productFound = productService.getOne(insertInCartId);
            if (productFound != null) {
                shoppingCartList.add(productFound);
                session.setAttribute("shopping-cart-list", shoppingCartList);
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("shoppingCartList", shoppingCartList);
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

    @RequestMapping("edit/{id}")
    public ModelAndView getEditPage(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.getOne(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return "redirect:/";
    }

}
