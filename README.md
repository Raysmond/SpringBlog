SpringBlog
=====

SpringBlog is a very simple and clean-design blog system implemented with Spring Boot.
It's one of my learning projects to explore awesome features in Spring Boot web programming. I've put it on production
for my personal blog site [https://raysmond.com](https://raysmond.com).

SpringBlog is powered by many powerful frameworks and third-party projects:

- Spring Boot and many of Spring familiy (e.g. Spring MVC, Spring JPA, Spring Secruity and etc)
- Hibernate + MySQL
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - A solid high-performance JDBC connection pool
- [Bootstrap](https://getbootstrap.com) - A very popular and responsive front-end framework
- [Pegdown](https://github.com/sirthias/pegdown) - A pure-java markdown processor
- [ACE Editor](http://ace.c9.io/) - A high performance code editor which I use to write posts and code.
- [Pygments](http://pygments.org/) - A python library for highlighting code syntax
- [Jade4j](https://github.com/neuland/jade4j) - [Jade](http://jade-lang.com/) is an elegant template language.
- [Webjars](http://www.webjars.org/) - A client-side web libraries packaged into JAR files. A easy way to manage JavaScript and CSS vendors in Gradle.
- [Redis](http://redis.io/) - A very powerful in-memory data cache server.

## Development

Before development, please install the following service software:

- [MySQL](https://www.mysql.com)
- [Redis](http://redis.io)
- [Pygments](http://pygments.org)

Edit the spring config profile `src/main/resources/application.yml` according to your settings.

And start MySQL and Redis first before running the application.

```
# If you're using Ubuntu server

# Install MySQL
apt-get install mysql-server
service mysql start
mysql -u root -p
>> create database spring_blog;


# Install Python pygments
apt-get install python-pip
apt-get install pygments
```

```
# If you want to enable redis cache
# Install redis server first, you can find instructions
# from https://www.digitalocean.com/community/tutorials/how-to-install-and-use-redis
service redis_6379 start
```

This is a Gradle project. Make sure Gradle is installed in your machine.
Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).
I recommend you import the source code into Intellij IDE to edit the code.

```
# Start the web application
./gradlew bootRun
```

View `http://localhost:8080/` on your browser.

### Todo

- Handling static web resources with `ResourceSolvers` and `ResourceTransformers` introduced in Spring Framework 4.1.
- Awesome features are always welcome.

Welcome to contribute.

## Deployment

- Build application jar `./gradlew build`, then upload the distribution jar (e.g. `build/libs/SpringBlog-0.1.jar`) to your remote server.
- Upload `application-production.yml` to your server and change it according to your server settings.
- Run it (Java8 is a must)

  ```
  # assuming you have the jar and yml files under current dir
  java -jar SpringBlog-0.1.jar --spring.config.location=application-production.yml
  ```

## License
Modified BSD license. Copyright (c) 2015, Jiankun LEI (Raysmond).
