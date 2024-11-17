# Address Api Spec

## Create Address

Endpoint : POST /api/contacts/{idContacts}/addresses

Request Header :

- X-API-TOKEN: Token

Request Body : 
```json
{
  "Street" : "Jalan Jalan",
  "City" : "Kota",
  "Province" : "provinsi",
  "country" : "negara",
  "postalCode" : "123123"
}
```

Response Body(success) :

```json
{
  "data": {
    "id" : "random-string",
    "Street" : "Jalan Jalan",
    "City" : "Kota",
    "Province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Contact is Not Found"
}
```

## Update Address

Endpoint : PUT /api/contacts/{idContacts}/addresses/{idAddress}

Request Header :

- X-API-TOKEN: Token

Request Body :
```json
{
  "Street" : "Jalan Jalan",
  "City" : "Kota",
  "Province" : "provinsi",
  "country" : "negara",
  "postalCode" : "123123"
}
```

Response Body(success) :

```json
{
  "data": {
    "id" : "random-string",
    "Street" : "Jalan Jalan",
    "City" : "Kota",
    "Province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Address is Not Found"
}
```


## Get Address

Endpoint : GET /api/contacts/{idContacts}/address/{idAddresses}

Request Header :
- X-API-TOKEN: Token


Response Body(success) :

```json
{
  "data": {
    "id" : "random-string",
    "Street" : "Jalan Jalan",
    "City" : "Kota",
    "Province" : "provinsi",
    "country" : "negara",
    "postalCode" : "123123"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Address is Not Found"
}
```

## Remove Address

Endpoint :DELETE /api/contacts/{idContact}/addresses/{idAddress}

Request Header :

- X-API-TOKEN: Token

Response Body(success) :
```json
{
  "data" : "OK"
}
```
Response Body (Failed) :
```json
{
  "errors" : "Address is not found"
}
```

## List Address

Endpoint : GET /api/contacts/{idContact}/address

Request Header :

- X-API-TOKEN: Token

Response Body(success) :
```json
{
  "data": [
    {
      "id" : "random-string",
      "Street" : "Jalan Jalan",
      "City" : "Kota",
      "Province" : "provinsi",
      "country" : "negara",
      "postalCode" : "123123"
    }
  ]
}
```
Response Body (Failed) :
```json
{
  "errors" : "Contact is not found"
}
```