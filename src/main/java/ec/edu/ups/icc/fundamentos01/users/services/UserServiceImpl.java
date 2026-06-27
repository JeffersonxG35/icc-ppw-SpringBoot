package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.models.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findByDeletedFalse().stream()
                .map(User::fromEntity)
                .map(User::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findOne(Long id) {
        UserEntity entity = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
                
        return User.fromEntity(entity).toResponseDto();
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            if (!u.isDeleted()) {
                throw new ConflictException("El correo ya está registrado");
            }
        });

        User user = User.fromDto(dto);
        UserEntity savedEntity = userRepository.save(user.toEntity());
        return User.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (entity.isDeleted()) {
            throw new NotFoundException("No se puede actualizar un usuario eliminado");
        }

        User user = User.fromEntity(entity);
        user.update(dto);

        UserEntity updatedEntity = userRepository.save(user.toEntity());
        return User.fromEntity(updatedEntity).toResponseDto();
    }

    @Override
    public UserResponseDto partialUpdate(Long id, PartialUpdateUserDto dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        if (entity.isDeleted()) {
            throw new NotFoundException("No se puede actualizar un usuario eliminado");
        }

        User user = User.fromEntity(entity);
        user.partialUpdate(dto);

        // Se antepone "hash_" para evidenciar la actualización de la clave
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String hashedPrefix = "hash_" + dto.getPassword();
            user.setPassword(hashedPrefix);
        }

        UserEntity updatedEntity = userRepository.save(user.toEntity());
        return User.fromEntity(updatedEntity).toResponseDto();
    }

    @Override
    public void delete(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        if (entity.isDeleted()) {
            throw new NotFoundException("El usuario ya se encuentra eliminado");
        }

        entity.setDeleted(true);
        userRepository.save(entity);
    }
}