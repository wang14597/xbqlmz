### System architecture diagram

![img.png](doc%2Fimg.png)

[架构图.drawio](doc%2F%BC%DC%B9%B9%CD%BC.drawio)

#### project struct

```
xbqlmz/
│
├── build.gradle
├── settings.gradle
│
├── auth-service/
│   ├── build.gradle
│   ├── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── xbqlmz/
│       │   │           └── auth/
│       │   │               └── AuthServiceApplication.java
│       │   └── resources/
│       │       ├── application.yml
│       │       └── static/
│       └── test/
│           ├── java/
│           └── resources/
│
├── service-a/
│   ├── build.gradle
│   ├── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── xbqlmz/
│       │   │           └── servicea/
│       │   │               └── ServiceAApplication.java
│       │   └── resources/
│       │       ├── application.yml
│       │       └── static/
│       └── test/
│           ├── java/
│           └── resources/
│
├── service-b/
│   ├── build.gradle
│   ├── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── xbqlmz/
│       │   │           └── serviceb/
│       │   │               └── ServiceBApplication.java
│       │   └── resources/
│       │       ├── application.yml
│       │       └── static/
│       └── test/
│           ├── java/
│           └── resources/
│
└── gateway/
    ├── build.gradle
    ├── src/
        ├── main/
        │   ├── java/
        │   │   └── com/
        │   │       └── xbqlmz/
        │   │           └── gateway/
        │   │               ├── GatewayApplication.java
        │   │               ├── config/
        │   │               ├── filter/
        │   │               └── route/
        │   └── resources/
        │       ├── application.yml
        │       └── static/
        └── test/
            ├── java/
            └── resources/

```

### module introduction：

##### **common:** base supported module for project

##### **auth-service:** auth and gateway service

### Development

#### step0:

```shell
sh setup.sh
```

#### step1:

```shell
cd docker
docker-compose -f docker-compose.middleware.yaml -p xbqlmz up -d
```

this step will create master-salve mysql database and redis

#### step2 configure auth-service:

add `application-dev.yaml` in `/auth-service/src/main/resources` such like follow:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/xbqlmz  # 这里配置主数据库
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  flyway:
    enabled: true
    locations: classpath:db/migration
  datasources:
    slave:
      url: jdbc:mysql://localhost:3307/xbqlmz  # 这里配置从数据库
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver

redis:
  host: localhost
  port: 6379
  password: xbqlmz123456
  timeout: 3
  poolMaxTotal: 10
  poolMaxIdle: 10
  poolMaxWait: 3
  database: 0

```

Then run project.
