<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <title>Musinsa Homework</title>
    <style>
        .tabs {
            display: flex;
            cursor: pointer;
            list-style: none;
        }

        .tabs li {
            padding: 10px;
            background-color: black;
            cursor: pointer;
            color: white;
        }

        .tabs li.active {
            background-color: black;
            color: darkgray;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        #modifyPopup {
            display: none;
            position: fixed;
            top: 50%;
            left: 30%;
            background-color: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .json-list {
            list-style: none;
            padding: 0;
        }

        .json-list li {
            /*background-color: #f9f9f9;*/
            margin: 10px 0;
            /*padding: 15px;*/
            /*border: 1px solid #ddd;*/
            /*border-radius: 5px;*/
        }

        .json-list span {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <ul class="tabs">
        <li class="tab-link active" data-tab="minCategory">구현 1</li>
        <li class="tab-link" data-tab="minBrand">구현 2</li>
        <li class="tab-link" data-tab="minMaxPrice">구현 3</li>
        <li class="tab-link" data-tab="productManage">구현 4</li>
    </ul>

    <div class="tab-content active minCategory">
        <p>카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API</p>
        <button type="button" id="minCategoryBtn">조회</button>
        <br/><br/><br/>

        <table border="1" width="300">
            <thead>
                <tr>
                    <th>카테고리</th>
                    <th>브랜드</th>
                    <th>가격</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <div class="tab-content minBrand">
        <p>단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API</p>
        <button type="button" id="minBrandBtn">조회</button>
        <br/><br/><br/>
        <div id="minBrandResult"></div>
    </div>

    <div class="tab-content minMaxPrice">
        <h1>구현 3</h1>
        <p>카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API</p>
        <input type="text" name="categoryName"/>
        <button type="button" id="minMaxPriceBtn">조회</button>
        <br/><br/><br/>
        <div id="minMaxResult"></div>
    </div>

    <div class="tab-content productManage">
        <p>브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API</p>
        <span>브랜드 추가 : </span><input type="text" name="brandName"/>
        <button type="button" id="addBrandBtn">브랜드 추가</button>
        <br/><br/><br/>

        <select id="brandSelect" name="brandId" style="min-width: 50px">
            <c:forEach var="option" items="${data.brands}">
                <option value="${option.id}">${option.name}</option>
            </c:forEach>
        </select>
        <select id="categorySelect" name="categoryId" style="min-width: 70px">
            <c:forEach var="option" items="${data.categories}">
                <option value="${option.id}">${option.name}</option>
            </c:forEach>
        </select>
        <input type="text" id="price" name="price"/>
        <button type="button" id="addProductBtn">상품 추가</button>
        <br/><br/><br/>

        <table border="1" width="1000px">
            <thead>
                <tr>
                    <th>브랜드</th>
                    <th>카테고리</th>
                    <th>가격</th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="productList">
            </tbody>
        </table>
    </div>

    <div id="modifyPopup">
        <h2>상품 수정</h2>
        <input type="hidden" name="productId"/>
        <select id="modifyBrand" name="modifyBrand" style="min-width: 50px">
            <c:forEach var="option" items="${data.brands}">
                <option value="${option.id}">${option.name}</option>
            </c:forEach>
        </select>
        <select id="modifyCategory" name="modifyCategory" style="min-width: 70px">
            <c:forEach var="option" items="${data.categories}">
                <option value="${option.id}">${option.name}</option>
            </c:forEach>
        </select>
        <input type="text" id="modifyPrice" name="price"/>

        <button type="button" id="saveBtn">저장</button>
        <button type="button" id="cancelModify">취소</button>
    </div>

    <script type="text/javascript">
        const tabs = {
            tab : function () {
                $('.tab-link').click(function () {
                    const targetTab = $(this).data('tab');
                    $('.tab-link').removeClass('active');
                    $('.tab-content').removeClass('active');

                    $(this).addClass('active');
                    $('.' + targetTab).addClass('active');
                });
            },

        }
        const search = {
            init : function () {
                this.button();
            },

            button : function () {
                $('#minCategoryBtn').click(function () {
                    $.ajax({
                        url : '/api/price/min-category',
                        type : 'GET',
                        contentType: 'application/json',
                        success: res => {
                            const tbody = $('.minCategory').find('tbody')
                            const data = res.data;
                            let html = '';
                            $.each(data.categoryPriceList, function (index, value) {
                                html += '<tr>';
                                html += '<td>' + value.categoryName + '</td>';
                                html += '<td>' + value.brandName + '</td>';
                                html += '<td>' + value.price + '</td>';
                                html += '</tr>';
                            });
                            html += '<tr>';
                            html += '<td colspan="2">총액</td>';
                            html += '<td>' + data.totalPrice + '</td>';
                            html += '</tr>';
                            tbody.html(html);

                        }
                    });
                });

                $("#minBrandBtn").click(function () {
                    $.ajax({
                        url : '/api/price/min-brand',
                        type : 'GET',
                        contentType: 'application/json',
                        success: res => {
                            $('#minBrandResult').empty();

                            let data = res.data;
                            let list = $('<ul class="json-list"></ul>');

                            list.append('<li><span>{ "최저가" : </span><br></li>');
                            list.append('<li><span>{ "브랜드" : </span>"' + data.brandName + '", [</li>');
                            let category = data.categoryInfoList;
                            $.each(category, function (index, item) {
                                let listItem = $('<li></li>');
                                listItem.append('<span>{ "카테고리" : </span>"' + item.categoryName + '", <span>"가격" : </span>"' + item.price + '" }<br>');
                                list.append(listItem);
                            });
                            list.append('<li> ],</li><li><span>"총액" : </span>"' + data.totalPrice + '"}</li><li>}</li>');
                            $('#minBrandResult').append(list);
                        }
                    });
                });

                $('#minMaxPriceBtn').click(function () {
                    $.ajax({
                        url : '/api/price/min-max',
                        type : 'GET',
                        data : {
                            categoryName : $('input[name="categoryName"]').val()
                        },
                        success: res => {
                            if (res.status === 'Error') {
                                alert(res.message);
                                return;
                            }

                            $('#minMaxResult').empty();

                            let data = res.data;
                            let list = $('<ul class="json-list"></ul>');

                            list.append('<li><span>{ "카테고리" : </span>"' + data.categoryName + '"</li>');
                            list.append('<li><span>{ "최저가" : </span>" : [</li>');

                            $.each(data.minPrice, function (index, item) {
                                let listItem = $('<li></li>');
                                listItem.append('<span>{ "브랜드" : </span>"' + item.brandName + '", <span>"가격" : </span>"' + item.price + '" }<br>');
                                list.append(listItem);
                            });

                            list.append('<li> ],</li>');
                            list.append('<li><span>{ "최고가" : </span>" : [</li>');

                            $.each(data.maxPrice, function (index, item) {
                                let listItem = $('<li></li>');
                                listItem.append('<span>{ "브랜드" : </span>"' + item.brandName + '", <span>"가격" : </span>"' + item.price + '" }<br>');
                                list.append(listItem);
                            });
                            list.append('<li> ]</li><li> }</li>');
                            $('#minMaxResult').append(list);
                        }
                    });
                });
            }
        }

        const manage = {
            init : function () {
                this.button();
                this.list();
            },
            button : function () {
                // 브랜드 추가 버튼 클릭
                $('#addBrandBtn').click(function () {
                    $.ajax({
                        url : 'api/product/brand/add',
                        type : 'POST',
                        contentType: 'application/json',
                        data : JSON.stringify({
                            name: $('input[name="brandName"]').val()
                        }),
                        success : res => {
                            alert(res.message);
                            // 등록 성공시 select box 리로드
                            if (res.status === 'Success') {
                                $('input[name="brandName"]').val('');
                                $('#brandSelect').empty();
                                $.each(res.data, function (index, value){
                                    $('#brandSelect').append('<option value="' + value.id + '">' + value.name + '</option>');
                                });
                            }
                        }
                    });
                });

                // 상품 추가 버튼 클릭
                $('#addProductBtn').click(function (){
                    $.ajax({
                        url : 'api/product/add',
                        type : 'POST',
                        contentType: 'application/json',
                        data : JSON.stringify({
                            brandId: $('#brandSelect').val(),
                            categoryId: $('#categorySelect').val(),
                            price: $('#price').val()
                        }),
                        success : res => {
                            alert(res.message);
                            manage.list();
                        }
                    });
                });

                // 수정 버튼 클릭 이벤트
                $('#productList').on('click', '.modifyBtn', function () {
                    let targetId = $(this).parent('.btnArea').data('id');
                    let brandId = $('#product_'+targetId).find('.spanBrand').data('id');
                    let categoryId = $('#product_'+targetId).find('.spanCategory').data('id');
                    let price = $('#product_'+targetId).find('.spanPrice').data('value');

                    $('#modifyPopup').css("display", "block");
                    $('input[name="productId"]').val(targetId);
                    $('#modifyBrand').val(brandId);
                    $('#modifyCategory').val(categoryId);
                    $('#modifyPrice').val(price);
                });

                // 수정 내용 저장
                $('#saveBtn').click(function () {
                    $.ajax({
                        url : 'api/product/update',
                        type : 'PUT',
                        contentType: 'application/json',
                        data : JSON.stringify({
                            productId : $('input[name="productId"]').val(),
                            brandId : $('#modifyBrand').val(),
                            categoryId : $('#modifyCategory').val(),
                            price : $('#modifyPrice').val()
                        }),
                        success : res => {
                            alert(res.message);
                            manage.list();
                            $('#modifyPopup').fadeOut();
                        }
                    });
                });

                // 취소 버튼 클릭 이벤트
                $('#cancelModify').click(function () {
                    $('#modifyPopup').fadeOut();
                });

                // 상품 삭제 버튼 클릭
                $('#productList').on('click', '.deleteBtn', function () {
                    $.ajax({
                        url : 'api/product/delete',
                        type : 'DELETE',
                        contentType: 'application/json',
                        data : JSON.stringify({
                            productId: $(this).parent('.btnArea').data('id')
                        }),
                        success : res => {
                            alert(res.message);
                            manage.list();
                        }
                    });
                });

            },

            list : function () {
                $.ajax({
                    url : 'api/product/list',
                    type : 'GET',
                    contentType: 'application/json',
                    success : res => {
                        const data = res.data;
                        let html = '';
                        $.each(data, function (index, value) {
                            html += '<tr id="product_' + value.id + '">';
                            html += '<td><span class="spanBrand" data-id="'+ value.brand.id +'">' + value.brand.name + '</span></td>';
                            html += '<td><span class="spanCategory" data-id="'+ value.category.id +'">' + value.category.name + '</span></td>';
                            html += '<td><span class="spanPrice" data-value="'+ value.price +'">' + value.price + '</span></td>';
                            html += '<td class="btnArea" data-id="' + value.id + '"><button type="button" class="modifyBtn">수정</button>';
                            html += '<button type="button" class="deleteBtn">삭제</button></td>';
                            html += '</tr>';
                        });
                        $('#productList').html(html);
                    }
                });
            }
        }
        $(document).ready(function() {
            tabs.tab();
            search.init();
            manage.init();
        });
    </script>
</body>
</html>