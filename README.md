SpringBlog
=====

SpringBlog is a very simple blog system implemented with Spring MVC.
It's one of my learning project which took me two days to develop the first minimal but runnable version. I've put it on production
for my personal website [http://raysmond.com](http://raysmond.com).

SpringBlog is powered by many powerful frameworks and third-party projects:

- Spring MVC + Spring JPA + Hibernate - Powerful frameworks
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - A solid high-performance JDBC connection pool
- [Bootstrap](https://getbootstrap.com) - A very popular and responsive front-end framework
- [Pegdown](https://github.com/sirthias/pegdown) - A pure-java markdown processor
- [ACE Editor](http://ace.c9.io/) - A high performance code editor
- [Pygments](http://pygments.org/) - Python syntax highlighter
- [Jade4j](https://github.com/neuland/jade4j) - [Jade](http://jade-lang.com/) is an elegant template language

## Development

Make sure Gradle is installed in your machine. Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).

Before development, please install MySQL5+ first and edit `persistence.properties` according to your database configurations.

```
# Install artifacts to your local repository
./gradlew build

# Start the web application
./gradlew jettyRun
```

View `http://localhost:8080/SpringBlog` on your browser.