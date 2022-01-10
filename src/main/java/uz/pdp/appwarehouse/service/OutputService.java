package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputDto;
import uz.pdp.appwarehouse.payload.OutputDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ClientRepository clientRepository;

    public Result addOutput(OutputDto outputDto) {
        boolean existsByCode = outputRepository.existsByCode(outputDto.getCode());
        if (existsByCode) {
            return new Result("Bunday output mavjud", false);
        }

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent()) {
            return new Result("Bunday currency mavjud emas", false);
        }

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new Result("Bunday warehouse mavjud emas", false);

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new Result("Bunday client mavjud emas", false);

        Output output = new Output();
        output.setDate(outputDto.getDate());
        output.setCode(codeGeneration()); //generatsiya qilamiz
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setCurrency(optionalCurrency.get());
        output.setClient(optionalClient.get());
        output.setWarehouse(optionalWarehouse.get());
        outputRepository.save(output);

        return  new Result("input saqlandi", true);
    }

    public List<Output> getOutputList(){
        return outputRepository.findAll();
    }

    public Output getOutput(Integer outputId){
        Optional<Output> optionalOutput = outputRepository.findById(outputId);
        return optionalOutput.orElse(null);
    }

    public Result editOutput(Integer outputId, OutputDto outputDto){
        Optional<Output> optionalOutput = outputRepository.findById(outputId);
        if (optionalOutput.isPresent()){
            Output output = optionalOutput.get();
            output.setDate(outputDto.getDate());
            output.setCode(outputDto.getCode());
            output.setFactureNumber(outputDto.getFactureNumber());

            Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
            if (!optionalCurrency.isPresent()) {
                return new Result("Bunday currency mavjud emas", false);
            }

            Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
            if (!optionalClient.isPresent())
                return new Result("Bunday client mavjud emas", false);

            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
            if (!optionalWarehouse.isPresent())
                return new Result("Bunday warehouse mavjud emas", false);

            output.setCurrency(optionalCurrency.get());
            output.setClient(optionalClient.get());
            output.setWarehouse(optionalWarehouse.get());
            outputRepository.save(output);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday output mavjud emas", false);
    }

    public Result deleteOutput(Integer outputId){
        Optional<Output> optionalOutput = outputRepository.findById(outputId);
        if (optionalOutput.isPresent()){
            outputRepository.deleteById(outputId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday output yoq mavjud emas", false);
    }

    private String codeGeneration(){
        List<Output> outputList = outputRepository.findAll();
        int max = 0;
        for (Output output : outputList) {
            if (Integer.parseInt(output.getCode()) >= max){
                max = Integer.parseInt(output.getCode());
            }
        }
        return String.valueOf(max);
    }
}
