package ec.edu.ups.icc.fundamentos01.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {
   @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    public UpdateUserDto() {
    }

    public UpdateUserDto(
            @NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres") String name,
            @NotBlank(message = "El correo es obligatorio") @Email(message = "Debe ser un correo electrónico válido") String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
