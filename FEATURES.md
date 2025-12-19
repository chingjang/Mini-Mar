# Mini Mart Management System - Features Documentation

## System Overview
The Mini Mart Management System is a full-featured web application built with Spring Boot, Thymeleaf, Bootstrap, and MySQL. It provides a complete solution for managing a small retail store's daily operations.

## Core Features

### 1. Product Management
**Location**: http://localhost:8080/products

#### Features:
- **View All Products**: Display all products in a sortable table
- **Add New Product**: Form to create new products with validation
- **Edit Product**: Update existing product information
- **Delete Product**: Remove products from inventory (with confirmation)
- **Stock Status Indicators**: Visual badges showing stock levels
  - 🟢 **In Stock**: Quantity > 10
  - 🟡 **Low Stock**: Quantity 1-10
  - 🔴 **Out of Stock**: Quantity = 0

#### Product Fields:
- **ID**: Auto-generated unique identifier
- **Name**: Product name (required)
- **Category**: Pre-defined categories (required)
  - Beverages, Snacks, Groceries, Dairy, Bakery, Personal Care, Household, Others
- **Price**: Product price in dollars (required, decimal)
- **Quantity**: Available stock (required, integer)

### 2. Sales Module
**Location**: http://localhost:8080/sales

#### Features:
- **Browse Products**: View all available products for sale
- **Add to Cart**: Add items with specified quantities
- **View Cart**: Real-time cart display with item details
- **Remove from Cart**: Delete items from cart before checkout
- **Checkout**: Complete the transaction
- **Automatic Stock Update**: Stock decreases automatically after checkout
- **Stock Validation**: Prevents over-selling beyond available quantity

#### Sales Process:
1. Browse available products (only shows items with stock > 0)
2. Select quantity and add to cart
3. Review cart items and total amount
4. Click checkout to complete sale
5. System creates sale record and updates inventory

#### Cart Features:
- **Item Details**: Product name, quantity, unit price, subtotal
- **Total Calculation**: Automatic sum of all cart items
- **Remove Items**: Delete unwanted items before checkout
- **Empty Cart Alert**: Warning if attempting to checkout with empty cart
- **Stock Validation**: Error message if insufficient stock

### 3. Reports & Analytics
**Location**: http://localhost:8080/reports

#### Daily Sales Report:
- **Date Selection**: Choose specific date for reports (default: today)
- **Sales Summary**: Total sales amount for selected date
- **Transaction Count**: Number of sales transactions
- **Transaction Details**: Complete list of all sales including:
  - Sale ID
  - Date and time of sale
  - Items sold (with quantities)
  - Total amount

#### Inventory Status Report:
- **Current Stock Levels**: Real-time inventory for all products
- **Product Information**: ID, name, category, price, quantity
- **Status Indicators**: Visual badges for stock status
- **Low Stock Alerts**: Easy identification of products needing reorder

### 4. Home Dashboard
**Location**: http://localhost:8080/

#### Features:
- **Welcome Screen**: Professional introduction to the system
- **Quick Navigation**: Cards linking to main features
  - Product Management
  - Sales Processing
  - Reports & Analytics
- **Feature Overview**: List of system capabilities
- **Modern Design**: Gradient hero section with Bootstrap styling

## Technical Features

### Database Schema

#### Products Table:
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(255) NOT NULL
);
```

#### Sales Table:
```sql
CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_date DATETIME NOT NULL,
    total_amount DOUBLE NOT NULL
);
```

#### Sale Items Table:
```sql
CREATE TABLE sale_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### Backend Architecture

#### Controllers:
- **HomeController**: Handles home page
- **ProductController**: CRUD operations for products
- **SaleController**: Shopping cart and checkout
- **ReportController**: Sales and inventory reports

#### Services:
- **ProductService**: Business logic for product management
- **SaleService**: Business logic for sales processing

#### Repositories:
- **ProductRepository**: JPA repository for products
- **SaleRepository**: JPA repository for sales
- **SaleItemRepository**: JPA repository for sale items

### Frontend Features

#### Bootstrap Components Used:
- **Navigation Bar**: Responsive navbar with brand and links
- **Cards**: For displaying features and content sections
- **Tables**: Responsive tables for data display
- **Forms**: Validated input forms
- **Buttons**: Action buttons with icons
- **Badges**: Status indicators
- **Alerts**: Success/error messages

#### Responsive Design:
- Mobile-friendly layout
- Adapts to different screen sizes
- Touch-friendly buttons and inputs

## Security Features

### Data Validation:
- **Required Fields**: All essential fields must be filled
- **Type Validation**: Numeric fields only accept numbers
- **Positive Values**: Prevents negative quantities and prices
- **Stock Validation**: Prevents over-selling

### Transaction Safety:
- **Automatic Stock Update**: Ensures inventory accuracy
- **Database Transactions**: ACID compliance via Spring @Transactional
- **Error Handling**: Graceful error messages

## User Experience Features

### Visual Feedback:
- **Color-coded Status**: Green (in stock), yellow (low), red (out)
- **Success Messages**: Confirmation after actions
- **Error Messages**: Clear error descriptions
- **Loading States**: Bootstrap spinners for async operations

### Usability:
- **Intuitive Navigation**: Clear menu structure
- **Consistent Design**: Same look across all pages
- **Icon Usage**: Bootstrap icons for visual clarity
- **Confirmation Dialogs**: Confirm before deleting

## Performance Features

### Optimization:
- **JPA/Hibernate**: Efficient database queries
- **Connection Pooling**: HikariCP for database connections
- **Lazy Loading**: Efficient data fetching
- **Caching**: Hibernate second-level cache ready

### Scalability:
- **Stateless Design**: Easy horizontal scaling
- **RESTful Architecture**: Clean separation of concerns
- **Database Indexes**: Auto-generated on foreign keys

## Maintenance Features

### Logging:
- **SQL Logging**: View generated SQL queries
- **Application Logs**: Spring Boot logging
- **Error Tracking**: Exception logging

### Configuration:
- **External Configuration**: application.properties
- **Profile Support**: Different configs for dev/prod
- **Environment Variables**: Support for sensitive data

## Future Enhancement Possibilities

### Potential Features:
- User authentication and authorization
- Multiple user roles (admin, cashier)
- Customer management
- Supplier management
- Purchase orders
- Barcode scanning
- Receipt printing
- Email notifications
- Advanced analytics and charts
- Export reports to PDF/Excel
- Product images
- Discount and promotion management
- Return/refund handling
- Multi-location support

## Browser Compatibility

### Supported Browsers:
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
- Mobile browsers (iOS Safari, Chrome Android)

## System Requirements

### Server:
- Java 17+
- 512MB RAM minimum (1GB recommended)
- 100MB disk space

### Client:
- Modern web browser
- JavaScript enabled
- Internet connection (for Bootstrap CDN)

## Best Practices Implemented

### Code Quality:
- ✅ Separation of concerns (MVC pattern)
- ✅ Dependency injection
- ✅ Unit testing
- ✅ Lombok for boilerplate reduction
- ✅ Clear naming conventions
- ✅ Comments where needed

### Database:
- ✅ Proper indexing
- ✅ Foreign key constraints
- ✅ Normalized schema
- ✅ Transaction management

### Frontend:
- ✅ Responsive design
- ✅ Accessibility features
- ✅ Semantic HTML
- ✅ Bootstrap best practices
