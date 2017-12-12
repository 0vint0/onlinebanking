# onlinebanking
Simple RestFull Online-Banking application

Technology stack:
- java-8
- memory DB HSQLDB
- Junit4
- spring-boot
- JPA + hibernate
- spring validation+hibernate validator
* The spring security was not integrated yet.

Functional Description:
As a client you can do:
- signUp (automatically default card account will be generated and assigned)
- see your details (client details/card account details)
- see all trasactions
- create transactions : supply/withdraw money 
** There are still space to extend this application:
      - deactivate/delete client
      - assign new cards to client
      - make transactions on specific card account
      - integrate security
      - etc. 

Validations:
- If there is alread client with same email new client will not be created
- PhoneNumber,email, identityCard must have respective format
- If client tries to withdraw more money than he has in balance, then exception will be thrown
- Client can do operation only exists in the systema and is Activated
- etc.

Data Model:
As data model there are used 3 entities : clients, card_accounts, cars_transactions

Application layer:
The application is depicted in few layers:
- Repository : for interaction with DB
- Service : 
        a) On interface part is defined validation
        b) On implemention is defined business.
   each model has it's own layer     
- Api: it's an additional layer which defines complex business logic of one process,
  so that why this can interact with multiple service layers.
  For example the client signup can be complecated process:
       - create client
       - send notification to another third system about new customer
       - send to client confirmation form
       - activate client
 -Controller: to interact with rest requests       
      


