### To build the project, use the command:
`mvn "-Dmaven.test.skip" package  -P h2,mysql,docker`

### To create image, use the command:
`docker build -t [image_name] .`

### To run my-sql container, use the command:
`docker run  --detach   --name [mysql_container_name] -p 6604:3306 -e MYSQL_ROOT_PASSWORD=[root_pass] -e MYSQL_DATABASE=[database] -e MYSQL_USER=[user] -e MYSQL_PASSWORD=[pass] -d mysql`

### To run docker container, use the command:
`docker run --name [app_container_name] -p[port]:8080 -v ~/db.migration:/var/migration -e server=[mysql_container_name] -e port=3306 -e dbuser=[user] -e dbpassword=[pass] -e dbname=[database] --link [mysql_container_name]:mysql -d [image_name]`

