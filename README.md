# Quarkus DynamoDB Crud Project
Guide: https://quarkus.io/guides/amazon-dynamodb#creating-json-rest-service

## Running the application
### Setup DynamoDB locally
```
# Start DynamoDB
docker run --publish 8000:8000  amazon/dynamodb-local:1.11.477 -jar DynamoDBLocal.jar -inMemory -sharedDb

# Create table and key
aws dynamodb create-table --table-name Animals  \
	--attribute-definitions AttributeName=animalName,AttributeType=S \
	--key-schema AttributeName=animalName,KeyType=HASH \
	--provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
	--endpoint-url http://localhost:8000
```

### Start local API
```
sam local start-api --template-file target/sam.jvm.yaml --docker-network host
```

## Test
```bash
# POST /animals: Create animal
curl --request POST \
  -d '{ "name": "Lion", "classification": "mammal" }' \
  -H 'Content-Type: application/json' \
  http://localhost:3000/animals

# GET /animals: Get list of animals
curl --request GET http://localhost:3000/animals

# POST /animals/{name}: Get animal by name 
curl --request GET http://localhost:3000/animals/Lion
```

