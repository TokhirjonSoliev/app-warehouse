package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Result addWarehouse(Warehouse warehouse){
        boolean existsByName = warehouseRepository.existsByName(warehouse.getName());
        if (existsByName){
            return new Result("Bunday ombor mavjud", false);
        }
        warehouseRepository.save(warehouse);
        return new Result("Muvaffaqiyatli qoshildi", true);
    }

    public List<Warehouse> getWarehouseList(){
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        return warehouseList;
    }

    public Warehouse getWarehouse(Integer warehouseId){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
        return optionalWarehouse.orElse(null);
    }

    public Result editWarehouse(Integer warehouseId, Warehouse warehouse){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
        if (optionalWarehouse.isPresent()){
            Warehouse warehouse1 = optionalWarehouse.get();
            warehouse1.setName(warehouse.getName());
            warehouseRepository.save(warehouse1);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday ombor mavjud emas", false);
    }

    public Result deleteWarehouse(Integer warehouseId){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
        if (optionalWarehouse.isPresent()){
            warehouseRepository.deleteById(warehouseId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday ombor mavjud emas", false);
    }
}
