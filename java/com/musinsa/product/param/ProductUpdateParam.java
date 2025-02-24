package com.musinsa.product.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
public class ProductUpdateParam {
    @Schema(description = "상품 ID", example = "1")
    @NonNull
    private Long productId;
    @Schema(description = "브랜드 ID", example = "1")
    private Long brandId;
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;
    @Schema(description = "가격", example = "1000")
    private BigDecimal price;
}
