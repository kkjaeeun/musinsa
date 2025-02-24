CREATE TABLE IF NOT EXISTS brand (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS product_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    price DECIMAL NOT NULL
);


-- brand 테이블의 id 컬럼에 인덱스 추가
CREATE INDEX idx_brand_id ON brand(id);

-- category 테이블의 id 컬럼에 인덱스 추가
CREATE INDEX idx_category_id ON category(id);

-- product_info 테이블의 brand_id와 category_id 컬럼에 인덱스 추가
CREATE INDEX idx_product_info_brand_id ON product_info(brand_id);
CREATE INDEX idx_product_info_category_id ON product_info(category_id);