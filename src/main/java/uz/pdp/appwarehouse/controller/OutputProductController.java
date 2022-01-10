package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/outputProduct")
public class OutputProductController {

    @Autowired
    OutputProductService outputProductService;

    @PostMapping
    public Result addOutputProductController(@RequestBody OutputProductDto outputProductDto){
        return outputProductService.addOutputProduct(outputProductDto);
    }

    @GetMapping
    public List<OutputProduct> getOutputProductListController(){
        return outputProductService.getOutputProductList();
    }

    @GetMapping(value = "/{outputProductId}")
    public OutputProduct getOutputProductController(@PathVariable Integer outputProductId){
        return outputProductService.getOutputProduct(outputProductId);
    }

    @PutMapping(value = "/{outputProductId}")
    public Result editOutputProductController(@PathVariable Integer outputProductId, @RequestBody OutputProductDto outputProductDto){
        return outputProductService.editOutputProduct(outputProductId, outputProductDto);
    }

    @DeleteMapping(value = "/{outputProductId}")
    public Result deleteOutputProductController(@PathVariable Integer outputProductId){
        return outputProductService.deleteOutputProduct(outputProductId);
    }
}
