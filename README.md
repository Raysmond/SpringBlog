SpringBlog
=====

SpringBlog is a very simple blog system implemented with Spring MVC.
It's one of my learning projects which took me two days to develop the first minimal and runnable version. I've put it on production
for my personal website [http://raysmond.com](http://raysmond.com).

SpringBlog is powered by many powerful frameworks and third-party projects:

- Spring MVC + Spring JPA + Hibernate - Powerful frameworks
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
# Install artifacts to your local repository
./gradlew build

# Start the web application
./gradlew jettyRun
```

View `http://localhost:8080/` on your browser.


## Deployment
Use gradle to build a production war and deploy it on Jetty9 server in Linux. Before deployment, please install
Java8, Jetty9 and the latest version of Redis server.

```
./gradlew warProduction
```

You'll find the production war in folder `build/dist`, for example `SpringBlog-production-0.1.war`.

Alternatively, you can run the `deploy.sh` script to deploy the application to remote server automatically. Please do change
the settings before running the script.