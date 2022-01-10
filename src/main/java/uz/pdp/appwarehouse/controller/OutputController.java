package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputService;
import uz.pdp.appwarehouse.service.OutputService;

import java.util.List;

@RestController
@RequestMapping(value = "/input")
public class OutputController {

    @Autowired
    OutputService outputService;

    @PostMapping
    public Result addOutputController(@RequestBody OutputDto outputDto){
        return outputService.addOutput(outputDto);
    }

    @GetMapping
    public List<Output> getOutputListController(){
        return outputService.getOutputList();
    }

    @GetMapping(value = "/{outputId}")
    public Output getOutputController(@PathVariable Integer outputId){
        return outputService.getOutput(outputId);
    }

    @PutMapping(value = "/{outputId}")
    public Result editOutputController(@PathVariable Integer outputId, @RequestBody OutputDto outputDto){
        return outputService.editOutput(outputId, outputDto);
    }

    @DeleteMapping(value = "/{outputId}")
    public Result deleteOutputController(@PathVariable Integer outputId){
        return outputService.deleteOutput(outputId);
    }
}
