# Contact API Spec

## Create Contact
Endpoint : POST /api/contacts

Request Header :

- X-API-TOKEN: Token

Request Body :
```json
{
  "id" : "random-string",
  "firstName" : "contoh",
  "lastName" : "aja",
  "email" : "contohaja@test.com",
  "phone" : "0899889998"
}
```

Response Body (Success) :
```json
{
  "data" : {
    "firstName" : "contoh",
    "lastName" : "aja",
    "email" : "contohaja@test.com",
    "phone" : "0899889998"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "email format invalid"
}
```
## Update Contact
Endpoint : PUT /api/contacts/{idContact}

Request Body :
```json
{
  "id" : "random-string",
  "firstName" : "contoh",
  "lastName" : "aja",
  "email" : "contohaja@test.com",
  "phone" : "0899889998"
}
```

Request Header :

- X-API-TOKEN: Token

Response Body (Success) :
```json
{
  "data" : {
    "firstName" : "contoh",
    "lastName" : "aja",
    "email" : "contohaja@test.com",
    "phone" : "0899889998"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "email format invalid"
}
```


## Get Contact

Endpoint : GET /api/contacts/{idContacts}

Request Header :

- X-API-TOKEN: Token

Request Body :
```json
{
  "id" : "random-string",
  "firstName" : "contoh",
  "lastName" : "aja",
  "email" : "contohaja@test.com",
  "phone" : "0899889998"
}
```

Response Body (Success) :
```json
{
  "data" : {
    "firstName" : "contoh",
    "lastName" : "aja",
    "email" : "contohaja@test.com",
    "phone" : "0899889998"
  }
}
```

Response Body (Failed,404) :
```json
{
  "errors" : "contact is not found"
}
```


## Search Contact
Endpoint : GET /api/contacts

Query Param : 

-Name : String,Contact Name , Using Like Query
- Phone : String,Contact Phone, Using Like Query
- Email : String, Contact Email , Using Like Query
- Page : Integer , Start from 0 default 0 
- Size : Integer default 10

Request Body :

Request Header :

- X-API-TOKEN: Token

Response Body (Success) :
```json
{
  "data" :[
    {
      "id" : "Random-string",
      "firstName" : "contoh",
      "lastName" : "aja",
      "email" : "contohaja@test.com",
      "phone" : "0899889998"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 10,
    "size" : 10
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Unauthorized"
}
```

## Remove Contact
Endpoint :DELETE /api/contacts/{idContacts}


Request Header :

- X-API-TOKEN: Token


Response Body (Success) :
```json
{
  "data" : "OK"
}
```


Response Body (Failed) :
```json
{
  "errors" : "contact is not found"
}
```