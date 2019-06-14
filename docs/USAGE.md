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
  }' localhost:8080/
```
  
## How do I delete a user?

    curl -i -H "Authorization: Bearer xxx.yyy.zzz" -X DELETE localhost:8080/
