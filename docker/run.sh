# docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:latest

docker run -p 8080:8080 --link mysql:mysql \
-v /Users/Raysmond/Projects/Lab/SeGA/TaskViewer/build/libs:/opt/jetty/webapps niaquinto/jetty
