## VelacorpProductService

### Tổng quan

**VelacorpProductService** là một demo dịch vụ microservice chịu trách nhiệm quản lý dữ liệu sản phẩm. Dịch vụ này được phát triển bằng Java và thiết kế để triển khai trong môi trường container hóa sử dụng Docker.

### Môi trường

- **Java Development Kit (JDK) 17**
- **Docker**

### Hướng dẫn bắt đầu

#### Bước 1: Kéo source code

```bash
git clone https://github.com/thieucong98/velacorpProductService
cd velacorpProductService
```

#### Bước 2: Cập nhật thông tin cấu hình

Cập nhật thông tin database và Kafka trong các file sau:

- `velacorp-product-service/src/main/resources/config/application.yml`
- `velacorp-product-service/src/main/resources/config/application-dev.yml`

(Nếu chạy bằng Docker thì không cần phải chỉnh sửa)

#### Bước 3: Chạy ứng dụng

**Cách 1: Chạy bằng source code**

- Khởi động các service cần thiết trong file `velacorp-product-service/src/main/docker/services.yml`
- Vào thư mục source code, sau đó sử dụng lệnh sau để start ứng dụng:

```bash
./mvnw
```

**Cách 2: Chạy bằng Docker**

- Build Docker file bằng lệnh:

```bash
./mvnw -ntp verify -DskipTests jib:dockerBuild
```

- Chạy toàn bộ service trong file `velacorp-product-service/src/main/docker/app.yml`

#### Bước 4: Kiểm tra ứng dụng

- Sử dụng Swagger Editor để thực hiện test: truy cập http://localhost:7742/
- Truy cập http://localhost:8081/v3/api-docs để lấy tài liệu API.
- Sau khi lấy xong, paste vào Swagger Editor.
- Các API sẽ được liệt kê ở bên phải.
- Có thể thao tác test trực tiếp trên trình duyệt.

### Liên hệ

Có thể tham khảo file README.md trong repository để biết thêm thông tin chi tiết.

Nếu có bất kỳ câu hỏi hoặc vấn đề nào, vui lòng mở issue trên GitHub repository hoặc liên hệ với các nhà phát triển.
