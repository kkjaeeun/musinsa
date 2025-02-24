package com.musinsa.product.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ProductDeleteParam {
    @Schema(description = "상품 ID", example = "1")
    @NonNull
    private Long productId;
}
