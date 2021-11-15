# Crypto Price

## Project Description

---

1. Prices of Bitcoin and Ethereum from two (any) different exchanges/sources.
    - Differentiate buy and sell price clearly
2. Recommendations on which exchange one should buy and/or sell.
    - Recommend where to buy and where to sell. Each of the recommendation can be a different exchange

## Technology Used

---

Backend → **Spring Boot**

Frontend → **React, Material UI**

DevOps → **Docker**

## Getting Started

---

### Prerequisite

- Docker
- npm

### Starting Backend Application

1. Navigate to the root of the project and go into the "crypto_price_backend" directory.
2. Run the following commands

    ```bash
    # build .jar file
    ./mvnw clean instal
    
    # running it in detached mode
    docker-compose up -d
    ```


1. The backend server should be running in the background. After, run the following command to stop it

    ```bash
    docker-compose down
    ```


### Starting Frontend Application

1. Navigate to the root of the project and go into the "client" directory
2. Run the following command

    ```bash
    # Install modules
    npm install
    
    # Start application
    npm start
    ```