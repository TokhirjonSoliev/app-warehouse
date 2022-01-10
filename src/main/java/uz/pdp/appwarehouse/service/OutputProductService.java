package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.OutputProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OutputRepository outputRepository;

    public Result addOutputProduct(OutputProductDto outputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Bunday product mavjud emas", false);
        }

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Bunday input mavjud emas", false);

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());
        outputProductRepository.save(outputProduct);

        return  new Result("output product saqlandi", true);
    }

    public List<OutputProduct> getOutputProductList(){
        return outputProductRepository.findAll();
    }

    public OutputProduct getOutputProduct(Integer outputProductId){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(outputProductId);
        return optionalOutputProduct.orElse(null);
    }

    public Result editOutputProduct(Integer outputProductId, OutputProductDto outputProductDto){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(outputProductId);
        if (optionalOutputProduct.isPresent()){
            OutputProduct outputProduct = optionalOutputProduct.get();

            Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
            if (!optionalProduct.isPresent()) {
                return new Result("Bunday product mavjud emas", false);
            }

            Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
            if (!optionalOutput.isPresent())
                return new Result("Bunday output mavjud emas", false);

            outputProduct.setAmount(outputProductDto.getAmount());
            outputProduct.setPrice(outputProductDto.getPrice());
            outputProduct.setOutput(optionalOutput.get());
            outputProduct.setProduct(optionalProduct.get());
            outputProductRepository.save(outputProduct);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday output product mavjud emas", false);
    }

    public Result deleteOutputProduct(Integer outputProductId){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(outputProductId);
        if (optionalOutputProduct.isPresent()){
            outputProductRepository.deleteById(outputProductId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday output product mavjud emas", false);
    }
}
