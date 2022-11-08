# Group 23

## How to build the Tomcat environment:

### 1. Download latest version JAVA 

how to check do you have JAVA environment(if you don't have JAVA please download) 

```
% javac
% javac -version
```

### 2. Download Tomcat web server(the latest version)

https://tomcat.apache.org/download-10.cgi

### 3. Configure Tomcat server environment variables

- **CATALINA_HOME=**X:\xxx\xxx\apache-tomcat-10.0.12

- **PATH=**%CATALINA_HOME%\bin

- **JAVA_HOME**=X:\xxx\xxx\jdk-19

- **PATH**=%JAVA_HOME%\bin

### 4. Run the Tomcat web server

Start Tomcat:

```
% startup.bat
```

Close Tomcat:

```
% shutdown.bat
```

## How to run the program in IntelliJ IDEA

### Step 1: Basic Construction

1. Create a new project
2. Create a new module
3. Right-click the module---->Add Framework Support------>Select Web Application*
     (A webapp directory structure conforming to the Servlet specification.)*

#### Webapp directory structure:

*webapproot*
      *|------WEB-INF*
      			*|------classes (store bytecode)*
      			*|------lib (third-party jar package)*
      			*|------web.xml (Register Servlet)*
      *|------Front-end content*
*Notice:*
*1.webapproot is the project name*
*2.classes store the class file after the Java program is compiled*

### Step 2: Let the IDEA tool associate the Tomcat server.

1. Add Configuration

2. The plus sign in the upper left corner, click Tomcat Server --> local

3. Deployment (click this to deploy the webapp), continue to click the plus sign to deploy. Modify the Application context to: /project_name

### Step 3: Start the Tomcat server and enter the address in the browser

  Open the browser and enter on the browser address bar: 

  http://localhost:8080/project_name
