# Quarkus DynamoDB Crud Project
Guide: https://quarkus.io/guides/amazon-dynamodb

## Running the application in Amazon
```
sam deploy
```

## Testing the application
### Setup DynamoDB locally
```bash
# Start DynamoDB
docker run --publish 8000:8000  amazon/dynamodb-local:1.11.477 -jar DynamoDBLocal.jar -inMemory -sharedDb

# Create table and key
aws dynamodb create-table --table-name Animals  \
	--attribute-definitions AttributeName=animalName,AttributeType=S \
	--key-schema AttributeName=animalName,KeyType=HASH \
	--provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
	--endpoint-url http://localhost:8000
```

### Configure Quarkus App
Change `application.properties` configuration. 
```
quarkus.dynamodb.endpoint-override=http://localhost:8000

quarkus.dynamodb.aws.region=eu-central-1
quarkus.dynamodb.aws.credentials.type=static
quarkus.dynamodb.aws.credentials.static-provider.access-key-id=test-key
quarkus.dynamodb.aws.credentials.static-provider.secret-access-key=test-secret
```

### Start local API
```bash
mvn clean package
sam local start-api --template-file target/sam.jvm.yaml --docker-network host
```

## Test
If the application is running in Amazon, go to _[CloudFormation Stacks](https://us-east-2.console.aws.amazon.com/cloudformation/home?region=us-east-2#/stacks) > quarkus-dynamodb > Outputs_ to find the API URL and replace `http://localhost:3000/`.

```bash
# POST /animals: Create animal
curl --request POST \
  -d '{ "name": "Lion", "classification": "mammal" }' \
  -H 'Content-Type: application/json' \
  http://localhost:3000/animals

# GET /animals: Get list of animals
curl --request GET http://localhost:3000/animals

# GET /animals/{name}: Get animal by name 
curl --request GET http://localhost:3000/animals/Lion
```

