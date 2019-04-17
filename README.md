# Simple Project With Spring Boot and Data JPA

[![Build Status](https://travis-ci.org/Jphrionas/spring-upload.svg?branch=master)](https://travis-ci.org/Jphrionas/spring-upload)

## Create Schema
```sh
 mysql -u [USER] -p
 CREATE SCHEMA faturadb DEFAULT COLLATE = utf8_general_ci CHARSET = utf8;
```

## Change Database in  application.yml
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

## Change Upload Folder

-- Before Upload in Spring, create Folder in OS
#### For unix
```sh
    root~: sudo su
    root~: sudo mkdir [PATH]/[FOLDER_NAME]
    root~: chmod -R 7777 [PATH]/[FOLDER_NAME]
```

##### For Windows
```sh
    mkdir [PATH]/[FOLDER_NAME]
```

### Change Spring Boot Upload
- In application.properties change you [PATH_FOLDER]
```sh
    app.upload.baseUrl=[PATH]
    app.upload.folderName=[FOLDER_NAME]
    app.upload.absolutePath=${app.upload.baseUrl}/${app.upload.folderName}/
```
