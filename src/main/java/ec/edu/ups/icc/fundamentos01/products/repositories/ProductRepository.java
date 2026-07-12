package ec.edu.ups.icc.fundamentos01.products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

        Optional<ProductEntity> findByNameIgnoreCaseAndDeletedFalse(String name);

        List<ProductEntity> findByDeletedFalse();

        Optional<ProductEntity> findByIdAndDeletedFalse(Long id);

        List<ProductEntity> findByOwner_IdAndDeletedFalse(Long ownerId);

        Slice<ProductEntity> findByOwner_IdAndDeletedFalse(
                        Long ownerId,
                        Pageable pageable);

        /*
         * Consulta general de productos activos usando Page.
         */
        @Query(value = """
                        SELECT p
                        FROM ProductEntity p
                        WHERE p.deleted = false
                        """, countQuery = """
                        SELECT COUNT(p)
                        FROM ProductEntity p
                        WHERE p.deleted = false
                        """)
        Page<ProductEntity> findActivePage(Pageable pageable);

        /*
         * Consulta general de productos activos usando Slice.
         */
        @Query("""
                        SELECT p
                        FROM ProductEntity p
                        WHERE p.deleted = false
                        """)
        Slice<ProductEntity> findActiveSlice(Pageable pageable);

        /*
         * Consulta productos activos asociados a una categoría.
         */
        @Query("""
                        SELECT DISTINCT p
                        FROM ProductEntity p
                        JOIN p.categories c
                        WHERE p.deleted = false
                          AND c.id = :categoryId
                          AND c.deleted = false
                          AND p.owner.deleted = false
                        """)
        List<ProductEntity> findByCategoryId(
                        @Param("categoryId") Long categoryId);

        /*
         * Consulta productos activos de un usuario aplicando filtros.
         */
        @Query("""
                        SELECT DISTINCT p
                        FROM ProductEntity p
                        LEFT JOIN p.categories c
                        WHERE p.deleted = false
                          AND p.owner.id = :userId
                          AND p.owner.deleted = false
                          AND (
                                COALESCE(:name, '') = ''
                                OR LOWER(p.name) LIKE LOWER(
                                    CONCAT('%', COALESCE(:name, ''), '%')
                                )
                          )
                          AND (:minPrice IS NULL OR p.price >= :minPrice)
                          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                          AND (:categoryId IS NULL OR c.id = :categoryId)
                          AND (:categoryId IS NULL OR c.deleted = false)
                        """)
        List<ProductEntity> findByOwnerIdWithFilters(
                        @Param("userId") Long userId,
                        @Param("name") String name,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("categoryId") Long categoryId);

        /*
         * Consulta normal de productos de una categoría aplicando filtros.
         */
        @Query("""
                        SELECT DISTINCT p
                        FROM ProductEntity p
                        JOIN p.categories c
                        WHERE p.deleted = false
                          AND c.id = :categoryId
                          AND c.deleted = false
                          AND p.owner.deleted = false
                          AND (
                                COALESCE(:name, '') = ''
                                OR LOWER(p.name) LIKE LOWER(
                                    CONCAT('%', COALESCE(:name, ''), '%')
                                )
                          )
                          AND (:minPrice IS NULL OR p.price >= :minPrice)
                          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                          AND (:userId IS NULL OR p.owner.id = :userId)
                        """)
        List<ProductEntity> findByCategoryIdWithFilters(
                        @Param("categoryId") Long categoryId,
                        @Param("name") String name,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("userId") Long userId);

        /*
         * Consulta paginada con Page para productos de una categoría.
         */
        @Query(value = """
                        SELECT DISTINCT p
                        FROM ProductEntity p
                        JOIN p.categories c
                        WHERE p.deleted = false
                          AND c.id = :categoryId
                          AND c.deleted = false
                          AND p.owner.deleted = false
                          AND (
                                COALESCE(:name, '') = ''
                                OR LOWER(p.name) LIKE LOWER(
                                    CONCAT('%', COALESCE(:name, ''), '%')
                                )
                          )
                          AND (:minPrice IS NULL OR p.price >= :minPrice)
                          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                          AND (:userId IS NULL OR p.owner.id = :userId)
                        """, countQuery = """
                        SELECT COUNT(DISTINCT p)
                        FROM ProductEntity p
                        JOIN p.categories c
                        WHERE p.deleted = false
                          AND c.id = :categoryId
                          AND c.deleted = false
                          AND p.owner.deleted = false
                          AND (
                                COALESCE(:name, '') = ''
                                OR LOWER(p.name) LIKE LOWER(
                                    CONCAT('%', COALESCE(:name, ''), '%')
                                )
                          )
                          AND (:minPrice IS NULL OR p.price >= :minPrice)
                          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                          AND (:userId IS NULL OR p.owner.id = :userId)
                        """)
        Page<ProductEntity> findByCategoryIdWithFiltersPage(
                        @Param("categoryId") Long categoryId,
                        @Param("name") String name,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("userId") Long userId,
                        Pageable pageable);

        /*
         * Consulta paginada con Slice para productos de una categoría.
         */
        @Query("""
                        SELECT DISTINCT p
                        FROM ProductEntity p
                        JOIN p.categories c
                        WHERE p.deleted = false
                          AND c.id = :categoryId
                          AND c.deleted = false
                          AND p.owner.deleted = false
                          AND (
                                COALESCE(:name, '') = ''
                                OR LOWER(p.name) LIKE LOWER(
                                    CONCAT('%', COALESCE(:name, ''), '%')
                                )
                          )
                          AND (:minPrice IS NULL OR p.price >= :minPrice)
                          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                          AND (:userId IS NULL OR p.owner.id = :userId)
                        """)
        Slice<ProductEntity> findByCategoryIdWithFiltersSlice(
                        @Param("categoryId") Long categoryId,
                        @Param("name") String name,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("userId") Long userId,
                        Pageable pageable);
}