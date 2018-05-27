package com.example.springbootwebfluxsecuritydao.controller;

import com.example.springbootwebfluxsecuritydao.entity.Product;
import com.example.springbootwebfluxsecuritydao.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("products", productService.listAllProducts());
        return "products";
    }

    @RequestMapping("product/show/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", Mono.justOrEmpty(productService.getProductById(id)));
        return "productshow";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "productform";
    }

//    @GetMapping("product/edit/{id}")
//    public Rendering edit(@PathVariable Long id, Model model) {
//        return Rendering.view("productform")
//            .modelAttribute("product", productService.getProductById(id))
//            .build();
//    }

    @RequestMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "productform";
    }

    @RequestMapping(value = "product", method = RequestMethod.POST)
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/show/" + product.getId();
    }

    @RequestMapping("product/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}