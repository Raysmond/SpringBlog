SpringBlog
=====

中文开发和部署文档请查看：http://raysmond.com/posts/springblog-guide

SpringBlog is a very simple and clean-design blog system implemented with Spring Boot.
It's one of my learning projects to explore awesome features in Spring Boot web programming. You can check my blog 
site for demo [https://raysmond.com](http://raysmond.com).

There's no demo online. Here's the screenshot of my previous blog homepage.
![](http://7b1fa0.com1.z0.glb.clouddn.com/screencapture-blog-raysmond-8080-1480663084590.png)

SpringBlog is powered by many powerful frameworks and third-party projects:

- Spring Boot and many of Spring familiy (e.g. Spring MVC, Spring JPA, Spring Secruity and etc)
- Hibernate + MySQL
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - A solid high-performance JDBC connection pool
- [Bootstrap](https://getbootstrap.com) - A very popular and responsive front-end framework
- [Pegdown](https://github.com/sirthias/pegdown) - A pure-java markdown processor
- [ACE Editor](http://ace.c9.io/) - A high performance code editor which I use to write posts and code.
- [Redis](http://redis.io/) - A very powerful in-memory data cache server.
- EhCache
- Thymeleaf (Spring MVC)

## Development

Before development, please install the following service software:

- [MySQL](https://www.mysql.com)

Edit the spring config profile `src/main/resources/application.yml` according to your settings.

And start MySQL and Redis first before running the application.

```
# If you're using Ubuntu server

# Install MySQL
apt-get install mysql-server
service mysql start
mysql -u root -p
>> create database spring_blog;
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

```
git clone https://github.com/Raysmond/SpringBlog.git 
cd SpringBlog

bower install 
```

1. Clone the project
2. Download all dependencies
3. Open the project in Intellij IDEA.
4. Run `SpringBlogApplication.java` as Java application.
5. Preview: http://localhost:8080
    Admin: http://localhost:8080/admin , the default admin account is: `admin`, password: `admin`


> Lombok is required to run the project. You can install the plugin in Intellij IDEA.
> Reference: https://github.com/mplushnikov/lombok-intellij-plugin


- Build application jar `./gradlew build`, then upload the distribution jar (e.g. `build/libs/SpringBlog-0.1.jar`) to your remote server.
- Upload `application-production.yml` to your server and change it according to your server settings.
- Run it (Java8 is a must)

  ```
  java -jar SpringBlog-0.1.jar --spring.profiles.active=prod
  # OR with external spring profile file
  java -jar SpringBlog-0.1.jar --spring.config.location=application-production.yml
  ```

## TODO

- [x] Upgrade frontend framework to Bootstrap4
- [ ] Replace Jade with Thymeleaf(HTML)
- [ ] Frontend building tools, e.g. webpack
- [x] Use hibernate 2nd level cache (EHCache?)
- [ ] Markdown preview while editing
- [ ] Html editor

## License
Modified BSD license. Copyright (c) 2015 - 2018, Jiankun LEI (Raysmond).
