curl --location 'http://localhost:8086/user-service/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Vinod Tiwari",
    "email": "vtiwari@americana-food.com"
}'