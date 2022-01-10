package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping(value = "/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurementController(@RequestBody Measurement measurement){
        return measurementService.addMeasurementService(measurement);
    }

    // GET LIST, GET ONE, EDIT, DELETE
    @GetMapping
    public List<Measurement> getMeasurementListController(){
        return measurementService.getMeasurementList();
    }

    @GetMapping(value = "/{measurementId}")
    public Measurement getMeasurementController(@PathVariable Integer measurementId){
        return measurementService.getMeasurement(measurementId);
    }

    @PutMapping(value = "/{measurementId}")
    public Result editMeasurementController(@PathVariable Integer measurementId, @RequestBody Measurement measurement){
        return measurementService.editMeasurement(measurementId, measurement);
    }

    @DeleteMapping(value = "/{measurementId}")
    public Result deleteMeasurementController(@PathVariable Integer measurementId){
        return measurementService.deleteMeasurement(measurementId);
    }
}
