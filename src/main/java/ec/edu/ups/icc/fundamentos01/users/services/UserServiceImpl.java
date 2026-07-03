package ec.edu.ups.icc.fundamentos01.users.services;

import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.models.UserModel;
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
        return userRepository.findByDeletedFalse()
                .stream()
                .map(UserModel::fromEntity)
                .map(UserModel::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findOne(Long id) {
        UserEntity entity = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return UserModel.fromEntity(entity).toResponseDto();
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        userRepository.findByEmail(dto.getEmail()).ifPresent(user -> {
            if (!user.isDeleted()) {
                throw new ConflictException("El correo ya está registrado");
            }
        });

        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPassword());

        UserEntity savedEntity = userRepository.save(entity);

        return UserModel.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        UserEntity entity = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

        UserEntity updatedEntity = userRepository.save(entity);

        return UserModel.fromEntity(updatedEntity).toResponseDto();
    }

    @Override
    public UserResponseDto partialUpdate(Long id, PartialUpdateUserDto dto) {
        UserEntity entity = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPasswordHash("hash_" + dto.getPassword());
        }

        UserEntity updatedEntity = userRepository.save(entity);

        return UserModel.fromEntity(updatedEntity).toResponseDto();
    }

    @Override
    public void delete(Long id) {
        UserEntity entity = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        entity.setDeleted(true);
        userRepository.save(entity);
    }
}