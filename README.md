# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.13/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.13/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#web)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/2.6.13/reference/htmlsingle/#io.webservices)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

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
