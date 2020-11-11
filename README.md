# chatbot clone api
## DB 세팅 방법
1. 내장된 `docker-compose.yml` 파일로 mongodb 설치
2. mongodb에서 `application.properties`에 적힌 정보를 토대로 db와 user 생성
```shell
use hellobot-db
db.createUser({ user:'admin', pwd:'****', roles:['userAdminAnyDatabase'] })
```

### mongoDB docker로 설치하기
1. docker 설치하고 실행
2. 프로젝트 루트에서 `docker-compose up -d` 입력
3. docker 컨테이너 접속 : `docker exec -it mongodb /bin/bash`입력
4. mongodb 접속 : `mongo` 입력
