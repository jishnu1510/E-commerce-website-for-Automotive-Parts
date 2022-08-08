package com.mywork.website.service;

import com.mywork.website.model.Category;
import com.mywork.website.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public void removeCategoryById(int id){categoryRepository.deleteById(id);}
    public Optional<Category> getCategoryById(int id){return categoryRepository.findById(id);}
}
