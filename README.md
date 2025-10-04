# Volando UY Backend

A Java backend application built with Servlets and JSP (JavaServer Pages) for managing flight information.

## 🚀 Features

- Java 17 backend application
- Servlet-based request handling
- JSP pages with JSTL for dynamic content
- Flight information management
- Clean and modern UI
- Maven build system

## 📋 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6 or higher

## 🛠️ Building the Application

To build the application, run:

```bash
mvn clean package
```

This will compile the Java code and create a WAR file in the `target` directory.

## 🏃 Running the Application

### Using Jetty (Development Server)

The easiest way to run the application for development is using the embedded Jetty server:

```bash
mvn jetty:run
```

The application will be available at: `http://localhost:8080`

### Using a Servlet Container

Deploy the generated WAR file (`target/volando-uy-backend.war`) to any Jakarta EE compatible servlet container such as:
- Apache Tomcat 10.1+
- Eclipse Jetty 11+
- GlassFish 7+

## 📁 Project Structure

```
volando-uy-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── uy/
│       │       └── volando/
│       │           ├── model/          # Data models
│       │           │   └── Flight.java
│       │           └── servlets/       # Servlet controllers
│       │               ├── HomeServlet.java
│       │               └── FlightServlet.java
│       ├── webapp/
│       │   ├── WEB-INF/
│       │   │   ├── jsp/               # JSP pages
│       │   │   │   ├── home.jsp
│       │   │   │   └── flights.jsp
│       │   │   └── web.xml            # Web application configuration
│       │   └── index.html             # Entry point
│       └── resources/                  # Application resources
└── pom.xml                             # Maven configuration
```

## 🌐 Available Endpoints

- `/` - Redirects to home page
- `/home` - Home page with welcome message
- `/flights` - Flight information page

## 🔧 Technology Stack

- **Java 17** - Programming language
- **Jakarta Servlet 6.0** - Web framework
- **Jakarta JSP 3.1** - View technology
- **JSTL 3.0** - JSP Standard Tag Library
- **Maven** - Build and dependency management
- **Jetty 11** - Development server

## 📝 License

This project is open source and available for educational purposes.