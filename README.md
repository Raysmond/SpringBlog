SpringBlog
=====

TaskViewer is for business operators to perform tasks and execute business processes.

## Development

This is a Maven project powered by Spring MVC framework. The following commands are useful for local development and testing:


### Gradle (recommended)
Make sure Gradle is installed in your machine. Try `gradle -v` command. Otherwise install in from [http://www.gradle.org/](http://www.gradle.org/).

```
# Install artifacts to your local repository
./gradlew build

# Start the web application
./gradlew jettyRun
```

View `http://localhost:8080/TaskViewer` on your browser.

### Maven

```
mvn install -DskipTests

mvn clean jetty:run
```

View `http://localhost:8080/` on your browser.


