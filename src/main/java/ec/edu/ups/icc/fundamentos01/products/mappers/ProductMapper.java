package ec.edu.ups.icc.fundamentos01.products.mappers;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.categories.entities.CategoryEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

public class ProductMapper {

    public static ProductModel toModelFromDTO(CreateProductDto dto) {
        if (dto == null) {
            return null;
        }

        ProductModel model = new ProductModel();

        model.setName(dto.getName());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());

        return model;
    }

    public static ProductModel toModelFromEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductModel model = new ProductModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setStock(entity.getStock());
        model.setCategories(entity.getCategories());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());

        return model;
    }

    public static ProductEntity toEntityFromModel(ProductModel model) {
        if (model == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        entity.setCategories(model.getCategories());
        entity.setDeleted(model.isDeleted());

        return entity;
    }

    public static ProductResponseDto toResponse(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getOwner() != null) {
            UserResponseDto ownerDto = new UserResponseDto();

            ownerDto.setId(entity.getOwner().getId());
            ownerDto.setName(entity.getOwner().getName());
            ownerDto.setEmail(entity.getOwner().getEmail());

            dto.setOwner(ownerDto);
        }

        if (entity.getCategories() != null) {
            List<CategoryResponseDto> categories = entity.getCategories()
                    .stream()
                    .map(ProductMapper::toCategoryResponse)
                    .toList();

            dto.setCategories(categories);
        }

        return dto;
    }

    private static CategoryResponseDto toCategoryResponse(
            CategoryEntity entity
    ) {
        if (entity == null) {
            return null;
        }

        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static List<ProductResponseDto> toResponseList(
            List<ProductEntity> entities
    ) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}