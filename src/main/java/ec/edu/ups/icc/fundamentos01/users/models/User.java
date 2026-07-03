/*package ec.edu.ups.icc.fundamentos01.users.models;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean deleted;

    public User(int id, String name, String email, String password, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.deleted = deleted;
    }

    public static User fromDto(CreateUserDto dto) {
        return new User(0, dto.getName(), dto.getEmail(), dto.getPassword(), false);
    }

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId() != null ? entity.getId().intValue() : 0,
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(), 
                entity.isDeleted()
        );
    }

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        if (id > 0) {
            entity.setId((long) id);
        }
        entity.setName(this.name);
        entity.setEmail(this.email);
        entity.setPasswordHash(this.password);
        return entity;
    }

    public UserResponseDto toResponseDto() {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(Long.valueOf(this.id));
        dto.setName(this.name);
        dto.setEmail(this.email);
        return dto;
    }

    public void update(UpdateUserDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
    }

    public void partialUpdate(PartialUpdateUserDto dto) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getEmail() != null) this.email = dto.getEmail();

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            this.password = dto.getPassword(); 
        }
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isDeleted() { return deleted; }

    public void setPassword(String password) { 
        this.password = password; 
    }
}*/