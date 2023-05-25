Playgrounds
Create a playground API (REST (Spring Boot) or library (jar) – which one is your decision) that
implements business logic for playground management
These are things that we expect library will do:
- define clear and usable domain classes, beans, components (interfaces) that should be used
  access required functionality/data
- API creates and manages play sites that consists of DOUBLE SWINGS, CAROUSEL, SLIDE AND A BALL
  PIT. API allows to create combinations of play sites (for example two swings, one slide etc.)
- API should not use DB or persistent data store, In-memory storage is enough.
- API should be able to add kids to play sites (we know kid's name, age, ticket number)
- API should not allow to add more kids to them than specified in configuration of play site
  (done once on initialization)
- it should be possible to enqueue kid or receive negative result if kid does not accept waiting
  in queue. API register queues on play sites when tries to add kid to play site, that is full, and
  kid accepts waiting in queue).
- it should also be possible to remove kid from play site / queue
- API should provide play site utilization. Play site utilization is calculated differently for each
  play site (most of play sites utilization is percent of capacity taken (3/10 places taken for 30%
  utilization), double swings 100% if full capacity, 0 if 1 or 0 kids, some not yet known play sites
  can have different calculation implementations, this possible extension requirement should
  reflect in design). Utilization is measured in %
- API should be able to provide a total visitor count during a current day on all play sites


Realisation :
The proposed solution is based on joins Visitor -> Ticket -> Component.
Thus, all business logic is transferred to manipulations with Ticket.
Accordingly, the ComponentController and SiteController have common functionality.
The main requests are made in the TicketController.

Initial disposition:
We have 4 sites on the playground: West, South, East, North;
components are distributed across sites.


1. Buying a ticket by a visitor - PUT request http://localhost:8080/api/ticket/component/1,
   where 1 is the component number, the request body contains information about the visitor in the json:
   {
   "name":"Peter Ivanov",
   "age":12
   }
   if there is no such child in the database, then it is added.
   The request takes into account the maximum number for the requested component and does create a ticket if the number of children is exceeded maximum

2. Change the ticket status from QUEUE to ACTIVE (the kid comes from the queue to the carousel)
   request - http://localhost:8080/api/ticket/activate/6
   (it is assumed that the ticket is activated on a special device -
   that is, the request by ticket id is valid)

    from QUEUE to CANCELLED (the kud decided to leave the queue)
request - http://localhost:8080/api/ticket/cancel/6
    6 - is ticket id in both cases

3. Request for the presence of kids on the site at a specific time:
   GET http://localhost:8080/api/ticket/site?siteName=South&timestamp=2023-05-25 12:20:28

4. Request for the presence of kids on the site at the current moment
   GET http://localhost:8080/api/ticket/site/South
   where South in both cases is the site name
5. Request for site utilization:
      GET http://localhost:8080/api/site/util/1 , where 1 is the id of the requested site

6. Request for component utilization:
   GET http://localhost:8080/api/component/util/1 , where 1 is the id of the requested component

All requests were verified using Postman for Windows version 10.14.5.

I implemented Swagger to the API for visual effect and check of the requests functionality.
To do this, I had to downgrade spring-boot to 2.2.6.RELEASE. With this version, swagger 2.8.0 works stably.
With the latest version, swagger , unfortunatelly has issues. I tried to resolve them but did not have enough time.
