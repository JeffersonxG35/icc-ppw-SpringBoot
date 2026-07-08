-- =========================
-- 5. Verificación
-- =========================

SELECT COUNT(*) AS total_users
FROM users;

SELECT COUNT(*) AS total_categories
FROM categories;

SELECT COUNT(*) AS total_products
FROM products;

SELECT COUNT(*) AS total_product_category_relations
FROM product_categories;

SELECT
    MIN(total_categories) AS min_categories_per_product,
    MAX(total_categories) AS max_categories_per_product,
    ROUND(AVG(total_categories), 2) AS avg_categories_per_product
FROM (
    SELECT
        product_id,
        COUNT(*) AS total_categories
    FROM product_categories
    GROUP BY product_id
) t;