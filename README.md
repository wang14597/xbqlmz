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

#### step2:

add `application-dev.yaml` in `src/main/resources` such like follow:

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
