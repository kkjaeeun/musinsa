package com.musinsa.product.dto;

import lombok.Getter;


@Getter
public class CategoryPriceDTO {
    private String categoryName;
    private String brandName;
    private String price;

    public CategoryPriceDTO(String categoryName, String brandName, String price) {
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.price = price;
    }
}
