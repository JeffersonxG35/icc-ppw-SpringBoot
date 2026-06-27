package ec.edu.ups.icc.fundamentos01.products.models;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private boolean deleted;

    public Product(int id, String name, double price, int stock, boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.deleted = deleted;
    }

    public static Product fromDto(CreateProductDto dto) {
        return new Product(0, dto.getName(), dto.getPrice(), dto.getStock(), false);
    }

    public static Product fromEntity(ProductEntity entity) {
        return new Product(
                entity.getId() != null ? entity.getId().intValue() : 0,
                entity.getName(),
                entity.getPrice() != null ? entity.getPrice().doubleValue() : 0.0,
                entity.getStock() != null ? entity.getStock().intValue() : 0,
                entity.isDeleted()
        );
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        if (id > 0) {
            entity.setId((long) id);
        }
        entity.setName(this.name);
        entity.setPrice(BigDecimal.valueOf(this.price)); 
        entity.setStock(this.stock);
        entity.setDeleted(this.deleted);
        return entity;
    }

    public ProductResponseDto toResponseDto() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(Long.valueOf(this.id));
        dto.setName(this.name);
        dto.setPrice(BigDecimal.valueOf(this.price));
        dto.setStock(Integer.valueOf(this.stock));
        dto.setDescription("Sin descripción");
        return dto;
    }

    public void update(UpdateProductDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
    }

    public void partialUpdate(PartialUpdateProductDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getPrice() != null) this.price = dto.getPrice();
        if (dto.getStock() != null) this.stock = dto.getStock();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public boolean isDeleted() { return deleted; }
}