package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Result addProductController(@RequestBody ProductDto productDto){
        Result result = productService.addProduct(productDto);
        return result;
    }

    @GetMapping
    public List<Product> getProductListController(){
        return productService.getProductList();
    }

    @GetMapping(value = "/{productId}")
    public Product getProductController(@PathVariable Integer productId){
        return productService.getProduct(productId);
    }

    @PutMapping(value = "/{productId}")
    public Result editProductController(@PathVariable Integer productId, @RequestBody ProductDto productDto){
        return productService.editProduct(productId, productDto);
    }

    @DeleteMapping(value = "/{productId}")
    public Result deleteProductController(@PathVariable Integer productId){
        return productService.deleteProduct(productId);
    }
}
