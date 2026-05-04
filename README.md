# MiniProject - Mini E-Commerce

Ung dung Spring Boot MVC cho he thong thuong mai dien tu mini su dung Spring Data JPA va Thymeleaf.

## Tinh nang
- CRUD danh muc va san pham (Admin)
- Trang san pham co phan trang va tim kiem theo nhieu tieu chi
- Gio hang theo session
- Dat hang transactional, tru ton kho
- Dashboard doanh thu va top san pham

## Cau truc du an
```
/miniProject
|-- build.gradle
|-- settings.gradle
|-- README.md
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   `-- com/demominiproject/
|   |   |       |-- DemoMiniProjectApplication.java
|   |   |       |-- config/
|   |   |       |   |-- DataInitializer.java
|   |   |       |   `-- WebConfig.java
|   |   |       |-- controller/
|   |   |       |   |-- AdminCategoryController.java
|   |   |       |   |-- AdminProductController.java
|   |   |       |   |-- AdminDashboardController.java
|   |   |       |   |-- StorefrontController.java
|   |   |       |   |-- CartController.java
|   |   |       |   |-- CheckoutController.java
|   |   |       |   |-- AppErrorController.java
|   |   |       |   `-- GlobalExceptionHandler.java
|   |   |       |-- dto/
|   |   |       |   |-- CheckoutForm.java
|   |   |       |   `-- ProductSearchCriteria.java
|   |   |       |-- exception/
|   |   |       |   |-- InsufficientStockException.java
|   |   |       |   `-- ResourceNotFoundException.java
|   |   |       |-- model/
|   |   |       |   |-- Cart.java
|   |   |       |   |-- CartItem.java
|   |   |       |   |-- Category.java
|   |   |       |   |-- Product.java
|   |   |       |   |-- Order.java
|   |   |       |   |-- OrderDetail.java
|   |   |       |   `-- User.java
|   |   |       |-- repository/
|   |   |       |   |-- CategoryRepository.java
|   |   |       |   |-- ProductRepository.java
|   |   |       |   |-- OrderRepository.java
|   |   |       |   |-- OrderDetailRepository.java
|   |   |       |   |-- UserRepository.java
|   |   |       |   `-- TopProductProjection.java
|   |   |       |-- service/
|   |   |       |   |-- CategoryService.java
|   |   |       |   |-- ProductService.java
|   |   |       |   |-- CartService.java
|   |   |       |   |-- OrderService.java
|   |   |       |   |-- DashboardService.java
|   |   |       |   `-- FileStorageService.java
|   |   |       `-- specification/
|   |   |           `-- ProductSpecifications.java
|   |   `-- resources/
|   |       |-- application.properties
|   |       |-- static/
|   |       |   `-- css/style.css
|   |       `-- templates/
|   |           |-- fragments/header.html
|   |           |-- admin/
|   |           |   |-- category-form.html
|   |           |   |-- category-list.html
|   |           |   |-- product-form.html
|   |           |   |-- product-list.html
|   |           |   `-- dashboard.html
|   |           `-- storefront/
|   |               |-- index.html
|   |               |-- cart.html
|   |               |-- checkout.html
|   |               `-- order-success.html
|   `-- test/
|       `-- java/com/demominiproject/
|           `-- DemoMiniProjectApplicationTests.java
```

## Thiet lap
Cap nhat thong tin database trong `src/main/resources/application.properties`.

Vi du:
```
spring.datasource.url=jdbc:mysql://localhost:3306/demo_mini_project_ss16?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
```

## Chay ung dung
```zsh
./gradlew bootRun
```

## Chay test
```zsh
./gradlew test
```
