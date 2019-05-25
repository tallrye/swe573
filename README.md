# SWE573
A repo for the term project for the SWE573 course on Bogazici University Software Engineering MS Program

## Installation

To install and run Wlearn on your machine, you need to install [Java](https://www.java.com) Jdk v8+, [Gradle](https://gradle.org/) & [MySQL database](https://dev.mysql.com/doc/workbench/en/wb-installing-windows.html)

For front-end application, you need to install [NodeJS](https://nodejs.org/en/)

After installation of Java, Gradle and NodeJS, you can clone the project into your workspace.

## Building the Back-End

After cloning the project go to workspace and run the gradle command:

```sh
$ .\gradlew build
```

Builds the backend for production to the `build/libs/`folder as `com.tallrye.wlearn-0.0.1-SNAPSHOT.jar`.<br>

## Building the Front-End

After cloning the project go to front-end folder and run the following commands;

```sh
$ npm install
```

Dowloads necessary packages and dependencies.

```sh
$ npm run build
```

Builds the app for production to the `/front-end/build/` folder.<br>

## Database Configuration

In order to make database configurations for local environment, you should open `src\main\resources\applicaion.properties` file and change the parameters below. After making changes you should run the application according to your environment. Running with profiles is explaned under running the backend sections

```sh
spring.datasource.url= jdbc:mysql://{DATABASE_URL}/{DATABASE_NAME}
spring.datasource.username= {USERNAME_NAME}
spring.datasource.password= {PASSWORD}
```

`{DATABASE_URL}` is the url of the database. it is mostly `localhost:3306` for the local environments

`{DATABASE_NAME}` is the name of the database that you created on database server for the appication. If you did not, first create a database.

`{USERNAME_NAME}` is the username of your database.

`{PASSWORD}` is the password of your database. 

**Note**
By default, your MySQL username would be "root" without a password.

## Run Back-End

After building the project running the project requires the steps below;

### Development environment:

```sh
$ java -jar {Jar_File_Location}/com.tallrye.wlearn-0.0.1-SNAPSHOT.jar
```

`{Jar_File_Location}` is the directory where you keep your jar file. Please change it according to your settings


### Production environment:

```sh
$ nohup java -jar {Jar_File_Location}/com.tallrye.wlearn-0.0.1-SNAPSHOT.jar &
```

`{Jar_File_Location}` is the directory where you keep your jar file. Please change it according to your settings

Application writes the log to nohup.out file which is placed where the jar is located.

## Run Front-End 

After building the project running the project requires the steps below;

Go to "front-end" folder under the workspace

Since project requires com.tallrye.wlearn, it is recomended to run backend first.

Before run the frontend application, please run the command below; 

```sh
$ npm install -g serve
```
Now the front-end should be up-and-running

### Development Environment:

```sh
$ serve -s {Build_Directory_Location}/build
```

`{Build_Directory_Location}` is the directory where you keep your build directory. Please change it according to your settings

It runs the frontend project as development mode

### Development Environment:

```sh
$ nohup serve -s {Build_Directory_Location}/build &
```

`{Build_Directory_Location}` is the directory where you keep your build directory. Please change it according to your settings

It runs the frontend project as production mode

Application writes the log to nohup.out file which is placed where the jar is located.

