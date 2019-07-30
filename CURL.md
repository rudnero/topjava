#Testing REST by curl
###### Get meals: "curl http://localhost:8080/topjava/rest/meals"
###### Get meal by Id: "curl http://localhost:8080/topjava/rest/meals/100002"
###### Create new meal: "curl -d '{"dateTime": "2015-06-01T18:00", "description": "Новый завтрак", "calories": 300}' -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals"
###### Update meal by Id: "curl -d '{"dateTime": "2015-06-01T18:00", "description": "Обновленный завтрак", "calories": 200}' -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100002"
###### Delete meal by Id: "curl -X DELETE http://localhost:8080/topjava/rest/meals/100002"
###### Filter meals by date and time: "curl -X POST http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=01:00&endDate=2015-05-31&endTime=23:00"
###### Filter meals by date: "curl -X POST http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&endDate=2015-05-31"
###### Filter meals by time: "curl -X POST http://localhost:8080/topjava/rest/meals/filter?startTime=07:00&endTime=11:00"