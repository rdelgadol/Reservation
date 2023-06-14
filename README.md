#Reservation API REST
It´s a booking service for the public company Cantur, that manages touristic resorts in the province of Cantabria (Spain).
	
The name is reservation, to avoid confusion whit the booking entity.

##Design
This API REST uses an embedded H2 database available in the URL http://localhost:9002/h2-console

As requested, it manages bookings and blocks. Each concept has its entity, repository and service, but there is only a controller.

The port has been changed to 9002 in the properties file.

##Endpoints
Both bookings and blocks can be managed through the following endpoints, examples are provided.

Get, to obtain entities that overlaps with the given dates:
* http://localhost:9002/booking?startDate=2023-09-01&endDate=2023-12-01
* http://localhost:9002/block?startDate=2023-09-01&endDate=2023-12-01

Post, to record new entities. Accepts a JSON representation of the entity:
* http://localhost:9002/booking
* http://localhost:9002/block

Put, to update any value of the entity, except the start date because it is the primary key. Accepts a JSON representation of the entity:
* http://localhost:9002/booking
* http://localhost:9002/block

Delete, to remove entities:
* http://localhost:9002/booking/2023-09-10
* http://localhost:9002/block/2023-09-10

The booking entity has an additional endpoint, just to change if it is available or not:
* http://localhost:9002/booking/available?startDate=2023-09-10&available=false

Only available bookings are considered in validations related to overlapping entities.
