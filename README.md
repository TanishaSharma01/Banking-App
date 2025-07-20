# Banking System Microservices

A distributed banking application built using Spring Boot microservices architecture, featuring customer management and account operations with service discovery, configuration management, and API gateway.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [Service Access URLs](#service-access-urls)
- [API Documentation](#api-documentation)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Overview

This banking system demonstrates a microservices architecture with the following key features:

- **Customer Management**: Create, read, update, and delete customer information
- **Account Management**: Handle account operations including deposits, withdrawals, and account lifecycle
- **Service Discovery**: Eureka-based service registry for dynamic service location
- **Configuration Management**: Centralized configuration using Spring Cloud Config
- **API Gateway**: Single entry point for all client requests with routing and load balancing

## Architecture

```
┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │───▶│   API Gateway   │
└─────────────────┘    └─────────────────┘
                                │
                       ┌────────┴────────┐
                       ▼                 ▼
              ┌─────────────────┐ ┌─────────────────┐
              │ Customer Mgmt   │ │ Account Mgmt    │
              │   Service       │ │   Service       │
              └─────────────────┘ └─────────────────┘
                       │                 │
                       ▼                 ▼
              ┌─────────────────┐ ┌─────────────────┐
              │   customerdb    │ │   accountdb     │
              │    (MySQL)      │ │    (MySQL)      │
              └─────────────────┘ └─────────────────┘

              ┌─────────────────┐ ┌─────────────────┐
              │ Config Server   │ │ Eureka Server   │
              │                 │ │ (Discovery)     │
              └─────────────────┘ └─────────────────┘
```

## Technology Stack

- **Framework**: Spring Boot, Spring Cloud
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config
- **Database**: MySQL 8.0+
- **Build Tool**: Maven
- **Java Version**: JDK 17+

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK)** - Version 17 or higher
- **Apache Maven** - Latest stable version
- **MySQL Workbench** - Version 8.0 or higher
- **Git** - For cloning the repository

## Project Structure

```
banking-microservices/
├── ConfigServer/
├── ServiceRegistry/
├── ApiGateway/
├── CustomerManagementService/
├── AccountManagementService/
├── Eureka-Server-Instances.png
└── README.md
```

## Setup and Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd banking-microservices
```

### 2. Database Configuration

Create the required databases in MySQL Workbench:

```sql
-- For Customer Management Service
CREATE DATABASE customerdb;

-- For Account Management Service  
CREATE DATABASE accountdb;
```

### 3. Build the Project

```bash
mvn clean install
```

## Running the Application

**Important**: Services must be started in the following order to ensure proper dependency resolution:

### Step 1: Start Config Server
```bash
java -jar ConfigServer/target/ConfigServer.jar
```
Wait for the service to start completely before proceeding.

### Step 2: Start Eureka Server
```bash
java -jar ServiceRegistry/target/ServiceRegistry.jar
```
Wait for the service to start completely before proceeding.

### Step 3: Start API Gateway
```bash
java -jar ApiGateway/target/ApiGateway.jar
```

### Step 4: Start Business Services

**Customer Management Service:**
```bash
java -jar CustomerManagementService/target/CustomerManagementService.jar
```

**Account Management Service:**
```bash
java -jar AccountManagementService/target/AccountManagementService.jar
```

## Service Access URLs

| Service | URL | Description |
|---------|-----|-------------|
| Config Server | http://localhost:8084 | Configuration management |
| Eureka Server | http://localhost:8761 | Service discovery dashboard |
| API Gateway | http://localhost:8083 | Main application entry point |
| Customer Service | http://localhost:8081 | Direct access (development only) |
| Account Service | http://localhost:8082 | Direct access (development only) |

## API Documentation

### Customer Management Service

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/customers/{customerId}` | Retrieve a specific customer |
| GET | `/customers` | Retrieve all customers |
| POST | `/customers` | Create a new customer |
| PUT | `/customers/{customerId}` | Update customer details |
| DELETE | `/customers/{customerId}` | Delete customer and associated accounts |

### Account Management Service

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/accounts/{accountId}` | Retrieve account with customer details |
| GET | `/accounts` | Retrieve all accounts |
| POST | `/accounts` | Create a new account |
| POST | `/accounts/add-money/{accountId}` | Deposit money to account |
| POST | `/accounts/withdraw-money/{accountId}` | Withdraw money from account |
| DELETE | `/accounts/{accountId}` | Delete account |

**Note**: All API calls should be made through the API Gateway (http://localhost:8083) for proper routing and load balancing.

## Troubleshooting

### Common Issues

**Issue**: Services fail to register with Eureka
- **Solution**: Ensure Eureka server is running before starting other services
- **Check**: Verify service registration at http://localhost:8761

**Issue**: Database connection errors
- **Solution**: Verify MySQL is running and databases exist
- **Check**: Connection strings in application configuration

**Issue**: Port conflicts
- **Solution**: Ensure ports 8081-8084 and 8761 are available
- **Check**: Use `netstat` or `lsof` to verify port usage

**Issue**: Gateway routing errors
- **Solution**: Ensure all services are registered with Eureka before making requests
- **Check**: Service status in Eureka dashboard

### Logs Location

Service logs are typically available in:
- Console output (when running with `java -jar`)
- Application logs directory (if configured)

---

**Note**: This application is designed for development and learning purposes. For production deployment, additional considerations such as security, monitoring, logging, and container orchestration should be implemented.

