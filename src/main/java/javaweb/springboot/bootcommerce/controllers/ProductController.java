package javaweb.springboot.bootcommerce.controllers;

import javaweb.springboot.bootcommerce.models.Product;
import javaweb.springboot.bootcommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService ps) {
        this.productService = ps;
    }

//    @RequestMapping("/shopping_cart_add")
//    public String shoppingCartAdd(HttpServletRequest req) {
//        HttpSession session = req.getSession();
//        System.out.println("------------------\n\n\n\nSESSION_ID do shoppingCartList: " + session.getId());
//        System.out.println("Sessão nova? " + session.isNew());
//        System.out.println("Carrinho: " +session.getAttribute("shoppingCartList" + "\n\n\n-----------------"));
//        return "redirect:/";
//    }

    @RequestMapping("/")
    public ModelAndView getHome(HttpSession session, @RequestParam(required = false) Long insertId, @RequestParam(required = false) Long removeId, @RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView("index");
//        System.out.println("------------------\n\n\n\nSESSION_ID da home: " + session.getId() + "\n\n\n-----------------");

        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        ArrayList<Product> shoppingCartList = (ArrayList<Product>) session.getAttribute("shoppingCartList");
        if (shoppingCartList == null) {
            shoppingCartList = new ArrayList<>();
        }
        // se o parametro para remover do carrinho foi enviado
        if (removeId != null) {
            // remove o produto com o id igual ao do parametro removeId
            shoppingCartList.removeIf(product -> product.getId().equals(removeId));
            // coloca lista alterada na sessão
            session.setAttribute("shoppingCartList", shoppingCartList);
        }
        if (insertId != null) {
            Product productFound = productService.getOne(insertId);
            if (productFound != null) {
                shoppingCartList.add(productFound);
            }
        }
        session.setAttribute("shoppingCartList", shoppingCartList);
        modelAndView.addObject("shoppingCartList", shoppingCartList);
        return modelAndView;
    }

    @RequestMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    @RequestMapping(value = "/save_creation", method = RequestMethod.POST)
    public String saveCreation(@ModelAttribute @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return "create";
        } else {
            productService.save(product);
            return "redirect:/?message=Cadastrado";
        }
    }
    @RequestMapping(value = "save_edition", method = RequestMethod.POST)
    public String saveEdition(@ModelAttribute @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return "edit";
        } else {
            productService.save(product);
            return "redirect:/?message=Editado";
        }
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
        return "redirect:/?message=Deletado";
    }

}
