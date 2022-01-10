package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public Result addProduct(ProductDto productDto) {
        boolean exists = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (exists) {
            return new Result("Bunday maxsulot ushbu kategoriyada mavjud", false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new Result("Bunday kategoriya mavjud emas", false);
        }

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday rasm mavjud emas", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday olchov birligi mavjud emas", false);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCode(codeGeneration()); //generatsiya qilamiz
        product.setCategory(optionalCategory.get());
        product.setPhoto(optionalAttachment.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);

        return  new Result("Maxsulot saqlandi", true);
    }

    public List<Product> getProductList(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null);
    }

    public Result editProduct(Integer productId, ProductDto productDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(product.getName());

            Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
            if (!optionalCategory.isPresent()) {
                return new Result("Bunday kategoriya mavjud emas", false);
            }

            Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());
            if (!optionalAttachment.isPresent())
                return new Result("Bunday rasm mavjud emas", false);

            Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
            if (!optionalMeasurement.isPresent())
                return new Result("Bunday olchov birligi mavjud emas", false);

            product.setCategory(optionalCategory.get());
            product.setPhoto(optionalAttachment.get());
            product.setMeasurement(optionalMeasurement.get());
            productRepository.save(product);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday mahsulot mavjud emas", false);
    }

    public Result deleteProduct(Integer productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(productId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday mahsulot mavjud emas", false);
    }

    private String codeGeneration(){
        List<Product> productList = productRepository.findAll();
        int max = 0;
        for (Product product : productList) {
            if (Integer.parseInt(product.getCode()) >= max){
                max = Integer.parseInt(product.getCode());
            }
        }
        return String.valueOf(max);
    }
}
