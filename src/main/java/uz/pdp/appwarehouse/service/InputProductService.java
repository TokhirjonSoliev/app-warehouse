package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.*;
import uz.pdp.appwarehouse.payload.InputProductDto;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InputRepository inputRepository;

    public Result addInputProduct(InputProductDto inputProductDto) {

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent()) {
            return new Result("Bunday product mavjud emas", false);
        }

        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Bunday input mavjud emas", false);

        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setInput(optionalInput.get());
        inputProduct.setProduct(optionalProduct.get());
        inputProductRepository.save(inputProduct);

        return  new Result("input product saqlandi", true);
    }

    public List<InputProduct> getInputProductList(){
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProduct(Integer inputProductId){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(inputProductId);
        return optionalInputProduct.orElse(null);
    }

    public Result editInputProduct(Integer inputProductId, InputProductDto inputProductDto){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(inputProductId);
        if (optionalInputProduct.isPresent()){
            InputProduct inputProduct = optionalInputProduct.get();

            Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
            if (!optionalProduct.isPresent()) {
                return new Result("Bunday product mavjud emas", false);
            }

            Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
            if (!optionalInput.isPresent())
                return new Result("Bunday input mavjud emas", false);

            inputProduct.setAmount(inputProductDto.getAmount());
            inputProduct.setExpireDate(inputProductDto.getExpireDate());
            inputProduct.setPrice(inputProductDto.getPrice());
            inputProduct.setInput(optionalInput.get());
            inputProduct.setProduct(optionalProduct.get());
            inputProductRepository.save(inputProduct);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday input product mavjud emas", false);
    }

    public Result deleteInputProduct(Integer inputProductId){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(inputProductId);
        if (optionalInputProduct.isPresent()){
            inputProductRepository.deleteById(inputProductId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday input product mavjud emas", false);
    }
}
