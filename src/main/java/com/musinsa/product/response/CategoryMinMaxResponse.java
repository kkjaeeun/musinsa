package com.musinsa.product.response;

import com.musinsa.product.dto.BrandPriceDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryMinMaxResponse {
    private String categoryName;
    private List<BrandPriceDTO> minPrice;
    private List<BrandPriceDTO> maxPrice;
}
