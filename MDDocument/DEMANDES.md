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

Business data
-------------

The data model consist of 10 business objects:

[OBJECTDOC:DemOrder] 

DemOrder : placed by administrators

[OBJECTDOC:DemRental] 
DemRental : placed by users or managers

[OBJECTDOC:DemReqOrd] 
DemReqOrd : link between requests and orders

[OBJECTDOC:DemReqSup] 
DemReqSup : link between  supplies and requests

[OBJECTDOC:DemRequest] 
DemRequest : placed by the users or managers

[OBJECTDOC:DemRequestHistoric] 
DemRequestHistoric : historics of the request

[OBJECTDOC:DemSprReq] 
DemSprReq : link between suppliers and their requests 

[OBJECTDOC:DemSupOrd] 
DemSupOrd : link between supplies and orders

[OBJECTDOC:DemSupplier] 
DemSupplier : who supply furniture

[OBJECTDOC:DemSupply] 
DemSupply : by supplier and that can be ordered


User profiles
-------------

The **Administrator** has full access to the suppliers, supply and order management:
he can access the validated requests placed by users and managers and manage the requests and orders. 
He is the only profile to be allowed to create and place orders and manage suppliers and supplies.

The **User** has access to his requests.
He can create a request and follow it. 
He can cancelled a request when the status it's pending. 
He has a read only access to the supplies.
He does not have access to orders and suppliers and cannot manage request.

The **Manager** has access to his requests and user requests.
He can manage request by validated it or rejected it or request for futher information.
He has a read only access to the supplies.
He does not have access to orders and suppliers and cannot manage supplies.

Business workflows
------------------

### Request states

Request can be in the following statuses:

- **Pending** as the initial status
- **Validated** when validated by the manager profile
- **Rejected by manager** when rejected by the manager profile
- **Request for futher information** when manager asked for futher information
- **Processing** when validated by the admnistrator
- **Rejected by administrator** when rejected by the administrator
- **Processed** when administrator has create an order for the request
- **Closed** when the request is fufilled
- **Cancelled** when the users cancelled the request

### Order states

Orders can be in the following statuses:

- **Pending order** as the initial status
- **Order placed** when an order is placed by the administrator profile
- **Validated** when validated by the suppliers
- **Shipped** when the suppliers shipped the furniture
- **Delivered** when the furniture is delivered to the user

### Rental states

Rental can be in the following statuses:

- **Pending** as the initial status
- **Validated** when validated by the manager
- **Rejected by manager** when rejected by manager
- **Closed** when date is due 
- **Cancelled** when users cancelled the rental request

### Place new request flow

The flow for request process is a 7 steps flow:
- **Users or managers :**
- Create a new request (Enter title and reason of the request and choice the supplies type) and submit
- Create a new Request/supply (Select supplies for the request and enter a quantity) and submit
- **Managers :**
- Validate or reject or request futher information the request
- **Administrators :**
- Select a validated request and validate or reject the request
- If validated, select process the request
- Create an order and processed the request 
- **Users :**
- Closed the request

### Place new order flow

The flow for order entry is a 4 steps flow by the **administrator**:

- Select one validated request from the request list
- Select one request/supply from the request/supply list
- Select create order funtion, the quantity ordered is calculated depending of the stock quantity of the supply and the quantity required by the user.
He can modify the quantity ordered if necessary and submit it
- Review the order summary, add a supply or a request to the order or edit the quantity of a supply and place it
- A order is create and place, an email will be sent to the supplier(s) 

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
It can have several supplies for a request, but only one order.

### Order business rules

An order can only be created and updated by the administrator profile.
An order can be for one or several requests or supplies.

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