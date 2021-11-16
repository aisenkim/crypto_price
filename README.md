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


### Starting Backend Application (METHOD 1)
Method 1: Pull image from docker hub

1. Visit : https://hub.docker.com/r/aisenkim15/crypto_backend/tags 
2. Pull the docker image to local machine
3. Run the following commands

    ```bash
      # View the images 
      docker ls
   
      # Start the backend application
      docker run -p 8080:8080 aisenkim15/crypto_backend
    ```

### Starting Backend Application (METHOD 2)
Method 2: build jarfile and use docker-compose

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