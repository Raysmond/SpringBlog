SpringBlog
=====

SpringBlog is a very simple blog system implemented with Spring MVC.
It's one of my learning projects which took me two days to develop the first minimal and runnable version. I've put it on production
for my personal website [http://raysmond.com](http://raysmond.com).

I think I need to emphasized that it's a learning project to learn and apply awesome and new features in Java web programming, especially with
Spring Framework. A blog site is just a good place to get started.

SpringBlog is powered by many powerful frameworks and third-party projects:

- Spring Boot + Spring MVC + Spring JPA + Hibernate - Powerful frameworks
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - A solid high-performance JDBC connection pool
- [Bootstrap](https://getbootstrap.com) - A very popular and responsive front-end framework
- [Pegdown](https://github.com/sirthias/pegdown) - A pure-java markdown processor
- [ACE Editor](http://ace.c9.io/) - A high performance code editor
- [Pygments](http://pygments.org/) - Python syntax highlighter
- [Jade4j](https://github.com/neuland/jade4j) - [Jade](http://jade-lang.com/) is an elegant template language
- [Webjars](http://www.webjars.org/) - A client-side web libraries packaged into JAR files. A easy way to manage JavaScript and CSS vendors in Gradle.
- [Redis](http://redis.io/) - A very powerful in-memory data cache server.

## Development

Before development, please install the following service software:

- MySQL5
- Redis
    - [how-to-install-and-use-redis-on-ubuntu](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-redis)
- Pygments
    - A python library for highlighting code syntax

Edit application configuration file `src/main/resources/dev_app.properties` according to your settings.

And start MySQL, Redis and Pygments first before running the application.

```
# If you're using Ubuntu server
sudo service mysql start
sudo service redis_6379 start
sudo pip install pygments
```

This is a Gradle project. Make sure Gradle is installed in your machine.
Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).
I recommend you import the source code into Intellij IDE to edit the code.

```
# Start the web application
./gradlew bootRun
```

View `http://localhost:8080/` on your browser.

## Deployment

- Build application jar `./gradlew build`, then upload the distribution jar (e.g. `build/libs/SpringBlog-0.1.jar`) to your remote server.
- Upload `application-production.yml` to your server and change the configuration according to your server settings.
- Run it (make sure you have installed Java8 already)

  ```
  # assuming you have the jar and yml files under current dir
  java -jar SpringBlog-0.1.jar --spring.config.location="application-production.yml"
  ```
  
## Todo

- Handling static web resources with `ResourceSolvers` and `ResourceTransformers` introduced in Spring Framework 4.1.
- Post tagging implementation
- Awesome features are always welcome.
  
