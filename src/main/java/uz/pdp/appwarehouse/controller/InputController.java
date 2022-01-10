package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputService;

import java.util.List;

@RestController
@RequestMapping(value = "/input")
public class InputController {

    @Autowired
    InputService inputService;

    @PostMapping
    public Result addInputController(@RequestBody InputDto inputDto){
        return inputService.addInput(inputDto);
    }

    @GetMapping
    public List<Input> getInputListController(){
        return inputService.getInputList();
    }

    @GetMapping(value = "/{inputId}")
    public Input getInputController(@PathVariable Integer inputId){
        return inputService.getInput(inputId);
    }

    @PutMapping(value = "/{inputId}")
    public Result editInputController(@PathVariable Integer inputId, @RequestBody InputDto inputDto){
        return inputService.editInput(inputId, inputDto);
    }

    @DeleteMapping(value = "/{inputId}")
    public Result deleteInputController(@PathVariable Integer inputId){
        return inputService.deleteInput(inputId);
    }
}
