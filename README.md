SpringBlog
=====

SpringBlog is a very simple and clean-design blog system implemented with Spring Boot.
It's one of my learning projects to explore awesome features in Spring Boot web programming. I had put it on production
for my personal blog site [https://raysmond.com](https://raysmond.com) for about one year. Now, I turn to Hexo for my blog site.

There's no demo online. Here's the screenshot of my previous blog homepage.
![](http://7b1fa0.com1.z0.glb.clouddn.com/screencapture-blog-raysmond-9000-1480663084590.png)

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
pip install pygments
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

## Development

**How to import the project into Intellij IDEA and run from the IDE?**


1. Clone the project
`git clone https://github.com/Raysmond/SpringBlog.git `
2. Download all dependencies
`cd SpringBlog `
`./gradlew idea `
3. Open the project in Intellij IDEA.
4. Run `Application.java` as Java application.
5. Preview: http://localhost:9000
    Admin: http://localhost:9000/admin , the default admin account is: admin@admin.com, password: admin


> Lombok is required to run the project. You can install the plugin in Intellij IDEA.
> Reference: https://github.com/mplushnikov/lombok-intellij-plugin


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
