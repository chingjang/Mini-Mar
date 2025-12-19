# Quick Start Guide

## Prerequisites
- Java 17 or higher installed
- MySQL 8.0 or higher installed and running
- Maven 3.6 or higher installed (or use the included Maven wrapper)

## Quick Setup (5 minutes)

### Step 1: Configure MySQL
Make sure MySQL is running on your system. The default credentials in the application are:
- **Username**: `root`
- **Password**: `root`
- **Port**: `3306`

If your MySQL credentials are different, update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Step 2: Build and Run
```bash
# Clone the repository (if not already done)
git clone https://github.com/chingjang/Mini-Mar.git
cd Mini-Mar

# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

Alternatively, run the JAR directly:
```bash
java -jar target/mini-mart-system-1.0.0.jar
```

### Step 3: Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

## First Steps

### Adding Your First Product
1. Click on **Products** in the navigation menu
2. Click **Add New Product** button
3. Fill in the product details:
   - Name: e.g., "Coca Cola 500ml"
   - Category: Select from dropdown (e.g., "Beverages")
   - Price: e.g., 1.50
   - Quantity: e.g., 100
4. Click **Save Product**

### Processing Your First Sale
1. Click on **Sales** in the navigation menu
2. Find a product from the available products list
3. Enter the quantity you want to sell
4. Click **Add to Cart**
5. Review the items in your shopping cart
6. Click **Checkout** to complete the sale
7. Stock will be automatically updated!

### Viewing Reports
1. Click on **Reports** in the navigation menu
2. Select a date to view sales for that day
3. View transaction history and current inventory status

## Default Categories
The system comes with the following product categories:
- Beverages
- Snacks
- Groceries
- Dairy
- Bakery
- Personal Care
- Household
- Others

## Troubleshooting

### Database Connection Issues
If you see database connection errors:
1. Verify MySQL is running: `sudo systemctl status mysql` (Linux) or check Services (Windows)
2. Check credentials in `application.properties`
3. Ensure MySQL is listening on port 3306

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

### Build Errors
If you encounter build errors:
```bash
# Clean the project and rebuild
mvn clean install -U
```

## Sample Data
To quickly populate your database with sample products, you can use the SQL in `src/main/resources/schema.sql.example`. Uncomment the INSERT statements and run them in MySQL Workbench or command line.

## Next Steps
- Explore the product management features
- Process multiple sales transactions
- View daily reports to track your business
- Customize categories as needed for your shop

## Support
For issues or questions, please create an issue in the GitHub repository.

Enjoy managing your Mini Mart! 🛒
