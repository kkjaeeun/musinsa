package com.musinsa.product.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BrandTotalPriceDTO {
    private Long brandId;
    private String brandName;
    private BigDecimal totalPrice;

    public BrandTotalPriceDTO(Long brandId, String brandName, BigDecimal totalPrice) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.totalPrice = totalPrice;
    }
}
