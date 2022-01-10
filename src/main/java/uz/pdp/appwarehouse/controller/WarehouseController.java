package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;
import uz.pdp.appwarehouse.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping(value = "/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @PostMapping
    public Result addWarehouseController(@RequestBody Warehouse warehouse){
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getWarehouseListController(){
        return warehouseService.getWarehouseList();
    }

    @GetMapping(value = "/{warehouseId}")
    public Warehouse getWarehouseController(@PathVariable Integer warehouseId){
        return warehouseService.getWarehouse(warehouseId);
    }

    @PutMapping(value = "/{warehouseId}")
    public Result editWarehouseController(@PathVariable Integer warehouseId, @RequestBody Warehouse warehouse){
        return warehouseService.editWarehouse(warehouseId, warehouse);
    }

    @DeleteMapping(value = "/{warehouseId}")
    public Result deleteWarehouseController(@PathVariable Integer warehouseId){
        return warehouseService.deleteWarehouse(warehouseId);
    }
}
