package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Datos requeridos para crear un producto")

public class CreateProductDto {

     @Schema(
            description = "Nombre del producto",
            example = "Laptop Lenovo IdeaPad 3"
    )

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

     @Schema(
            description = "Precio del producto",
            example = "750.50"
    )
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser menor a 0")
    private Double price;

      @Schema(
            description = "Cantidad disponible en stock",
            example = "10"
    )

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private Integer stock;

    //@NotNull(message = "El ID del usuario es obligatorio")
    //private Long userId;

    @Schema(
            description = "Lista de identificadores de categorías asociadas al producto",
            example = "[1, 2]"
    )

    @NotEmpty(message = "Debe seleccionar al menos una categoría")
    private Set<Long> categoryIds;

    public CreateProductDto() {
    }

    public CreateProductDto(String name, Double price, Integer stock, Set<Long> categoryIds) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }
}