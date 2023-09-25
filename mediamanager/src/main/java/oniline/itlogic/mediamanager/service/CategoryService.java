package oniline.itlogic.mediamanager.service;

import oniline.itlogic.mediamanager.exceptions.UserNotFoundException;
import oniline.itlogic.mediamanager.model.Bewertung;
import oniline.itlogic.mediamanager.model.Category;
import oniline.itlogic.mediamanager.repo.BewertungenRepository;
import oniline.itlogic.mediamanager.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category){
        return  categoryRepository.save(category);
    }
    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }
    public Category findCategoryById(Long id){
        return categoryRepository.findCategoryById(id).orElseThrow(()-> new UserNotFoundException("Category by id " + id+" was not Found" ));
    }
    public  void deleteBewertungen(Long id){
        categoryRepository.deleteById(id);
    }
}

