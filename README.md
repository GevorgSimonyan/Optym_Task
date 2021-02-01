# Project for Optym

This is the Gevorg Simonyan's practice task solution for Optym

## Getting Started

The project contains both backend and frontend sources.

### Prerequisites

You need to have Mongo DB installed on your PC.
The installation guide for Mongo DB https://www.mongodb.com/try/download/community?tck=docs_server

Also you will need node.js v12.20.1 and above
To check the version of node.js use follwing command in terminal:
node -v

Instructions about npm and node.js installation you can find here: https://www.npmjs.com/get-npm

Also need installed java 8 and above


## Deployment

You can set MongoDB configuration on file Optym_Task\Backend\demo\src\main\resources\mongo.properties or keep default values (host=127.0.0.1, port=27017, database=adminmax)

To run backend server:

Open Optym_Task\Backend\demo in the terminal and run command: mvnw spring-boot:run

To run frontend:

Open Optym_Task\Frontend\project in the terminal and run command: npm install than npm start

You can find and test endpoinst on SwaggerUI. for that after frontend run need to go http://localhost:8080/swagger-ui.html


## Authors

* **Gevorg Simonyan** 




