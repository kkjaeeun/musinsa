package com.musinsa.product.repository;

import com.musinsa.product.dto.BrandPriceDTO;
import com.musinsa.product.dto.BrandTotalPriceDTO;
import com.musinsa.product.dto.CategoryInfoDTO;
import com.musinsa.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    /**
     * 카테고리별 최저가격 브랜드, 가격 조회
     *
     * @return
     */
    @Query(value = "SELECT categoryName, brandName, price FROM (" +
            "SELECT" +
            "    c.name AS categoryName, b.name AS brandName, pi.price AS price, " +
            "    ROW_NUMBER() over (PARTITION BY c.id ORDER BY pi.price ASC, b.id DESC) AS rank " +
            "FROM product_info pi " +
            "INNER JOIN category c ON pi.category_id = c.id " +
            "INNER JOIN brand b ON pi.brand_id = b.id " +
            "ORDER BY pi.category_id " +
            ") AS ranking " +
            "WHERE ranking.rank = 1", nativeQuery = true)
    List<Object[]> findBrandNameAndMinPriceByCategoryId();

    /**
     * 브랜드별 총액 조회
     *
     * @return
     */
    @Query("SELECT new com.musinsa.product.dto.BrandTotalPriceDTO(pi.brand.id, b.name, SUM(pi.price)) FROM ProductInfo pi JOIN pi.brand b GROUP BY pi.brand.id")
    List<BrandTotalPriceDTO> findTotalPriceByBrandId();

    /**
     * 최저가격 브랜드의 카테고리별 금액 조회
     *
     * @param brandId
     * @return
     */
    @Query("SELECT new com.musinsa.product.dto.CategoryInfoDTO(c.name, pi.price) FROM ProductInfo pi JOIN pi.category c WHERE pi.brand.id = :brandId")
    List<CategoryInfoDTO> findCategoryNameAndPriceByBrandId(@Param("brandId") Long brandId);

    /**
     * 카테고리 이름으로 최저가격 브랜드, 금액 조회
     *
     * @param categoryName
     * @return
     */
    @Query("SELECT new com.musinsa.product.dto.BrandPriceDTO(b.name, pi.price) FROM ProductInfo pi JOIN pi.brand b WHERE pi.price = (SELECT MIN(pi2.price) FROM ProductInfo pi2 JOIN pi2.category c WHERE pi2.category.name = :categoryName)")
    List<BrandPriceDTO> findBrandNameAndMinPriceByCategoryName(@Param("categoryName") String categoryName);

    /**
     * 카테고리 이름으로 최고가격 브랜드, 금액 조회
     *
     * @param categoryName
     * @return
     */
    @Query("SELECT new com.musinsa.product.dto.BrandPriceDTO(b.name, pi.price) FROM ProductInfo pi JOIN pi.brand b WHERE pi.price = (SELECT MAX(pi2.price) FROM ProductInfo pi2 JOIN pi2.category c WHERE pi2.category.name = :categoryName)")
    List<BrandPriceDTO> findBrandNameAndMaxPriceByCategoryName(@Param("categoryName") String categoryName);
}
