package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.CategoryDto;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result addCategoryController(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping
    public List<Category> getCategoryListController(){
        return categoryService.getCategoryList();
    }

    @GetMapping(value = "/{categoryId}")
    public Category getCategoryController(@PathVariable Integer categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PutMapping(value = "/{categoryId}")
    public Result editCategoryController(@PathVariable Integer categoryId, @RequestBody CategoryDto categoryDto){
        return categoryService.editCategory(categoryId, categoryDto);
    }

    @DeleteMapping(value = "/{categoryId}")
    public Result deleteCategoryController(@PathVariable Integer categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
