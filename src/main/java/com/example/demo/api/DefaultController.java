package com.example.demo.api;

import com.example.demo.configurations.RSAUtils;
import com.example.demo.entities.ProductEntity;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;

@Controller
public class DefaultController {

    private ThymeleafUtils thymeleafUtils;

    ProductService productService;

    @Autowired
    DefaultController(ProductService productService, ThymeleafUtils thymeleafUtils) {
        this.productService = productService;
        this.thymeleafUtils = thymeleafUtils;
    }


    @GetMapping(path = {"", "/"})
    public String home(Model model) {


        List<ProductEntity> products = productService.getAllProducts().getBody(); // Fetch products from the database
        String productHTML = generateProductListings(products); // Generate HTML markup for products
        Iterator<ProductEntity> iterator = products.iterator();
        while(iterator.hasNext()) {
            ProductEntity p = iterator.next();
            System.out.println("\n\n\n\n\n\n" + p + "\n\n\n\n\n\n");
            if(p.getPhoto() == null)
                iterator.remove();
        }
        model.addAttribute("products", products); // Add product listings to the model
        return "index";
    }

    private String generateProductListings(List<ProductEntity> products) {
        StringBuilder htmlBuilder = new StringBuilder();

        for (ProductEntity product : products) {

            if (product.getPhoto() != null) {

//                String base64Image = Base64.getEncoder().encodeToString(product.getPhoto());
//
//                String imgTag = "<img src=\"data:image/jpeg;base64," + base64Image + "\" alt=\"Image\">";

                htmlBuilder.append("<div th:replace=\"~{fragments/catalog_item :: item(product=${product})}\"></div>");
            }
        }

        return htmlBuilder.toString();
    }

//    private String generateProductListings(List<ProductEntity> products) {
//        StringBuilder htmlBuilder = new StringBuilder();
//
//        for (ProductEntity product : products) {
//            if (product.getPhoto() != null) {
//                String imgTag = thymeleafUtils.convertToHtmlImage(product.getPhoto());
//                htmlBuilder.append(imgTag);
//            }
//        }
//
//        return htmlBuilder.toString();
//    }

//    public static String convertToHtmlImage(byte[] imageBytes) {
//        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//        return "<img th:src=\"@{" + base64Image + "}\" alt=\"Image\">";
//    }

    @GetMapping("/login")
    public String login() {
        return "client_login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "client_signup";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/add-a-product")
    public String addProduct() {
        return "add_a_product";
    }

    @GetMapping("/get-public-key")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(RSAUtils.getPublicKey());
    }
}