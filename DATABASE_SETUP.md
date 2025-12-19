# Database Setup Guide

## Prerequisites
- MySQL Server 8.0 or higher installed
- MySQL running on localhost:3306

## Setup Instructions

### Option 1: Automatic Database Creation (Recommended)
The application is configured to automatically create the database if it doesn't exist.

1. Make sure MySQL is running
2. Update the database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root
   ```
3. Run the application - the database and tables will be created automatically

### Option 2: Manual Database Setup

1. Login to MySQL:
   ```bash
   mysql -u root -p
   ```

2. Create the database:
   ```sql
   CREATE DATABASE minimart_db;
   USE minimart_db;
   ```

3. Grant permissions (if needed):
   ```sql
   GRANT ALL PRIVILEGES ON minimart_db.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

4. The tables will be created automatically when you run the application

## Adding Sample Data

After the application starts, you can add sample data through the web interface or by uncommenting and running the SQL in `src/main/resources/schema.sql`.

## Database Configuration

Default configuration in `application.properties`:
- **Database Name**: minimart_db
- **Username**: root
- **Password**: root
- **Port**: 3306
- **Host**: localhost

Update these values according to your MySQL setup.
