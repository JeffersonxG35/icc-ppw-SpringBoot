package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.Product;
import ec.edu.ups.icc.fundamentos01.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll().stream()
                .filter(entity -> !entity.isDeleted())
                .map(Product::fromEntity)
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto findOne(Long id) {
        ProductEntity entity = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        return Product.fromEntity(entity).toResponseDto();
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        productRepository.findByNameAndDeletedFalse(dto.getName()).ifPresent(p -> {
            throw new ConflictException("El nombre del producto ya está registrado");
        });

        Product productModel = Product.fromDto(dto);
        ProductEntity entity = productModel.toEntity();
        ProductEntity savedEntity = productRepository.save(entity);
        return Product.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public ProductResponseDto update(Long id, UpdateProductDto dto) {
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (existingEntity.isDeleted()) {
            throw new NotFoundException("Producto no encontrado");
        }

        Product productModel = Product.fromEntity(existingEntity);
        productModel.update(dto);

        ProductEntity savedEntity = productRepository.save(productModel.toEntity());
        return Product.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public ProductResponseDto partialUpdate(Long id, PartialUpdateProductDto dto) {
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (existingEntity.isDeleted()) {
            throw new NotFoundException("Producto no encontrado");
        }

        Product productModel = Product.fromEntity(existingEntity);
        productModel.partialUpdate(dto);

        ProductEntity savedEntity = productRepository.save(productModel.toEntity());
        return Product.fromEntity(savedEntity).toResponseDto();
    }

    @Override
    public void delete(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (entity.isDeleted()) {
            throw new NotFoundException("El producto ya se encuentra eliminado");
        }

        entity.setDeleted(true);
        productRepository.save(entity);
    }
}