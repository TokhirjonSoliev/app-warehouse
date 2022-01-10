package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/inputProduct")
public class InputProductController {

    @Autowired
    InputProductService inputProductService;

    @PostMapping
    public Result addInputProductController(@RequestBody InputProductDto inputProductDto){
        return inputProductService.addInputProduct(inputProductDto);
    }

    @GetMapping
    public List<InputProduct> getInputProductListController(){
        return inputProductService.getInputProductList();
    }

    @GetMapping(value = "/{inputProductId}")
    public InputProduct getInputProductController(@PathVariable Integer inputProductId){
        return inputProductService.getInputProduct(inputProductId);
    }

    @PutMapping(value = "/{inputProductId}")
    public Result editInputProductController(@PathVariable Integer inputProductId, @RequestBody InputProductDto inputProductDto){
        return inputProductService.editInputProduct(inputProductId, inputProductDto);
    }

    @DeleteMapping(value = "/{inputProductId}")
    public Result deleteInputProductController(@PathVariable Integer inputProductId){
        return inputProductService.deleteInputProduct(inputProductId);
    }
}
