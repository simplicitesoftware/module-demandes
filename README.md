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



`DemRental` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demRenStartDate`                                            | datetime                                 | yes      |           |          | -                                                                                |
| `demRenEndDate`                                              | datetime                                 | yes      |           |          | -                                                                                |
| `demRenEquipmentCondition`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `demReqType`                                                 | enum(7) using `REQUESTTYPE` list         |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(7) using `REQUESTSTATUS` list       |          | yes       |          | -                                                                                |
| `demReqReason`                                               | text(500)                                |          |           |          | -                                                                                |
| `demReqSupplyType`                                           | enum(7) using `SUPPLYTYPE-REQ` list      | yes      |           |          | -                                                                                |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `REQUESTSTATUS`
    - `DRAFT` Draft
    - `PENDING` Pending
    - `VALIDATED` Validated
    - `REJECTEDMAN` Rejected manager
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `REJECTEDADM` rejected by administrator
    - `CLOSED` Closed
    - `CANCELLED` Cancelled
* `SUPPLYTYPE-REQ`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies

### Custom actions

* `REQUESTSTATUS-PENDING-VALIDATED-RENTAL`: 
* `REQUESTSTATUS-PENDING-REJECTED-RENTAL`: 

`DemReqSup` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demReqsupReqId` link to **`DemRequest`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqReference`_                      | _char(10)_                               |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqTitle`_                          | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqType`_                           | _enum(7) using `REQUESTTYPE` list_       |          |           |          | -                                                                                |
| _Ref. `demReqsupReqId.demReqSupplyType`_                     | _enum(7) using `SUPPLYTYPE-REQ` list_    |          |           |          | -                                                                                |
| `demReqsupSupId` link to **`DemSupply`**                     | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupStockQuantity`_                  | _int(10)_                                |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupReference`_                      | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupSprId`_                          | _id_                                     |          |           |          | -                                                                                |
| _Ref. `demSupSprId.demSprName`_                              | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupName`_                           | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupType`_                           | _enum(7) using `SUPPLYTYPE` list_        |          |           |          | -                                                                                |
| _Ref. `demReqsupSupId.demSupAllocation`_                     | _enum(7) using `REQSUPALLOCATION` list_  |          |           |          | -                                                                                |
| `demReqsupOrderChoice`                                       | multi(100) using `DEMREQSUPORDERCHOICE` list |          |           |          | -                                                                                |
| `demReqsupQuantityOrdered`                                   | int(100)                                 |          |           |          | -                                                                                |
| `demReqsupQuantityRequired`                                  | int(10)                                  | yes      |           |          | -                                                                                |

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

### Custom actions

* `DEM_GETSTOCK`: 
* `DEM_ORDER`: 

`DemRequest` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demReqReference`                                            | char(10)                                 | yes*     |           |          | -                                                                                |
| `demReqTitle`                                                | char(100)                                |          |           |          | -                                                                                |
| `demReqType`                                                 | enum(7) using `REQUESTTYPE` list         |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(7) using `REQUESTSTATUS` list       |          | yes       |          | -                                                                                |
| `demReqReason`                                               | text(500)                                |          |           |          | -                                                                                |
| `demReqFutherInformation`                                    | notepad(500)                             |          | yes       |          | -                                                                                |
| `demReqRequestDate`                                          | date                                     |          |           |          | -                                                                                |
| `demReqSupplyType`                                           | enum(7) using `SUPPLYTYPE-REQ` list      | yes      |           |          | -                                                                                |
| `demReqRejectedReasonAdministrator`                          | multi(500) using `DEMREQREJECTEDREASONADMINISTRATOR` list |          |           |          | -                                                                                |
| `demReqRejectedReasonManager`                                | multi(500) using `DEMREQUESTREJECTEDREASONMANAGER` list |          |           |          | -                                                                                |

### Lists

* `REQUESTTYPE`
    - `PURCHASE` Purchase
    - `RENTAL` Rental
* `REQUESTSTATUS`
    - `DRAFT` Draft
    - `PENDING` Pending
    - `VALIDATED` Validated
    - `REJECTEDMAN` Rejected manager
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `REJECTEDADM` rejected by administrator
    - `CLOSED` Closed
    - `CANCELLED` Cancelled
* `SUPPLYTYPE-REQ`
    - `FURNITURE` Furniture
    - `IT` IT
    - `OFFICE` Office supplies
* `DEMREQREJECTEDREASONADMINISTRATOR`
    - `NOBUDGET` No budget
    - `UNAVAILABLE` Unavailable
* `DEMREQUESTREJECTEDREASONMANAGER`
    - `NOBUDGET` No budget
    - `NOTNECESSARY` Not necessary

### Custom actions

* `REQUESTSTATUS-PENDING-VALIDATED`: Transition
* `REQUESTSTATUS-PENDING-REJECTEDMAN`: Transition
* `REQUESTSTATUS-VALIDATED-REJECTEDADM`: 

`DemRequestHistoric` business object definition
-----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `row_ref_id` link to **`DemRequest`**                        | id                                       | yes*     |           |          | -                                                                                |
| `row_idx`                                                    | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `created_by_hist`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `created_dt_hist`                                            | datetime                                 | yes*     |           |          | -                                                                                |
| `demReqRejectedReasonManager`                                | multi(500) using `DEMREQUESTREJECTEDREASONMANAGER` list |          |           |          | -                                                                                |
| `demReqRejectedReasonAdministrator`                          | multi(500) using `DEMREQREJECTEDREASONADMINISTRATOR` list |          |           |          | -                                                                                |
| `demReqStatus`                                               | enum(7) using `REQUESTSTATUS` list       |          | yes       |          | -                                                                                |

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
    - `REJECTEDMAN` Rejected manager
    - `REQUESTFUTHERINFO` Request for futher information
    - `PROCESSING` Processing
    - `REJECTEDADM` rejected by administrator
    - `CLOSED` Closed
    - `CANCELLED` Cancelled

`DemSprReq` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demSprreqSprId` link to **`DemSupplier`**                   | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demSprreqSprId.demSprName`_                           | _char(100)_                              |          |           |          | -                                                                                |
| `demSprreqReqId` link to **`DemRequest`**                    | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `demSprreqReqId.demReqReference`_                      | _char(10)_                               |          |           |          | -                                                                                |

`DemSupplier` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demSprName`                                                 | char(100)                                | yes*     | yes       |          | -                                                                                |
| `demSprContactLastName`                                      | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactName`                                          | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactEmail`                                         | char(100)                                |          | yes       |          | -                                                                                |
| `demSprContactPhone`                                         | char(100)                                |          | yes       |          | -                                                                                |

`DemSupply` business object definition
--------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `demSupReference`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `demSupName`                                                 | char(100)                                |          | yes       |          | -                                                                                |
| `demSupType`                                                 | enum(7) using `SUPPLYTYPE` list          | yes      | yes       |          | -                                                                                |
| `demSupDescription`                                          | text(500)                                |          | yes       |          | -                                                                                |
| `demSupDeliveryTimeDays`                                     | int(10)                                  |          | yes       |          | -                                                                                |
| `demSupStockQuantity`                                        | int(10)                                  |          | yes       |          | -                                                                                |
| `demSupPrice`                                                | float(10, 0)                             |          | yes       |          | -                                                                                |
| `demSupAllocation`                                           | enum(7) using `REQSUPALLOCATION` list    |          | yes       |          | -                                                                                |
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

`DemRentalAgenda` external object definition
--------------------------------------------




`DemRentalSupply` external object definition
--------------------------------------------




