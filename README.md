# Three-Tier Java Web Application

A three-tier web application developed as part of **CNT 4714 – Enterprise Computing** at the University of Central Florida.

The project demonstrates a Java web architecture using **Jakarta Servlets, JSP, Apache Tomcat, JDBC, and MySQL**, with authentication, role-based database access, dynamic query results, business logic, and stored-procedure reporting.

## Technologies

* Java
* Jakarta Servlets
* JSP
* Apache Tomcat
* MySQL
* JDBC
* HTML/CSS

## Features

### User Authentication

The application authenticates users against a MySQL database and directs them to different interfaces based on their assigned role.

The application supports three user types:

* **Root User** — database query and modification functionality
* **Client User** — restricted database query access
* **Accountant User** — predefined reporting functionality

### Role-Based Database Access

Each user type interacts with the database through a dedicated Java Servlet and database configuration.

The client interface restricts modification operations, while the root interface provides additional database functionality.

### Business Logic

Database modification operations involving shipment data can trigger additional business rules, demonstrating server-side application logic beyond basic CRUD/database access.

### Reporting

The accountant interface provides predefined reports backed by MySQL stored procedures, including:

* Total part weight
* Maximum supplier status
* Total shipment count
* Job workforce information
* Supplier name and status reporting

### Dynamic Results

SQL query and report results are processed in Java and rendered dynamically as HTML tables in the JSP interface.

## Architecture

The application follows a three-tier structure:

1. **Presentation Layer** — HTML and JSP pages
2. **Application Layer** — Java Servlets handling authentication, database operations, permissions, and business logic
3. **Data Layer** — MySQL database accessed through JDBC

## Project Context

This project was created for **CNT 4714 – Enterprise Computing** at the University of Central Florida in Fall 2024.

The goal was to build a distributed database-backed web application and gain experience working with Java web technologies, application servers, SQL databases, authentication, and multi-user access control.
