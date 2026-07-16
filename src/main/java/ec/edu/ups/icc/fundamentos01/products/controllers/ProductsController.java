package ec.edu.ups.icc.fundamentos01.products.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.core.dtos.PaginationDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import ec.edu.ups.icc.fundamentos01.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/*
 * Controlador REST encargado de exponer endpoints HTTP
 * para la gestión de productos.
 *
 * Todos los endpoints de este controlador requieren JWT,
 * porque el proyecto usa .anyRequest().authenticated().
 */
@Tag(
        name = "Productos",
        description = "Gestión de productos con paginación, roles y ownership"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(
            ProductService productService
    ) {
        this.productService = productService;
    }

    /*
     * Endpoint administrativo.
     *
     * Solo ROLE_ADMIN puede consumir este endpoint.
     */
    @Operation(
            summary = "Listar todos los productos",
            description = "Permite listar todos los productos registrados. Este endpoint solo puede ser consumido por usuarios con rol ADMIN."
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok(
                productService.findAll()
        );
    }

    /*
     * Lista productos usando paginación completa.
     *
     * Devuelve información como:
     * - contenido
     * - total de elementos
     * - total de páginas
     * - página actual
     */
    @Operation(
            summary = "Listar productos paginados",
            description = "Devuelve una página de productos usando Page. Incluye información completa de paginación."
    )
    @GetMapping("/page")
    public Page<ProductResponseDto> findAllPage(
            @Valid @ModelAttribute PaginationDto pagination
    ) {
        return productService.findAllPage(pagination);
    }

    /*
     * Lista productos usando Slice.
     *
     * Slice es útil cuando no se necesita conocer
     * el total exacto de registros.
     */
    @Operation(
            summary = "Listar productos con slice",
            description = "Devuelve productos usando Slice. Permite saber si existe una siguiente página sin calcular el total de registros."
    )
    @GetMapping("/slice")
    public Slice<ProductResponseDto> findAllSlice(
            @Valid @ModelAttribute PaginationDto pagination
    ) {
        return productService.findAllSlice(pagination);
    }

    /*
     * Busca un producto por su identificador.
     */
    @Operation(
            summary = "Buscar producto por ID",
            description = "Obtiene la información de un producto específico mediante su identificador."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getOne(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                productService.findOne(id)
        );
    }

    /*
     * Crea un producto.
     *
     * El usuario dueño del producto se obtiene desde el JWT,
     * por eso CreateProductDto ya no recibe userId.
     */
    @Operation(
            summary = "Crear producto",
            description = "Crea un nuevo producto asignando como propietario al usuario autenticado mediante JWT."
    )
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
            @Valid @RequestBody CreateProductDto dto,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        ProductResponseDto response =
                productService.create(dto, currentUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /*
     * Actualiza completamente un producto.
     *
     * Se valida ownership:
     * - el dueño puede modificar su producto
     * - ADMIN puede modificar productos ajenos
     */
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza completamente un producto. Solo el propietario o un usuario ADMIN puede modificarlo."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> createOrUpdate(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDto dto,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        ProductResponseDto response =
                productService.update(
                        id,
                        dto,
                        currentUser
                );

        return ResponseEntity.ok(response);
    }

    /*
     * Actualiza parcialmente un producto.
     *
     * Solo se modifican los campos enviados en el body.
     */
    @Operation(
            summary = "Actualizar parcialmente producto",
            description = "Actualiza parcialmente un producto. Solo el propietario o un usuario ADMIN puede modificarlo."
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDto> partialUpdate(
            @PathVariable Long id,
            @Valid @RequestBody PartialUpdateProductDto dto,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        ProductResponseDto response =
                productService.partialUpdate(
                        id,
                        dto,
                        currentUser
                );

        return ResponseEntity.ok(response);
    }

    /*
     * Elimina lógicamente un producto.
     *
     * Se valida ownership antes de eliminar.
     */
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina lógicamente un producto. Solo el propietario o un usuario ADMIN puede eliminarlo."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        productService.delete(id, currentUser);

        return ResponseEntity.noContent().build();
    }

    /*
     * Lista productos pertenecientes a un usuario específico.
     */
    @Operation(
            summary = "Listar productos por usuario",
            description = "Obtiene todos los productos asociados a un usuario específico mediante su ID."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductResponseDto>> findByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                productService.findByUserId(userId)
        );
    }

    /*
     * Lista productos de un usuario usando Slice.
     *
     * Este endpoint fue agregado para mostrar productos
     * filtrados por usuario con paginación tipo Slice.
     */
    @Operation(
            summary = "Listar productos por usuario con slice",
            description = "Obtiene productos de un usuario específico usando Slice y paginación."
    )
    @GetMapping("/user/{userId}/slice")
    public Slice<ProductResponseDto> findByUserIdSlice(
            @PathVariable Long userId,
            @Valid @ModelAttribute PaginationDto pagination
    ) {
        return productService.findByUserIdSlice(
                userId,
                pagination
        );
    }

    /*
     * Lista productos asociados a una categoría específica.
     */
    @Operation(
            summary = "Listar productos por categoría",
            description = "Obtiene todos los productos asociados a una categoría específica mediante su ID."
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> findByCategoryId(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(
                productService.findByCategoryId(categoryId)
        );
    }
}