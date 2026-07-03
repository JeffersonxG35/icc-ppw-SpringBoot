package ec.edu.ups.icc.fundamentos01.categories.services;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.CreateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.dtos.UpdateCategoryDto;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.categories.repositories.CategoryRepository;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findByDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponseDto findOne(Long id) {
        CategoryEntity entity = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        return toResponse(entity);
    }

    @Override
    public CategoryResponseDto create(CreateCategoryDto dto) {
        if (categoryRepository.existsByNameIgnoreCaseAndDeletedFalse(dto.getName())) {
            throw new ConflictException("El nombre de la categoría ya está registrado");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        CategoryEntity saved = categoryRepository.save(entity);

        return toResponse(saved);
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryDto dto) {
        CategoryEntity entity = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        CategoryEntity saved = categoryRepository.save(entity);

        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        CategoryEntity entity = categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        entity.setDeleted(true);
        categoryRepository.save(entity);
    }

    private CategoryResponseDto toResponse(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}