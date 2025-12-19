# Mini Mart Management System

A comprehensive web-based management system for mini marts and small retail shops. This application helps shop owners efficiently manage products, process sales, track inventory, and generate daily reports.

## Features

### 🛍️ Product Management
- Add, edit, and delete products
- Track product details: ID, name, price, quantity, category
- Real-time inventory status (In Stock, Low Stock, Out of Stock)
- Category-based product organization

### 💰 Sales Module
- Interactive shopping cart interface
- Add multiple items to cart
- Real-time total calculation
- Automatic stock reduction after checkout
- Transaction history tracking

### 📊 Reports & Analytics
- Daily sales reports with date selection
- Transaction history with detailed item breakdown
- Current inventory status overview
- Low stock alerts
- Total daily sales calculation

### 🎨 User Interface
- Modern, responsive design using Bootstrap 5
- User-friendly navigation
- Mobile-compatible layout
- Clean and professional interface

## Technology Stack

- **Backend**: Spring Boot 3.1.5
- **Frontend**: Thymeleaf + Bootstrap 5
- **Database**: MySQL
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java JDK 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/chingjang/Mini-Mar.git
cd Mini-Mar
```

### 2. Database Setup
Make sure MySQL is running and update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/minimart_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
```

The database and tables will be created automatically when you run the application.

For detailed database setup instructions, see [DATABASE_SETUP.md](DATABASE_SETUP.md)

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/mini-mart-system-1.0.0.jar
```

### 5. Access the Application
Open your web browser and navigate to:
```
http://localhost:8080
```

## Usage Guide

### Managing Products
1. Navigate to **Products** from the menu
2. Click **Add New Product** to create a product
3. Fill in product details (name, category, price, quantity)
4. Use Edit/Delete buttons to manage existing products

### Processing Sales
1. Navigate to **Sales** from the menu
2. Select products from the available products list
3. Choose quantity and click **Add to Cart**
4. Review items in the shopping cart
5. Click **Checkout** to complete the sale
6. Stock is automatically updated after checkout

### Viewing Reports
1. Navigate to **Reports** from the menu
2. Select a date to view daily sales
3. Review transaction details and inventory status
4. Monitor low stock items

## Project Structure

```
Mini-Mar/
├── src/
│   ├── main/
│   │   ├── java/com/minimart/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── model/            # Entity Classes
│   │   │   ├── repository/       # JPA Repositories
│   │   │   ├── service/          # Business Logic
│   │   │   └── MiniMartApplication.java
│   │   └── resources/
│   │       ├── templates/        # Thymeleaf Templates
│   │       │   ├── products/
│   │       │   ├── sales/
│   │       │   └── reports/
│   │       ├── static/css/       # CSS Styles
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/                     # Test Classes
├── pom.xml
└── README.md
```

## Database Schema

### Products Table
- `id`: Primary key
- `name`: Product name
- `price`: Product price
- `quantity`: Available stock
- `category`: Product category

### Sales Table
- `id`: Primary key
- `sale_date`: Transaction timestamp
- `total_amount`: Total sale amount

### Sale Items Table
- `id`: Primary key
- `sale_id`: Foreign key to Sales
- `product_id`: Foreign key to Products
- `quantity`: Quantity sold
- `price`: Price at time of sale
- `subtotal`: Line item total

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or suggestions, please create an issue in the GitHub repository.

## Author

Created for managing mini mart operations efficiently.
