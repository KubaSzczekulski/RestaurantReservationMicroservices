## Architecture

![architekturaMikroserwisy](https://github.com/KubaSzczekulski/RestaurantReservationMicroservices/assets/72909122/598f8741-747d-48d6-824f-ad08a585dece)


## Description
The project is a web application designed for restaurant table reservations, leveraging a microservices architecture to optimize service management and scalability. It is developed using Java with Spring Framework, ensuring robust backend functionality. The application supports user interactions through a gateway, which serves as the only point of contact for the microservices, enhancing security by regulating API traffic with Zuul.

Each microservice—User, Restaurant, Reservation—maintains its own SQL database, with Hibernate employed for efficient object-relational mapping. This setup facilitates smooth operations such as the creation, modification, and deletion of reservations through dedicated API endpoints.

For notification purposes, the application uses RabbitMQ to queue messages, which informs users about their reservation statuses via email, improving the reliability and efficiency of communications. Testing and validation of the application functionalities are conducted using Postman, ensuring the system meets all specified requirements.

## Team Members
Olaf Teperek
Jakub Szczękulski

## Tech Stack

### Backend:

Java
Spring
Hibernate
SQL Database

### Messaging and Notifications:

RabbitMQ

### Security:

Keycloak
OAuth 2.0

### Testing:

Postman
