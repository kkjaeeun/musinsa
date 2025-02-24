package com.musinsa.product.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BrandParam {
    @Schema(description = "브랜드명", example = "A")
    @NotBlank(message = "브랜드 이름을 입력해주세요.")
    private String name;
}
