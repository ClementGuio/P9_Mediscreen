# P9_Mediscreen

Mediscreen is an application that allows doctors to register their patients, record notes and analyse notes to extract risk report concerning diabete. 

## Pre-requisites

* Java 11
* MySql 8
* Mongo 3
* Docker 20.10.22
* Docker-compose 1.25

## Tools used in software

* Spring framework 2.7.7
* Maven
* Thymeleaf 
* Jackson
* Spring Data Jpa
* ...
     
## Getting started

To simply lauch Mediscreen App, you have to build every services and skip tests. Go to service root folder and execute : **mvn clean package -Dmaven.test.skip**.  
After building all services, go to the root folder of the project and execute with admin or superuser rights : **docker-compose build --no-cache** and **docker-compose up**.  
Now, you have to retrieve ip of docker-ui microservice, execute : **docker ps**, copy container ID, execute **docker inspect <containerID>** and look for IPAddress.  
Then, you can access Mediscreen in your browser via **\<docker-uiIP\>:8080/mediscreen/patient**.  
To access API, retrieve all IPs and port in order to request the good location.  
   
If you want to launch tests, you will have to install Mysql and create database by using initdb.sql in patient-api folder (verify that the mysql port 3306 or modify it in applications.properties).  
Then you can build simply by executing **mvn clean package**. This will run the tests and build package in case of success.  

**WARNING** : If you execute Mediscreen App locally you will not be able to use UI, only API requests. Please prefer docking application.  
