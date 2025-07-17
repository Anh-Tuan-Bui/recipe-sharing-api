# 🍲 Recipe Sharing API – Nền tảng chia sẻ công thức nấu ăn

[![Java](https://img.shields.io/badge/Java-21-blue)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green)]()
[![License](https://img.shields.io/badge/license-MIT-lightgrey)]()

## 📘 Giới thiệu

**Recipe Sharing API** là một dự án xây dựng nền tảng chia sẻ công thức nấu ăn, nơi người dùng có thể tạo, tìm kiếm, và đánh giá các công thức. Dự án có phân vai trò người dùng (admin, đầu bếp & người dùng thường) và hỗ trợ đăng bài

---

## 🎯 Tính năng chính
- 🧑‍🍳 Phân quyền người dùng:
  - `Chef`: được phép đăng và quản lý công thức
  - `User`: chỉ được xem, tìm kiếm, đánh giá
  - `Admin`: quản lý toàn bộ hệ thống

- 📝 Quản lý công thức:
  - Tạo / chỉnh sửa / xoá công thức nấu ăn
  - Gán category / label cho công thức
  - Hình ảnh, mô tả, nguyên liệu, cách làm

- 🔍 Tìm kiếm nâng cao:
  - Theo tên công thức
  - Theo danh mục / nhãn đã gán
  - Có phân trang và sắp xếp

---

## 🏗️ Kiến trúc tổng thể
- Phân tầng rõ ràng: `Controller → Service → Repository`
- Sử dụng DTO cho dữ liệu đầu vào/ra
- Áp dụng Spring Security + JWT cho phân quyền

---

## 📁 Cấu trúc thư mục chính
```
src/
├── config/ # Cấu hình chung của hệ thống
│ └── security/ # Cấu hình security
│ └── ... # Các cấu hình khác: ModelMapper, Cloudinary, v.v...
├── controller/ # REST API
├── dto/ # Định nghĩa các request/response DTO
│ └── request/ 
│ └── response/
├── entity/ # Entity JPA
├── enums/ # Các enum
├── exception/ # Xử lý lỗi tập trung
├── repository/ # Repository lớp tương tác DB
├── security/ # Impl các thành phần có sẵn của Spring Security
├── service/ # Business logic
│ └── impl/ # Implement của service
└── application.yml # Cấu hình hệ thống
```

---

## ⚙️ Công nghệ sử dụng

| Thành phần     | Công nghệ |
|----------------|-----------|
| Ngôn ngữ       | Java 21 |
| Backend        | Spring Boot 3 |
| ORM            | JPA / Hibernate |
| Bảo mật        | Spring Security + JWT |
| Database       | MySQL |
| Cloud          | Cloudinary |
| Build Tool     | Maven |

---

## ▶️ Hướng dẫn chạy dự án

### 1. Yêu cầu hệ thống
- Java 17+
- Maven 3.8+
- MySQL 8+
- Postman để test API

### 2. Cài đặt
#### Chạy bằng IntelliJ / VScode
- Clone repo:
    `git clone https://github.com/Anh-Tuan-Bui/recipe-sharing-api.git`
- Mở project trong IntelliJ
- Cấu hình file src/main/resources/application.yml theo MySQL của bạn
- Chạy file RecipeSharingApplication.java

#### Chạy bằng terminal
```bash
# Clone repo
git clone https://github.com/Anh-Tuan-Bui/recipe-sharing-api.git
cd recipe-sharing-api

# Cập nhật cấu hình DB và Redis trong file: src/main/resources/application.yml

# Build và chạy
./mvnw spring-boot:run
```

### 3. Truy cập API (Sử dụng Postman)
API Base URL: http://localhost:8080/api/recipes

---

## 🌐 Một số API tiêu biểu
| Method | Endpoint | Vai trò |	Mô tả |
|--------|----------|---------|-------|
| `POST` | `/api/auth/register` | Public | Đăng ký tài khoản |
| `POST` | `/api/auth/login` | Public | Đăng nhập và nhận JWT |
| `POST` | `/api/recipes` | CHEF | Tạo công thức (kèm nhiều ảnh)|
| `GET` | `/api/recipes` | All | Lấy danh sách công thức |
| `GET` | `/api/recipes/{id}` | All | Xem chi tiết công thức |
| `GET` | `/api/categories`	| All |	Lấy danh sách danh mục |

---

## 📄 License
Dự án sử dụng giấy phép MIT – phục vụ mục đích học tập và chia sẻ cộng đồng.
