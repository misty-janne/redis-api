# Swagger API

## user-controller
### createUser
#### Request
`POST /api/users`

```
curl -X 'POST' \
  'http://127.0.0.1:8089/api/users' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "890",
  "name": "lili"
}'
```

#### Response
```
Status: 200 OK
Content-Type: application/json

{"id": "890", "name": "lili"}
```
### getUserById
#### Request
`GET /api/users/:id`

```
curl -X 'GET' \
  'http://127.0.0.1:8089/api/users/890' \
  -H 'accept: */*'
```

#### Response
```
Status: 200 OK
Content-Type: application/json

{"id": "890", "name": "lili"}
```
### getAllUsers
#### Request
`GET /api/users`

```
curl -X 'GET' \
  'http://127.0.0.1:8089/api/users' \
  -H 'accept: */*'
```
#### Response
```
Status: 200 OK
Content-Type: application/json

[ { "id": "123", "name": "jane" }, ..{ "id": "890", "name": "lili" } ]
```
### deleteUserById
#### Request
`DELETE /api/users/:id`

```
curl -X 'DELETE' \
  'http://127.0.0.1:8089/api/users/890' \
  -H 'accept: */*'
```

#### Response
```
Status: 200 OK
```

## coupon-controller
### generateCoupons
### issueCoupon

# Redis cli

## user-controller
### createUser
#### test
```
127.0.0.1:6379> hset User 234 "{\"id\":\"234\",\"name\":\"nana\"}"
```
#### result
```
(integer) 1
```
### getUserById
#### test
```
127.0.0.1:6379> hget User 234
```
#### result
```
"{\"id\":\"234\",\"name\":\"nana\"}"
```
### getAllUsers
#### test
```
127.0.0.1:6379> hvals User
```
#### result
```
1) "{\"id\":\"123\",\"name\":\"jane\"}"
2) "{\"id\":\"234\",\"name\":\"nana\"}"
```
### deleteUserById

## coupon-controller
### generateCoupons
### issueCoupon
