package ec.edu.ups.icc.fundamentos01.products.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class ProductFilterByCategoryDto {

    @Size(min = 2, max = 150)
    private String name;

    @DecimalMin(value = "0.0")
    private Double minPrice;

    @DecimalMin(value = "0.0")
    private Double maxPrice;

    public ProductFilterByCategoryDto() {
    }

    public ProductFilterByCategoryDto(String name, Double minPrice, Double maxPrice) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public boolean hasValidPriceRange() {
        if (minPrice != null && maxPrice != null) {
            return maxPrice >= minPrice;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}