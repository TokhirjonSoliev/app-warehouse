package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId() != null) {
            Optional<Category> optionalCategory =
                    categoryRepository.findById(categoryDto.getParentCategoryId());

            if (!optionalCategory.isPresent()) {
                return new Result("Bunday kategoriya mavjud emas", false);
            }
            category.setParentCategory(optionalCategory.get());
        }
        categoryRepository.save(category);
        return new Result("Muvaffaqiyatli saqlandi", true);
    }

    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }

    public Category getCategory(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null);
    }

    public Result editCategory(Integer categoryId, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDto.getName());

            if (categoryDto.getParentCategoryId() != null) {
                Optional<Category> optionalCategory1 =
                        categoryRepository.findById(categoryDto.getParentCategoryId());

                if (!optionalCategory1.isPresent()) {
                    return new Result("Bunday ota kategoriya mavjud emas", false);
                }
                category.setParentCategory(optionalCategory1.get());
            }
            categoryRepository.save(category);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday katagoriya mavjud emas", false);
    }

    public Result deleteCategory(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(categoryId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday katagoriya mavjud emas", false);
    }
}
