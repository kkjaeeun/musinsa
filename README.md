# [MUSINSA] Java(Kotlin) Backend Engineer - 과제

|기술 스택|버전|
|--|--|
|Spring boot|3.3.0|
|jdk|20|
|Gradle|8.2|
|DB|H2|

## 구현 범위
* ### swagger UI 구현
  : localhost:8080/swagger-ui/index.htm 를 통해 API를 확인할 수 있다.
* ### Front 페이지 구현
  : localhost:8080/ 페이지 진입시, API 통신을 할 수 있는 화면을 제공.
* ### API 설명
  * #### 구현 1) GET '/api/price/min-category'
    * 카테고리별 브랜드의 상품 금액이 낮은순으로 rank를 정한 다음 가장 적은 금액의 상품 정보를 조회 (Native Query 사용)
    * 조회해온 상품의 총액을 계산
  * #### 구현 2) GET '/api/price/min-brand'
    * 브랜드별 총액을 조회해온 후 로직으로 구현하여 가장 낮은 브랜드를 찾고
    * 해당 브랜드의 카테고리별 금액을 조회
  * #### 구현 3) GET '/api/price/min-max' 
    * Parameter : String categoryName
    * 입력한 카테고리의 최저, 최고 금액에 해당하는 브랜드와 금액을 각각 조회하고, 합쳐서 반환
  * #### 구현 4-1) POST 'api/product/brand/add'
    * Parameter : String name
    * 입력된 브랜드명이 빈값이거나 이미 존재할 경우 Error 메시지를 반환
    * 새로운 브랜드이름인 경우 저장처리
  * #### 구현 4-2-1) POST 'api/product/add-name'
    * 카테고리명, 브랜드명을 직접 입력하여 상품을 추가하는 경우
    * 입력한 카테고리명이 존재할 경우 브랜드명의 존재여부 체크
    * 브랜드명이 존재하지 않을 경우, 브랜드를 새로 추가하고 상품을 등록
  * #### 구현 4-2-2) POST 'api/product/add'
    * 프론트 화면에서 select box로 넘어온 id 값을 통해 상품을 등록
  * #### 구현 4-3) PUT 'api/product/brand/update'
    * Parameter : Long productId, Long brandId, Long categoryId, BigDecimal price
    * 상품ID로 상품이 존재할 경우 카테고리가 수정되었으면 수정된 카테고리가 존재하는지 체크
    * 카테고리가 존재할 경우 브랜드ID가 변경됐을 경우 브랜드가 존재하는지 확인
    * 변경된 값을 새로 셋팅한 후 저장
  * #### 구현 4-4) DELETE 'api/product/brand/delete'
    * Parameter : Long productId
    * 상품ID를 통해 해당하는 상품을 삭제.
  
  
## 코드 빌드, 테스트, 실행 방법

## ERD
https://github.com/kkjaeeun/musinsa/blob/master/ERD.png

## 테이블 정의
1. brand 테이블
  - 용도 : 브랜드 정보를 저장.
  - id : PRIMARY KEY로 사용하며 AUTO_INCREMENT로 자동 생성.
  - name : 브랜드의 이름을 저장하는 필드로, NULL을 허용하지 않고 UNIQUE한 값을 갖는다.

2. category 테이블
  - 용도 : 카테고리 정보를 저장.
  - id :  PRIMARY KEY로 사용하며 AUTO_INCREMENT로 자동 생성.
  - name : 카테고리 이름을 저장하는 필드로, NULL을 허용하지 않고 UNIQUE한 값을 갖는다.

3. product_info 테이블
  - id : PRIMARY KEY로 사용하며 AUTO_INCREMENT로 자동 생성.
  - brand_id : 브랜드 테이블의 id를 저장. NULL을 허용하지 않고 brand 테이블을 참조한다.
  - category_id : 카테고리 테이블의 id를 저장. NULL을 허용하지 않고 category 테이블을 참조한다.
  - price : 상품의 가격을 저장하는 필드로, NULL을 허용하지 않는다.


