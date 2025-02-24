package com.musinsa.product.repository;

import com.musinsa.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * 카테고리명으로 카테고리 정보 조회
     *
     * @param categoryName
     * @return
     */
    Category findFirstByName(String categoryName);
}
