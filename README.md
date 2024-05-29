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
#### Request
`POST /api/coupons/generate`

```
curl -X 'POST' \
  'http://127.0.0.1:8089/api/coupons/generate?count=2' \
  -H 'accept: */*' \
  -d ''
```

#### Response
```
Status: 200 OK
Content-Type: application/json

["5c0d57cb-c640-431f-acab-d14f4923de27", "0f979777-a16a-44a4-8ac3-e3fd5a7611a0"]
```

### issueCoupon
#### Request
`GET /api/coupons/generate`

```
curl -X 'GET' \
  'http://127.0.0.1:8089/api/coupons/issue' \
  -H 'accept: */*'
```

#### Response
```
Status: 200 OK
Content-Type: text/plain;charset=UTF-8

c9b3b8b7-89ca-48c3-9ab1-bc735b681f38
```

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
#### test
```
127.0.0.1:6379> hdel User 123
```
#### result
```
(integer) 1
```
## coupon-controller
### generateCoupons
#### test
```
127.0.0.1:6379> rpush Coupon qwer-1234-test
```
#### result
```
(integer) 4
```
### issueCoupon
#### test
```
127.0.0.1:6379> lpop Coupon
```
#### result
```
"23f1ddf8-1d0a-40fc-880a-e9f2eb930ead"
```
### select all
#### test
```
127.0.0.1:6379> lrange Coupon 0 -1
```
#### result
```
1) "9a1c10ac-fabf-496e-bf04-e1bfb0380806"
2) "c9b3b8b7-89ca-48c3-9ab1-bc735b681f38"
3) "qwer-1234-test"
```
