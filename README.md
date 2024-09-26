
# Running the Microservices

## Prerequisites

• Java Development Kit (JDK) installed (version 17 or higher)

• Maven build tool

• MySQL Workbench (version 8.0 or higher)

## Configuring database 
1. Create a database by the name "customerdb" for Customer Management Service in your MySQL Workbench
2. Create a database by the name "accountdb" for Account Management Service in your MySQL Workbench

## Steps to run
1. Download the repository. 
2. Build the microservices: `mvn clean install`
3.  Run the Config server: `java -jar ConfigServer/target/ConfigServer.jar`
4.  Run the Eureka server: `java -jar ServiceRegistry/target/ServiceRegistry.jar`
5.  Run the Api Gateway: `java -jar ApiGateway/target/ApiGateway.jar`
6.  Run the Customer Management Service: `java -jar CustomerManagementService/target/CustomerManagementService.jar`
7.  Run the Account Management Service: `java -jar AccountManagementService/target/AccountManagementService.jar`
  
## Accessing the Services
1. Config Server: http://localhost:8084
2. Discovery Server(Eureka): http://localhost:8761
3. Api Gateway: http://localhost:8083
4. Customer Management Service: http://localhost:8081
5. Account Management Service: http://localhost:8082
  
## Sample Endpoints
### Customer Management Service
• GET Single Customer: GET http://localhost:8083/customers/{customerId}

• GET All Customers: GET http://localhost:8083/customers

• POST Create a new Customer: POST http://localhost:8083/customers

• PUT Update Customer Details: PUT http://localhost:8083/customers/{customerId}

• DELETE Delete Customer and associated Accounts: DELETE http://localhost:8083/customers/{customerId}

### Account Management Service
• GET Single Account with Customer Details: GET http://localhost:8083/accounts/{accountId}

• GET All Accounts: GET http://localhost:8083/accounts

• POST Create a new Account: POST http://localhost:8083/accounts

• POST Deposit money to an Account: POST http://localhost:8083/accounts/add-money/{accountId}

• POST Withdraw money from an Account: POST http://localhost:8083/accounts/withdraw-money/{accountId}

• DELETE Delete Account: DELETE http://localhost:8083/accounts/{accountId}

