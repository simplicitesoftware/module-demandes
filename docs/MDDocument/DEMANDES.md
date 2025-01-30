<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`Demandes` module definition
============================

The module is an office supply request management application.

It has various users which allow user to place their own request, manager to manage the request and 
an administrator to process the request and order the supply.
2 types of request, one for buying new supply and one for rental supply.

Some business rules, including right rules, applies on all business objects some of which depends on the user profile.


User profiles
-------------

The **Administrator** has full access to the suppliers, supply , requests and rentals

The **User** has access to his requests.
He can create a request and follow it. 
He can cancelled a request when the status is pending. 
He has a read only access to the supplies.
He does not have access to suppliers and cannot manage request.

The **Manager** has access to his requests and user requests.
He can manage request by validated it or rejected it or request for futher information.
He has a read only access to the supplies.
He does not have access to suppliers and cannot manage supplies.

Business workflows
------------------

### Request and Rental states

Request can be in the following statuses:
- **Draft** as the initial state
- **Pending** when request is submitted
- **Validated** when validated by the manager profile
- **Rejected ** by the manager profile
- **Request for futher information** when manager asked for futher information
- **Processed** by the administrator when manager validate the request
- **Closed** when the request is fufilled
- **Cancelled** when the users cancelled the request

### Rental states


### Place new request flow

The flow for request process is a 7 steps flow:
- **Users or managers :**
- Create a new request (Enter title and description of the request and choose the supplies type) and submit  
- **Managers :**
- Create a new Request/supply (Select supplies for the request and enter a quantity) and submit  
- Validate or reject or request futher information the request
- **Administrators :**
- Select a validated request and validate or reject the request  
- If validated, select process the request  
- Has full rights in case of mistakes
- **Users :**  
- Closed the request  


Business rules
--------------

### Supplier related business rules

Supplier can only be created and updated by the administrator profile.
Supplier can have several supplies

### Supply related business rules

Supply can only be created and updated by the administrator profile via the supplier profile.
Supply can only belong to one supplier. 
Supply can be in several request and order.

### Request and rental related business rules

A request or rental can only be created and updated by the user and manager profile.
A request or rental can be modify until it's validated.
It can have several supplies for a request.


Others
------

### Rental calendar

Rental calendar show different rentals for a month, a week or a day. 
A rental appears in :
- Red when it has been rejected by the manager,
- Orange when it's still pending
- Green when it has been validated
- Gray when it's closed or cancelled
User or manager check the rental by clicking on it

Business data
-------------

The data model consist of 6 business objects:


[OBJECTDOC:DemRental] 
DemRental : placed by users or managers

[OBJECTDOC:DemReqSup] 
DemReqSup : link between  supplies and requests

[OBJECTDOC:DemRequest] 
DemRequest : placed by the users or managers

[OBJECTDOC:DemRequestHistoric] 
DemRequestHistoric : historics of the request

[OBJECTDOC:DemSupplier] 
DemSupplier : who supply furniture

[OBJECTDOC:DemSupply] 
DemSupply : by supplier and that can be ordered