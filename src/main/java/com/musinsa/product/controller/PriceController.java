package com.musinsa.product.controller;

import com.musinsa.product.response.ResponseObject;
import com.musinsa.product.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/price")
public class PriceController {

    private final PriceService priceService;

    @Operation(summary = "카테고리별 최저가격 브랜드, 가격, 총액 조회", description = "카테고리별로 최저가인 브랜드와 총액을 조회한다.")
    @GetMapping("/min-category")
    public ResponseObject getMinPriceByCategory() {
        return priceService.getMinPriceByCategory();
    }

    @Operation(summary = "단일브랜드별 최저가격 브랜드, 총액 조회", description = "단일 브랜드로 전체 카테고리 구매시 최저가격인 브랜드와 총액을 조회한다.")
    @GetMapping("/min-brand")
    public ResponseObject getMinPriceByBrand() {
        return priceService.getMinPriceByBrand();
    }

    @Operation(summary = "카테고리로 최저, 최고 가격 브랜드와 상품 가격 조회", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회한다.")
    @GetMapping("/min-max")
    public ResponseObject getMinMaxPriceByCategory(@RequestParam String categoryName) {
        return priceService.getMinMaxPriceByCategory(categoryName);
    }

}
