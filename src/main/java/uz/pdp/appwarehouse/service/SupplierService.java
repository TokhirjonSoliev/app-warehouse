package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Result addSupplier(Supplier supplier){
        boolean existsByName = supplierRepository.existsByName(supplier.getName());
        if (existsByName){
            return new Result("Bunday supplier mavjud", false);
        }
        supplierRepository.save(supplier);
        return new Result("Muvaffaqiyatli qoshildi", true);
    }

    public List<Supplier> getSupplierList(){
        List<Supplier> supplierList = supplierRepository.findAll();
        return supplierList;
    }

    public Supplier getSupplier(Integer supplierId){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        return optionalSupplier.orElse(null);
    }

    public Result editSupplier(Integer supplierId, Supplier supplier){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (optionalSupplier.isPresent()){
            Supplier supplier1 = optionalSupplier.get();
            supplier1.setName(supplier.getName());
            supplier1.setPhoneNumber(supplier.getPhoneNumber());
            supplierRepository.save(supplier1);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday supplier mavjud emas", false);
    }

    public Result deleteSupplier(Integer supplierId){
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if (optionalSupplier.isPresent()){
            supplierRepository.deleteById(supplierId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday supplier mavjud emas", false);
    }
}
