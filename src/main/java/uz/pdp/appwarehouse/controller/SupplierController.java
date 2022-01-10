package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result addSupplierController(@RequestBody Supplier supplier){
        return supplierService.addSupplier(supplier);
    }

    // GET LIST, GET ONE, EDIT, DELETE
    @GetMapping
    public List<Supplier> getSupplierListController(){
        return supplierService.getSupplierList();
    }

    @GetMapping(value = "/{supplierId}")
    public Supplier getSupplierController(@PathVariable Integer supplierId){
        return supplierService.getSupplier(supplierId);
    }

    @PutMapping(value = "/{supplierId}")
    public Result editSupplierController(@PathVariable Integer supplierId, @RequestBody Supplier supplier){
        return supplierService.editSupplier(supplierId, supplier);
    }

    @DeleteMapping(value = "/{supplierId}")
    public Result deleteSupplierController(@PathVariable Integer supplierId){
        return supplierService.deleteSupplier(supplierId);
    }
}
