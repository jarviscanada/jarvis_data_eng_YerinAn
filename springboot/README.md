Table of contents
* [Introduction](#Introduction)
* [Quick Start](#Quick Start)
* [Architecture](##Architecture)
* [REST API Usage](##REST API Usage)
* [Test](#Test)
* [Deployment](#Deployment)
* [Improvements](#Improvements)

# Introduction
This application is an online stock trading simulator using a REST API that returns JSON documents to users. It retrieves the real stock market information using IEX Cloud API(IEX Cloud has delayed stock information), saves and persists data into a database. And, this application allows users to manage client profiles and accounts, monitor portfolio performance, and trade securities. 
The application is a Microservice backend that is implemented using Java and Springboot with a PSQL database. The application follows MVC and 3-tier architecture.

# Quick Start
- Prequiresites: Docker, CentOS 7
- Docker scripts with description
	- build images
        ```shell script
        # psql image - make sure you are in the psql directory
        docker build -t traading-psql .
        # app image - make sure you are in the root directory
        docker build -t trading-app .
        ```
  - create a docker network
    ```shell script
    # create a network to communicate between both containers
    docker network create --driver bridge trading-net
    ```
  - start containers
    ```shell script
    # Start PSQL container
    docker run --name trading-psql-dev \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_DB=jrvstrading \
    -e POSTGRES_USER=postgres \
    --network trading-net \
    -d -p 5432:5432 trading-psql
    
    # Set IEX Token (from your IEX Cloud account)
    IEX_PUB_TOKEN=your_token
    
    # Start application container
    docker run --name trading-app-dev \
    -e "PSQL_URL=jdbc:postgresql://trading-psql-dev:5432/jrvstrading" \
    -e "PSQL_USER=postgres" \
    -e "PSQL_PASSWORD=password" \
    -e "IEX_PUB_TOKEN=${IEX_PUB_TOKEN}" \
    --network trading-net \
    -p 8080:8080 trading-app
    ```
-  SWAGGER UI: http://localhost:8080/swagger-ui.html
![image description](assets/swagger.png)
![image description](assets/swagger_01.png)
# Implemenation
## Architecture
![image description](assets/Diagram.png)

  - Controller layer 
    - The controller layer handles HTTP requests and calls the service layer with given input (handles HTTP requests using Tomcat) and controls exceptions thrown by the app at the HTTP level.
  - Service layer
    - The service layer handles business logic, validates data passed down from the controller layer, and calls corresponding functionality in the DAO layer.
  - DAO layer
    - The DAO layer handles data storage and performs the actual data manipulation. The DAOs make calls to access the PSQL instances through the DataSource class. It also accesses an external REST API to retrieve data from IEX.
  - SpringBoot:
    - SpringBoot manages class dependencies automatically through IoC. Apache Tomcat is the default web servlet in Springboot, provides a Java HTTP web server environment that automatically handles REST API calls from clients.
  - PSQL and IEX
    - PSQL provides the data storage platform to store the data, and IEX cloud's REST API provides the real stock market data source.

## REST API Usage
### Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?
### Quote Controller
- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
- briefly explain each endpoint
  e.g.
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system blah..blah..
### Trader Controller
- High-level description for trader controller (e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain each endpoint
### Order Controller
- High-level description for this controller.
- briefly explain each endpoint
### App controller
- briefly explain each endpoint
### Optional(Dashboard controller)
- High-level description for this controller.
- briefly explain each endpoint

# Test 
How did you test your application? Did you use any testing libraries? What's the code coverage?

# Deployment
- docker diagram including images, containers, network, and docker hub
e.g. https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- describe each image in details (e.g. how psql initialize tables)

# Improvements
If you have more time, what would you improve?
- at least 3 improvements