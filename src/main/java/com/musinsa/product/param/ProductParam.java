package com.musinsa.product.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
public class ProductParam {
    @Schema(description = "브랜드명", example = "A")
    @NonNull
    private String brandName;
    @Schema(description = "카테고리명", example = "상의")
    @NonNull
    private String categoryName;
    @Schema(description = "가격", example = "1000")
    @NonNull
    private BigDecimal price;
}
