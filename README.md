# ClickMart — Ecommerce Backend

> A production-ready Spring Boot REST API powering the ClickMart ecommerce platform. It handles authentication, product catalogue, orders, payments, and more.

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Features & Flows](#features--flows)
- [Prerequisites](#prerequisites)
- [Environment Setup](#environment-setup)
- [Run Instructions](#run-instructions)
- [API Overview](#api-overview)
- [Third-Party Integrations](#third-party-integrations)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.0 |
| ORM | Spring Data JPA (Hibernate) |
| Database | MySQL 8+ |
| Security | Spring Security + JWT (JJWT 0.11.5) |
| Payments | Razorpay Java SDK 1.4.8 |
| Image Storage | Cloudinary (cloudinary-http44 1.37.0) |
| Email | Spring Boot Starter Mail (Gmail SMTP) |
| Validation | Spring Boot Starter Validation (Bean Validation) |
| Build Tool | Apache Maven (Maven Wrapper included) |
| Testing | JUnit 5 + Spring Security Test + H2 (in-memory) |

---

## Project Structure

```
Ecommerce_Backend/
├── pom.xml                          # Maven build descriptor & dependencies
├── mvnw / mvnw.cmd                  # Maven wrapper scripts
├── init.sql                         # Database initialization and sample data script
└── src/
    ├── main/
    │   ├── java/com/clickmart/backend/
    │   │   ├── EcommerceBackendApplication.java   # Spring Boot entry point
    │   │   ├── config/                            # Security & JWT infrastructure
    │   │   │   ├── SecurityConfig.java            # CORS, filter chain, public/private routes
    │   │   │   ├── JwtUtil.java                   # Token generation & validation
    │   │   │   ├── JwtAuthenticationFilter.java   # Per-request JWT filter
    │   │   │   ├── CustomUserDetailsService.java  # UserDetails impl from DB
    │   │   │   └── SecurityUtils.java             # Helper to get current user
    │   │   ├── entity/                            # JPA entity classes (DB tables)
    │   │   │   ├── User.java
    │   │   │   ├── Product.java
    │   │   │   ├── Category.java
    │   │   │   ├── CartItem.java
    │   │   │   ├── Order.java
    │   │   │   ├── OrderItem.java
    │   │   │   ├── Payment.java
    │   │   │   ├── Address.java
    │   │   │   ├── Coupon.java
    │   │   │   ├── DeliveryOption.java
    │   │   │   ├── Notification.java
    │   │   │   ├── Review.java
    │   │   │   ├── Ticket.java
    │   │   │   ├── WishlistItem.java
    │   │   │   ├── PasswordResetToken.java
    │   │   │   └── RevokedToken.java
    │   │   ├── dto/                               # Data Transfer Objects (request/response)
    │   │   ├── enums/                             # Shared enumerations (roles, statuses, etc.)
    │   │   ├── exceptions/                        # Global exception handlers
    │   │   ├── service/                           # Cross-cutting shared services
    │   │   └── features/                          # Feature modules (each has controller/service/repo/dto)
    │   │       ├── auth/                          # Registration, login, OTP reset
    │   │       ├── product/                       # Product CRUD, image upload
    │   │       ├── category/                      # Category management
    │   │       ├── cart/                          # Add/update/remove cart items
    │   │       ├── order/                         # Place orders, order status
    │   │       ├── payment/                       # Razorpay order creation & verification
    │   │       ├── coupon/                        # Coupon creation & validation
    │   │       ├── inventory/                     # Stock management
    │   │       ├── address/                       # Saved addresses
    │   │       ├── profile/                       # User profile & password change
    │   │       ├── wishlist/                      # Wishlist add/remove
    │   │       ├── review/                        # Product reviews & ratings
    │   │       ├── support/                       # Customer support tickets
    │   │       ├── notification/                  # In-app notifications
    │   │       ├── delivery/                      # Delivery options
    │   │       ├── dashboard/                     # Admin analytics & stats
    │   │       ├── customer/                      # Admin customer management
    │   │       └── admin/                         # Admin-only utilities
    │   └── resources/
    │       ├── application-local.properties        # Your private secrets (git-ignored)
    │       ├── application-local.properties.example # Template — copy & fill in
    │       └── templates/                         # Email templates (HTML)
    └── test/                                      # Unit & integration tests (H2 in-memory)
```

---

## Features & Flows

### 1. Authentication Flow
```
Client → POST /api/auth/register → User created (BCrypt password hashed)
Client → POST /api/auth/login    → JWT Access Token returned
Client → [Bearer Token header]   → JwtAuthenticationFilter validates → SecurityContext set
Client → POST /api/auth/logout   → Token revoked (stored in RevokedToken table)

Forgot Password:
Client → POST /api/auth/forgot-password (email) → OTP sent via Gmail SMTP
Client → POST /api/auth/verify-otp             → OTP validated
Client → POST /api/auth/reset-password         → Password updated
```

### 2. Product & Category Flow
```
Admin → POST /api/products       → Create product (image uploaded to Cloudinary)
Admin → PUT  /api/products/{id}  → Update product / price sync
Admin → PATCH /api/inventory/{productId} → Update stock levels

Customer → GET /api/products            → Browse with filter/sort/search
Customer → GET /api/products/{id}       → Product detail + reviews
Customer → POST /api/reviews            → Submit rating & review
```

### 3. Cart & Checkout Flow
```
Customer → POST   /api/cart          → Add item to cart
Customer → PATCH  /api/cart/{itemId} → Update quantity
Customer → DELETE /api/cart/{itemId} → Remove item

Customer → POST /api/coupons/validate → Validate coupon code → discount applied
Customer → GET  /api/delivery-options → Fetch available delivery methods
Customer → POST /api/orders           → Place order (cart cleared, inventory decremented)
```

### 4. Payment Flow (Razorpay)
```
Customer → POST /api/payments/create-order → Razorpay order created, orderId returned
Customer → [Razorpay checkout UI]          → Payment completed by user
Customer → POST /api/payments/verify       → Signature verified server-side
                                             → Order status updated to PAID
```

### 5. Admin Flow
```
Admin → GET  /api/admin/dashboard   → Revenue, order stats, top products
Admin → GET  /api/admin/orders      → All orders with filter/status update
Admin → GET  /api/admin/customers   → Customer list & details
Admin → PUT  /api/admin/orders/{id} → Update order status
Admin → GET  /api/admin/tickets     → Support ticket queue
```

---

## Prerequisites

Before running the project, ensure you have the following installed:

| Tool | Version | Notes |
|---|---|---|
| **JDK** | 17+ | [Download Temurin 17](https://adoptium.net/) |
| **Maven** | 3.8+ | Or use the included `mvnw` wrapper |
| **MySQL** | 8.0+ | [Download MySQL Community](https://dev.mysql.com/downloads/) |
| **Git** | Any | For cloning the repo |

> **Optional (for full integration):**
> - A **Cloudinary** account (free tier works) for image uploads
> - A **Razorpay** account (test mode) for payment flow
> - A **Gmail** account with an App Password enabled for OTP emails

---

## Environment Setup

### Step 1 — Create the Database

Log in to MySQL and create the database, then run the initialization script to populate sample data:

```sql
-- 1. Create the database
CREATE DATABASE ecommerce_backend;
USE ecommerce_backend;

-- 2. Run the initialization script (from the project root)
SOURCE init.sql;
```

### Step 2 — Configure Secrets

Copy the example properties file and fill in your values:

```bash
# From the project root
cp src/main/resources/application-local.properties.example \
   src/main/resources/application-local.properties
```

Then edit `application-local.properties`:

```properties
# Database
DB_URL=jdbc:mysql://127.0.0.1:3306/ecommerce_backend
DB_USERNAME=root
DB_PASSWORD=your_mysql_password

# JWT — generate a strong 256-bit random string
JWT_SECRET=your_super_secret_jwt_key_here

# Gmail SMTP (generate App Password at myaccount.google.com/apppasswords)
MAIL_USERNAME=your_gmail@gmail.com
MAIL_PASSWORD=your_gmail_app_password

# Cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# Razorpay
RAZORPAY_KEY_ID=rzp_test_xxxxxxxxxxxx
RAZORPAY_KEY_SECRET=your_razorpay_secret

# CORS — URL of your React frontend
APP_CORS_ALLOWED_ORIGINS=http://localhost:5173
```

> ⚠️ **Never commit `application-local.properties` to Git.** It is already in `.gitignore`.

---

## Run Instructions

### Option A — Using Maven Wrapper (Recommended)

```bash
# Windows
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local

# macOS / Linux
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Option B — Using Installed Maven

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Option C — Build & Run JAR

```bash
# Build
.\mvnw.cmd clean package -DskipTests

# Run
java -jar target/Ecommerce_Backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

The server starts on **`http://localhost:8080`** by default.

### Running Tests

```bash
.\mvnw.cmd test
```

> Tests use an **H2 in-memory database** — no MySQL setup needed for tests.

---

## API Overview

> Base URL: `http://localhost:8080/api`

| Category | Method | Endpoint | Auth Required |
|---|---|---|---|
| **Auth** | POST | `/auth/register` | No |
| | POST | `/auth/login` | No |
| | POST | `/auth/logout` | Yes |
| | POST | `/auth/forgot-password` | No |
| | POST | `/auth/verify-otp` | No |
| | POST | `/auth/reset-password` | No |
| **Products** | GET | `/products` | No |
| | GET | `/products/{id}` | No |
| | POST | `/products` | Admin |
| | PUT | `/products/{id}` | Admin |
| | DELETE | `/products/{id}` | Admin |
| **Categories** | GET | `/categories` | No |
| | POST | `/categories` | Admin |
| **Cart** | GET | `/cart` | Customer |
| | POST | `/cart` | Customer |
| | PATCH | `/cart/{id}` | Customer |
| | DELETE | `/cart/{id}` | Customer |
| **Orders** | POST | `/orders` | Customer |
| | GET | `/orders` | Customer |
| | GET | `/orders/{orderNumber}` | Customer |
| **Payments** | POST | `/payments/create-order` | Customer |
| | POST | `/payments/verify` | Customer |
| **Coupons** | POST | `/coupons/validate` | Customer |
| | POST | `/coupons` | Admin |
| **Admin** | GET | `/admin/dashboard` | Admin |
| | GET/PUT | `/admin/orders` | Admin |
| | GET | `/admin/customers` | Admin |
| | GET | `/admin/tickets` | Admin |
| **Profile** | GET/PUT | `/profile` | Customer |
| **Addresses** | GET/POST/DELETE | `/addresses` | Customer |
| **Wishlist** | GET/POST/DELETE | `/wishlist` | Customer |
| **Reviews** | GET/POST | `/reviews` | Customer |
| **Support** | POST | `/tickets` | Customer |
| **Notifications** | GET | `/notifications` | Customer |

---

## Third-Party Integrations

### Cloudinary (Image Upload)
- Product images are uploaded server-side to Cloudinary.
- The returned secure URL is stored in the `Product` entity.
- Sign up: [cloudinary.com](https://cloudinary.com)

### Razorpay (Payments)
- Test mode credentials work for local development.
- Server creates a Razorpay Order → client completes checkout → server verifies HMAC signature.
- Sign up: [razorpay.com](https://razorpay.com)

### Gmail SMTP (OTP Email)
- OTP-based password reset emails are sent via Gmail.
- Requires a Gmail **App Password** (not your normal password).
- Enable at: [myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords)

---

## Notes

- The `local` Spring profile is required to load `application-local.properties`.
- Hibernate is configured to **auto DDL** — tables are created automatically on first run.
- JWT tokens are stateless; logout is enforced via a `RevokedToken` denylist in the DB.
- CORS is configurable via `APP_CORS_ALLOWED_ORIGINS` in your local properties.
