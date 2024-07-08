##### Tech Stack
- Java, Spring Boot, H2 DB, Kafka, maven

##### How to run app?
- install java11 & maven
- install kafka or use docker to run app which will run kafka image (docker compose up)
- Database : This app using the H2 database, credentials can be used from application.properties file
- Run MobilePlaceApplication.java to start this app or use command **./mvnw spring-boot:run**
- Table creating is happening whenever app starts using **schema.sql** file
- Data setup for the mobile product is happening whenever app starts using **data.sql** file

# API Details

**I. Provide all the product details with it's current availability status**
> product
This table hold the current state of all the mobile products.

GET http://localhost:8080/product

[========]

**II. Endpoint that allows a phone to be booked / returned.**
status and user_id must be passed as book or return.
> product_history_info
Whenever book or return operaion successfully happen , audit history will be maintned into the table  product_history_info

###### #### Mobile Book API Request

    curl --request POST \
        --url http://localhost:8080/product/{product_id} \
        --header 'Content-Type: application/json' \
        --header 'User-Agent: insomnia/8.6.1' \
        --data '{
        "user_id" : "user1",
        "status" : "book"
        }'

###### API Response


    {
    	"success": true,
    	"response_body": {
    		"product_history_id": 1,
    		"product_id": 1,
    		"status": "BOOK",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T21:46:06.351Z"
    	}
    }



###### #### Mobile Return API Request

    curl --request POST \
        --url http://localhost:8080/product/{product_id} \
        --header 'Content-Type: application/json' \
        --header 'User-Agent: insomnia/8.6.1' \
        --data '{
        "user_id" : "user1",
        "status" : "return"
        }'

###### API Response


    {
    	"success": true,
    	"response_body": {
    		"product_history_id": 2,
    		"product_id": 1,
    		"status": "return",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T23:46:06.351Z"
    	}
    }


###### Cases
- if user try to book already booked product_id, then API respone send with success as false


    {
    	"success": false,
    	"response_body": {
    		"product_history_id": 1,
    		"product_id": 1,
    		"status": "BOOK",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T21:46:06.351Z"
    	}
    }


- if user try to return booked product_id, then API respone send with success as true


    {
    	"success": false,
    	"response_body": {
    		"product_history_id": 2,
    		"product_id": 1,
    		"status": "RETURN",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T21:46:06.351Z"
    	}
    }

- Whenever an successful booking or return happened, feed will be sent to kafka topic. This feed enable or disable configured by using the application.proprites
###### product.kafka.enable=trueproduct.kafka.enable=true
- Feed Topic is also configurable using property
###### product.kafka.topic=product-update-feedproduct.kafka.topic=product-update-feed

[========]

**III. Endpoint that provide history of product id.**
GET http://localhost:8080/product/{product_id}

API Response

    [
    	{
    		"product_history_id": 1,
    		"product_id": 1,
    		"status": "BOOK",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T21:46:06.351Z"
    	},
    	{
    		"product_history_id": 2,
    		"product_id": 1,
    		"status": "RETURN",
    		"user_id": "user1",
    		"placed_time": "2024-07-08T23:17:12.906Z"
    	}
    ]


[========]

**IV. Endpoint that provide history of product by history id.**
GET http://localhost:8080/product/id/{product_history_id}

API Response

    {
    	"product_history_id": 1,
    	"product_id": 1,
    	"status": "BOOK",
    	"user_id": "user1",
    	"placed_time": "2024-07-08T21:46:06.351Z"
    }
[========]
