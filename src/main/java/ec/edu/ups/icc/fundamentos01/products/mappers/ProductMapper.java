package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

import java.util.List;

public class ProductMapper {

    public static ProductModel toModelFromDTO(CreateProductDto dto) {
        ProductModel model = new ProductModel();

        model.setName(dto.getName());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());

        return model;
    }

    public static ProductModel toModelFromEntity(ProductEntity entity) {
        ProductModel model = new ProductModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setStock(entity.getStock());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());

        return model;
    }

    public static ProductEntity toEntityFromModel(ProductModel model) {
        ProductEntity entity = new ProductEntity();

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        entity.setDeleted(model.isDeleted());

        return entity;
    }

    public static ProductResponseDto toResponse(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        UserResponseDto ownerDto = new UserResponseDto();
        ownerDto.setId(entity.getOwner().getId());
        ownerDto.setName(entity.getOwner().getName());
        ownerDto.setEmail(entity.getOwner().getEmail());
        dto.setOwner(ownerDto);

        List<CategoryResponseDto> categories = entity.getCategories()
                .stream()
                .map(ProductMapper::toCategoryResponse)
                .toList();

        dto.setCategories(categories);

        return dto;
    }

    private static CategoryResponseDto toCategoryResponse(CategoryEntity entity) {
        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }
}