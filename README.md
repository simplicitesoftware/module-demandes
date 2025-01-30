<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://platform.simplicite.io/logos/standard/logo250.png)
* * *

`Demandes` module definition
============================



`DemPurchase` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demReqType`                                                 | enum(100) using `REQUESTTYPE` list       |          |           |          | -                                                                                |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental

`DemUser` business object definition
------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `usr_image_id`                                               | image                                    |          | yes       | yes      | Picture                                                                          |
| `usr_login`                                                  | regexp(100)                              | yes*     | yes       | yes      | Login                                                                            |
| `usr_first_name`                                             | char(50)                                 |          | yes       | yes      | First name                                                                       |
| `usr_last_name`                                              | char(50)                                 |          | yes       | yes      | Last name                                                                        |
| `usr_email`                                                  | email(100)                               |          | yes       | yes      | Email address                                                                    |
| `usr_cell_num`                                               | phone(20)                                |          | yes       | yes      | Mobile/cellular phone number                                                     |
| `demDemuserRole`                                             | enum(10) using `DEM_DEMUSER_ROLE` list   |          | yes       |          | -                                                                                |

### Lists

* `DEM_DEMUSER_ROLE`
    - `A` Administrator
    - `M` Manager
    - `U` User

`DemRequestHistoric` business object definition
-----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `row_ref_id` link to **`DemRequest`**                        | id                                       | yes*     |           |          | Record row ID                                                                    |
| `row_idx`                                                    | int(11)                                  | yes*     | yes       |          | History record index                                                             |
| `created_by_hist`                                            | char(100)                                | yes*     |           |          | Created by                                                                       |
| `created_dt_hist`                                            | datetime                                 | yes*     |           |          | Created date                                                                     |
| `demReqRejectedReasonManager`                                | multi(500) using `DEMREQUESTREJECTEDREASONMANAGER` list |          |           |          | -                                                                                |
| `demReqRejectedReasonAdministrator`                          | multi(500) using `DEMREQREJECTEDREASONADMINISTRATOR` list |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(20) using `REQUESTSTATUS` list      |          | yes       |          | -                                                                                |

### Lists

* `DEMREQUESTREJECTEDREASONMANAGER`
    - `NOBUDGET` No budget
    - `NOTNECESSARY` Not necessary
* `DEMREQREJECTEDREASONADMINISTRATOR`
    - `NOBUDGET` No budget
    - `UNAVAILABLE` Unavailable
* `REQUESTSTATUS`
    - `DRAFT` Draft
    - `PENDING` Pending
    - `VALIDATED` Validated
    - `REJECTED` Rejected
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `CLOSED` Closed
    - `CANCELLED` Cancelled

`DemRequest` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demReqReference`                                            | char(12)                                 | yes*     |           |          | -                                                                                |
| `demReqTitle`                                                | char(100)                                |          | yes       |          | -                                                                                |
| `demReqType`                                                 | enum(100) using `REQUESTTYPE` list       |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(20) using `REQUESTSTATUS` list      |          | yes       |          | -                                                                                |
| `demReqRejectedReason`                                       | multi(500) using `DEMREQREJECTEDREASONADMINISTRATOR` list |          |           |          | -                                                                                |
| `demReqFutherInformation`                                    | notepad(500)                             |          | yes       |          | -                                                                                |
| `demReqRequestDate`                                          | date                                     |          |           |          | -                                                                                |
| `demReqSupplyType`                                           | enum(10) using `SUPPLYTYPE-REQ` list     | yes      | yes       |          | -                                                                                |
| `demReqDemUserId` link to **`DemUser`**                      | id                                       |          | yes       |          | -                                                                                |
| `demReqDesc`                                                 | html(1000)                               |          | yes       |          | -                                                                                |
| _Ref. `demReqDemUserId.usr_login`_                           | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `demReqDemUserId.usr_first_name`_                      | _char(50)_                               |          |           | yes      | _First name_                                                                     |
| _Ref. `demReqDemUserId.usr_last_name`_                       | _char(50)_                               |          |           | yes      | _Last name_                                                                      |
| _Ref. `demReqDemUserId.usr_email`_                           | _email(100)_                             |          |           | yes      | _Email address_                                                                  |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `REQUESTSTATUS`
    - `DRAFT` Draft
    - `PENDING` Pending
    - `VALIDATED` Validated
    - `REJECTED` Rejected
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `CLOSED` Closed
    - `CANCELLED` Cancelled
* `DEMREQREJECTEDREASONADMINISTRATOR`
    - `NOBUDGET` No budget
    - `UNAVAILABLE` Unavailable
* `SUPPLYTYPE-REQ`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies

`DemRental` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demRenStartDate`                                            | datetime                                 | yes      | yes       |          | -                                                                                |
| `demRenEndDate`                                              | datetime                                 | yes      | yes       |          | -                                                                                |
| `demRenEquipmentCondition`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `demReqType`                                                 | enum(100) using `REQUESTTYPE` list       |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(20) using `REQUESTSTATUS` list      |          | yes       |          | -                                                                                |
| `demReqRejectedReason`                                       | multi(500) using `DEMREQREJECTEDREASONADMINISTRATOR` list |          |           |          | -                                                                                |
| `demReqSupplyType`                                           | enum(10) using `SUPPLYTYPE-REQ` list     | yes      | yes       |          | -                                                                                |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `REQUESTSTATUS`
    - `DRAFT` Draft
    - `PENDING` Pending
    - `VALIDATED` Validated
    - `REJECTED` Rejected
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `CLOSED` Closed
    - `CANCELLED` Cancelled
* `DEMREQREJECTEDREASONADMINISTRATOR`
    - `NOBUDGET` No budget
    - `UNAVAILABLE` Unavailable
* `SUPPLYTYPE-REQ`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies

`DemSupplier` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demSprName`                                                 | char(100)                                | yes*     | yes       |          | -                                                                                |
| `demSprContactLastName`                                      | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactName`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactEmail`                                         | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactPhone`                                         | char(100)                                |          | yes       |          | -                                                                                |

`DemSupply` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demSupReference`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `demSupName`                                                 | char(100)                                |          | yes       |          | -                                                                                |
| `demSupType`                                                 | enum(100) using `SUPPLYTYPE` list        | yes      | yes       |          | -                                                                                |
| `demSupDescription`                                          | text(500)                                |          | yes       |          | -                                                                                |
| `demSupDeliveryTimeDays`                                     | int(10)                                  |          | yes       |          | -                                                                                |
| `demSupStockQuantity`                                        | int(10)                                  |          | yes       |          | -                                                                                |
| `demSupPrice`                                                | float(10, 0)                             |          | yes       |          | -                                                                                |
| `demSupAllocation`                                           | enum(10) using `REQSUPALLOCATION` list   |          | yes       |          | -                                                                                |
| `demSupSprId` link to **`DemSupplier`**                      | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demSupSprId.demSprName`_                              | _char(100)_                              |          |           |          | -                                                                                |

### Lists

* `SUPPLYTYPE`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies
* `REQSUPALLOCATION`
    - `PURCHASE` Purchase
    - `RENTAL` Rental

`DemReqSup` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `demReqsupReqId` link to **`DemRequest`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqReference`_                      | _char(12)_                               |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqTitle`_                          | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqType`_                           | _enum(100) using `REQUESTTYPE` list_     |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqSupplyType`_                     | _enum(10) using `SUPPLYTYPE-REQ` list_   |          |           |          | -                                                                                |
| `demReqsupSupId` link to **`DemSupply`**                     | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupReference`_                      | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupStockQuantity`_                  | _int(10)_                                |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupSprId`_                          | _id_                                     |          |           |          | -                                                                                |
| _Ref. `demSupSprId.demSprName`_                              | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupName`_                           | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupType`_                           | _enum(100) using `SUPPLYTYPE` list_      |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupAllocation`_                     | _enum(10) using `REQSUPALLOCATION` list_ |          |           |          | -                                                                                |
| `demReqsupOrderChoice`                                       | multi(100) using `DEMREQSUPORDERCHOICE` list |          | yes       |          | -                                                                                |
| `demReqsupQuantityOrdered`                                   | int(100)                                 |          | yes       |          | -                                                                                |
| `demReqsupQuantityRequired`                                  | int(10)                                  | yes      | yes       |          | -                                                                                |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `SUPPLYTYPE-REQ`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies
* `SUPPLYTYPE`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies
* `REQSUPALLOCATION`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `DEMREQSUPORDERCHOICE`
    - `DECREASESTOCK` Decrease stock
    - `ORDER` Order

`DemRentalAgenda` external object definition
--------------------------------------------




