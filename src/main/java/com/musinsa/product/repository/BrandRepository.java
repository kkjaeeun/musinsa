package com.musinsa.product.repository;

import com.musinsa.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    /**
     * 브랜드명으로 브랜드 정보 조회
     * @param name
     * @return
     */
    Brand findFirstByName(String name);
}
