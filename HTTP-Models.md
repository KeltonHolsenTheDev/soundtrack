# Planning
## HTTP
@url = http://localhost:8080/api/
* @Get  
* {{url}} HTTP/1.1  


## API Controllers
* Event
    * get all events past and present
    * get event by name
    * get event by date
    * post(create) an event
    * put(update) an event 
    * delete an event by id
* Item
    * get all items
    * get item by name
    * get item by type
    * get item by location
    * post(create) an item
    * put(update) an item
    * delete an item by id
* User
    * get all users
    * get all volunteers
    * get all admin
    * get user by email
    * post(create) a user
    * put(update) a user  
    * delete a user by id  
* Repair Ticket
    * get all repair tickets
    * get all by user
    * get all if "open case"  
    * post(create) a repair ticket
    * put(update) a repair ticket
    * delete a repair ticket by id
    
**Consider archiving events and repair tickets  
rather than deleting.**   
**Offer a dropdown filter menu for UI/UX**