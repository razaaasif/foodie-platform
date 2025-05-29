
# foodie-app

Foodie App is a microservices-based food ordering platform designed for restaurants and users. It allows users to register, browse restaurant menus, place orders, and track their orders in real-time—from preparation to delivery. It’s a complete backend service .

💡 What This Project Does
This project provides a REST API-driven backend infrastructure that enables the following:

✅ User registration and management

🏢 Restaurant onboarding

🍽️ Menu item management per restaurant

🛒 Order placement and processing

💳 Payment gateway integration (Razorpay, BharatPe, PhonePe, etc.)

🛵 Rider assignment and delivery tracking

🔄 Real-time order status updates across services

# User Registration API

This service allows registering new users into the system via a RESTful API.

---

## 🚀 Endpoint

### Register a New User

**URL:** `POST /user-service/users`

**Request Body:**

```json
curl --location 'http://localhost:8086/user-service/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Vinod Tiwari",
    "email": "vtiwari@americana-food.com"
}'

```
###
### Register a New Restaurent

**URL:** `POST /restaurant-service/restaurants`

**Request Body:**

```json

curl --location 'http://localhost:8082/restaurant-service/restaurants' \
--header 'Content-Type: application/json' \
--data '{
  "name": "Americana Foods",
    "address":"UAE",
    "phone": "+96-80-66604545"
  }'
```
###
### Add menu items for Restaurent

**URL:** `POST /menu-service/menu-items`

**Request Body:**


```json
curl --location 'http://localhost:8081/menu-service/menu-items' \
--header 'Content-Type: application/json' \
--data '[
  {
    "restaurantId": 1,
    "name": "Dal Bukhara",
    "description": "Slow-cooked black lentils in tomato gravy",
    "price": 895.0,
    "category": "Main Course",
    "available": true
  },
  {
    "restaurantId": 1,
    "name": "Tandoori Jhinga",
    "description": "Grilled jumbo prawns with Indian spices",
    "price": 1195.0,
    "category": "Appetizer",
    "available": true
  }
]'

```

###
### Place Order

**URL:** `POST /order-service/orders?userId=1`

**Request Body:**


```json
curl --location 'http://localhost:8083/order-service/orders?userId=1' \
--header 'userId: 1' \
--header 'Content-Type: application/json' \
--data '{
  "restaurantId": 1,
  "deliveryAddress": "224 Shyam Colony Faridabad, 121003",
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2,
      "pricePerItem": 790.0,
      "specialInstructions": "Extra spicy"
    },
     {
      "menuItemId": 2,
      "quantity": 2,
      "pricePerItem": 1195.0,
      "specialInstructions": "Extra spicy"
    }
  ],
  "paymentMethod":"RAZORPAY"
}'
```

###
### Order Status

**URL:** `GET /order-service/orders?userId=1`
**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```

###
### Payement gateway link
**URL:** `GET /order-service/orders?userId=1`
#
**Payment Methods**: `BHARAT_PE, PHONE_PE, RAZORPAY, MOCK_PE`
#
**Request Body:**
```json
curl --location 'http://localhost:8084/payment-service/payments/payment-gateway' \
--header 'Content-Type: application/json' \
--data '{
    "paymentMethod": "RAZORPAY",
    "orderId": 1
}'
```
### Again check order-status
### Order Status
**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```
###
### Confirm Payment

**URL:** `POST /payment-service/payments/process-payment`

**Transaction Id:** It can be obtained from order status 

**Request Body:** 
```json
curl --location 'http://localhost:8084/payment-service/payments/process-payment' \
--header 'Content-Type: application/json' \
--data '{
    "transactionId": "4c04bf84-08e0-4501-a94d-453c669d28da",
    "amount": "3970.00"
}'
```
### Again check order-status

###
### Order Status

**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```


### Restaurent take order : Preparing Mode

**URL:** `POST /restaurant-service/restaurants/orders/preparing`

**Request Body:** 
```json
curl --location 'http://localhost:8082/restaurant-service/restaurants/orders/preparing' \
--header 'Content-Type: application/json' \
--data '{
    "restaurantId":1,
    "orderId":1
}'
```
### Again check order-status
### Order Status
**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```

##
### Restaurent Prepared Food : Prepared

**URL:** `POST /restaurant-service/restaurants/orders/prepared'`

**Request Body:** 
```json
curl --location 'http://localhost:8082/restaurant-service/restaurants/orders/prepared' \
--header 'Content-Type: application/json' \
--data '{
    "restaurantId":1,
    "orderId":1
}'
```

#
### Rider is already assign after Prepred from rider service with kafka 
### Again check order-status
### Order Status
**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```
 

##
### Rider take order : OUT_FOR_DELIVERY

**URL:** `POST /rider-service/riders/take-order`

**Request Body:** 
```json
curl --location 'http://localhost:8085/rider-service/riders/take-order' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": 1,
    "riderId": "e13dd0d7-c4d8-40ef-93b1-b072278dfaf3"
}'
```
### Again check order-status

### Order Status

**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```

#

### Rider DeliverED Order : DELIVERED

**URL:** `POST /rider-service/riders/deliver`

**Request Body:** 
```json
curl --location 'http://localhost:8085/rider-service/riders/deliver' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": 1,
    "riderId": "e13dd0d7-c4d8-40ef-93b1-b072278dfaf3"
}'
```
### Again check order-status and FINAL statge

###
### Order Status

**URL:** `POST /order-service/orders?userId=1`

**Request Body:**
```json
curl --location 'http://localhost:8083/order-service/orders/1'
```

