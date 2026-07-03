package ec.edu.ups.icc.fundamentos01.users.models;

import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

import java.time.LocalDateTime;

public class UserModel {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;

    public UserModel() {
    }

    public UserModel(Long id, String name, String email, String password, String passwordHash, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordHash = passwordHash;
        this.deleted = deleted;
    }

    public static UserModel fromDto(CreateUserDto dto) {
        return new UserModel(null, dto.getName(), dto.getEmail(), dto.getPassword(), null, false);
    }

    public static UserModel fromEntity(UserEntity entity) {
        UserModel model = new UserModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPasswordHash(entity.getPasswordHash());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());

        return model;
    }

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();

        entity.setId(this.id);
        entity.setName(this.name);
        entity.setEmail(this.email);
        entity.setPasswordHash(this.passwordHash != null ? this.passwordHash : "hash_" + this.password);
        entity.setDeleted(this.deleted);

        return entity;
    }

    public UserResponseDto toResponseDto() {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(this.id);
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
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}