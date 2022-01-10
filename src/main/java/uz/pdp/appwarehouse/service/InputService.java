package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    SupplierRepository supplierRepository;

    public Result addInput(InputDto inputDto) {
        boolean existsByCode = inputRepository.existsByCode(inputDto.getCode());
        if (existsByCode) {
            return new Result("Bunday input mavjud", false);
        }

        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()) {
            return new Result("Bunday currency mavjud emas", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday warehouse mavjud emas", false);

        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
        if (!optionalSupplier.isPresent())
            return new Result("Bunday supplier birligi mavjud emas", false);

        Input input = new Input();
        input.setDate(inputDto.getDate());
        input.setCode(codeGeneration()); //generatsiya qilamiz
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setCurrency(optionalCurrency.get());
        input.setSupplier(optionalSupplier.get());
        input.setWarehouse(optionalWarehouse.get());
        inputRepository.save(input);

        return  new Result("input saqlandi", true);
    }

    public List<Input> getInputList(){
        return inputRepository.findAll();
    }

    public Input getInput(Integer inputId){
        Optional<Input> optionalInput = inputRepository.findById(inputId);
        return optionalInput.orElse(null);
    }

    public Result editInput(Integer inputId, InputDto inputDto){
        Optional<Input> optionalInput = inputRepository.findById(inputId);
        if (optionalInput.isPresent()){
            Input input = optionalInput.get();
            input.setDate(inputDto.getDate());
            input.setCode(inputDto.getCode());
            input.setFactureNumber(inputDto.getFactureNumber());

            Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
            if (!optionalCurrency.isPresent()) {
                return new Result("Bunday currency mavjud emas", false);
            }

            Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDto.getSupplierId());
            if (!optionalSupplier.isPresent())
                return new Result("Bunday supplier mavjud emas", false);

            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDto.getWarehouseId());
            if (!optionalWarehouse.isPresent())
                return new Result("Bunday olchov birligi mavjud emas", false);

            input.setCurrency(optionalCurrency.get());
            input.setSupplier(optionalSupplier.get());
            input.setWarehouse(optionalWarehouse.get());
            inputRepository.save(input);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday input mavjud emas", false);
    }

    public Result deleteInput(Integer inputId){
        Optional<Input> optionalInput = inputRepository.findById(inputId);
        if (optionalInput.isPresent()){
            inputRepository.deleteById(inputId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday input yoq mavjud emas", false);
    }

    private String codeGeneration(){
        List<Input> inputList = inputRepository.findAll();
        int max = 0;
        for (Input input : inputList) {
            if (Integer.parseInt(input.getCode()) >= max){
                max = Integer.parseInt(input.getCode());
            }
        }
        return String.valueOf(max);
    }
}
