# How to use this service

## How do I sign up?

  ```json
  curl -i -H "Content-Type: application/json" -X POST -d '{
      "username":"admin",
      "password":"password",
      "address":
      {
          "street":"Main Road",
          "houseNumber":"1",
          "postalCode":76137,
          "city": "Karlsruhe",
          "country":"Germany"
      }
  }' http://localhost:8080/signup
  ```

You will receive a JWT as output.

## How do I log in?

  ```json
  curl -i -H "Content-Type: application/json" -X POST -d '
  {
       "username": "admin",
       "password": "password"
  }
  ' localhost:8080/login
  ```
  
You will receive a JWT as output.

## How do I validate the JWT?

    curl -i -H "Authorization: Bearer xxx.yyy.zzz" -X GET localhost:8080/validate

## How do I get user information from the JWT?

    curl -i -H "Authorization: Bearer  xxx.yyy.zzz" -X GET localhost:8080/resolve

## How do I update a user?

 ```json
  curl -i -H "Authorization: Bearer xxx.yyy.zzz" -H "Content-Type: application/json" -X PUT -d '{
      "username":"admin",
      "password":"password",
      "address":
      {
          "street":"Main Road",
          "houseNumber":"1",
          "postalCode":76137,
          "city":"Karlsruhe",
          "country":"Germany"
      }
  }' localhost:8080/user
```
  
## How do I delete a user?

    curl -i -H "Authorization: Bearer xxx.yyy.zzz" -X DELETE localhost:8080/user
    
## How do I use the parking service?

To use the parking service, send a JSON of the `BrokerMessage` class structure to the defined MQTT broker.

    {
        'parkingId': 1,
        'timestamp': '2019-06-26 14:37:27'
        'uid': '[118, 241, 88, 26, 42]',
        'isFree': False
    }
    
This will update the status of the parking space available in the parking table.

In addition, an entry is created in the Booking Table.

To release the parking, send a JSON to the MQTT broker again.

    {
        'parkingId': 1,
        'timestamp': '2019-06-26 14:37:27'
        'isFree': True
    }
    
The parking fees are calculated automatically on the basis of the parking duration and the defined price structure.
