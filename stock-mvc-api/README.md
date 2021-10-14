
# open stock-mvc-api directory
$ cd stock/stock-mvc-api

# package  with dependencies
$ mvn clean package

# for docker build
docker build -t stock  .

# serve with hot reload at localhost
$ mvn spring-boot run


