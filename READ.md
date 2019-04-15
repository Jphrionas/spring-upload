# Simple Project With Spring Boot and Data JPA

[![Build Status](https://travis-ci.org/Jphrionas/spring-upload.svg?branch=master)](https://travis-ci.org/Jphrionas/spring-upload)

## Create Schema
```sh
 mysql -u [USER] -p
 CREATE SCHEMA faturadb DEFAULT COLLATE = utf8_general_ci CHARSET = utf8;
```

## Change appication.yml
> The MySQL Connector is support from MySQL 8 Version InnoDB
  - Change database name
  - Chage username and password 

```sh
    spring:
        datasource:
            url: jdbc:mysql://localhost:3306/[YOU_DB]?createDatabaseIfNotExist=true&serverTimeZone=UTC&sslMode=DISABLED
            passoword: [YOU PASSWORD]
            username: [YOU_USERNAME]
```


