package ec.edu.ups.icc.fundamentos01.products.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProductDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

    @Min(value = 0, message = "El precio no puede ser menor a 0")
    private double price;

    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private int stock;

    public CreateProductDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}