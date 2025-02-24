package com.musinsa.product.service;

import com.musinsa.product.dto.*;
import com.musinsa.product.entity.Category;
import com.musinsa.product.repository.CategoryRepository;
import com.musinsa.product.response.CategoryMinMaxResponse;
import com.musinsa.product.repository.ProductInfoRepository;
import com.musinsa.product.response.BrandPriceResponse;
import com.musinsa.product.response.CategoryPriceResponse;
import com.musinsa.product.response.ResponseObject;
import com.musinsa.product.util.PriceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PriceService {

    private final ProductInfoRepository productInfoRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리별 최저가격인 브랜드와 가격, 총액 조회
     *
     * @return
     */
    public ResponseObject getMinPriceByCategory() {
        try {
            CategoryPriceResponse response = new CategoryPriceResponse();
            // 카테고리별 최저가격 브랜드, 가격 조회
            List<Object[]> minPriceCategoryList = productInfoRepository.findBrandNameAndMinPriceByCategoryId();


            List<CategoryPriceDTO> categoryPriceList = minPriceCategoryList.stream()
                    .map(result -> new CategoryPriceDTO(
                            (String) result[0],
                            (String) result[1],
                            PriceUtil.format((BigDecimal) result[2])
                    )).collect(Collectors.toList());

            // 총합 계산
            String totalPrice = PriceUtil.format(minPriceCategoryList.stream()
                            .map(result -> (BigDecimal) result[2])
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                    );

            response.setCategoryPriceList(categoryPriceList);
            response.setTotalPrice(totalPrice);
            return new ResponseObject(response);
        } catch (Exception e) {
            return new ResponseObject("Error", e.getMessage());
        }
    }

    /**
     * 브랜드별 전체 카테고리 구매시 최저가격 브랜드와 총액 조회
     *
     * @return
     */
    public ResponseObject getMinPriceByBrand() {
        BrandPriceResponse response = new BrandPriceResponse();
        // 브랜드별 총액 조회
        List<BrandTotalPriceDTO> totalPriceList = productInfoRepository.findTotalPriceByBrandId();

        Optional<BrandTotalPriceDTO> minPriceBrand = totalPriceList.stream()
                .min(Comparator.comparing(BrandTotalPriceDTO::getTotalPrice));

        // 최저가격 브랜드
        BrandTotalPriceDTO brand = minPriceBrand.get();
        // 최저가격 브랜드의 카테고리별 금액 조회
        List<CategoryInfoDTO> categoryInfoList = productInfoRepository.findCategoryNameAndPriceByBrandId(brand.getBrandId());

        response.setBrandName(brand.getBrandName());
        response.setCategoryInfoList(categoryInfoList);
        response.setTotalPrice(PriceUtil.format(brand.getTotalPrice()));

        return new ResponseObject(response);
    }

    /**
     * 카테고리로 최저, 최고 가격 브랜드와 상품 가격 조회
     *
     * @param categoryName
     * @return
     */
    public ResponseObject getMinMaxPriceByCategory(String categoryName) {
        CategoryMinMaxResponse response = new CategoryMinMaxResponse();
        if (null == categoryName || "".equals(categoryName)) {
            return new ResponseObject("Error", "카테고리 이름을 입력해주세요.");
        }
        // 없는 카테고리 검색시
        Category category = categoryRepository.findFirstByName(categoryName);
        if (category == null) {
            return new ResponseObject("Error", "존재하지 않는 카테고리입니다.");
        }

        response.setCategoryName(categoryName);
        List<BrandPriceDTO> minPrice = productInfoRepository.findBrandNameAndMinPriceByCategoryName(categoryName);
        List<BrandPriceDTO> maxPrice = productInfoRepository.findBrandNameAndMaxPriceByCategoryName(categoryName);
        response.setMinPrice(minPrice);
        response.setMaxPrice(maxPrice);

        return new ResponseObject(response);
    }
}
