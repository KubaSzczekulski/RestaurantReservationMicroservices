## Architecture

![architekturaMikroserwisy](https://github.com/KubaSzczekulski/RestaurantReservationMicroservices/assets/72909122/598f8741-747d-48d6-824f-ad08a585dece)

The web application for restaurant table reservations has been built using a microservices architecture with Java and Spring. The authentication system relies on Keycloak, and service-to-service traffic is managed by Zuul. Each microservice - User, Restaurant, Reservation - has its own SQL database. Communication between microservices occurs solely through the Gateway, providing additional security.

Hibernate has been used for object-relational mapping, facilitating database operations. Dedicated API endpoints have been designed for each microservice, enabling operations such as creating, editing, and deleting reservations. The email notification system, confirming reservations, utilizes RabbitMQ for message queuing, enhancing the efficiency of the notification process. The project allows for functional testing using the Postman tool.
