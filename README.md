

[LinkedIn] https://www.linkedin.com/in/babak-shojaee


# About Stock Application
The goal of the application is to create simple crud on the stock entity with separate API and user interface

This application consist of two modules

stock-mvc-api: which handle api management

stock-ui: which handle user interface

### Built With


stock-mvc-api

* [Springboot](https://spring.io/projects/spring-boot)

* [Lombok](https://projectlombok.org/)

* [h2](https://www.h2database.com/)


stock-ui
* [React.js](https://reactjs.org/)
* [Redux.js](https://redux.js.org/)


<!-- USAGE EXAMPLES -->
## Usage stock-mvc-api

# open stock-mvc-api directory
$ cd stock/stock-mvc-api

# package  with dependencies
$ mvn clean package

# for docker build
docker build -t stock  .

# serve with hot reload at localhost
$ mvn spring-boot run

## Usage stock ui
# open stock-ui directory
$ cd stock-ui

# install dependencies
$ npm i || npm install

# build for production
$ npm run build

# for docker build
docker build -t stock-ui:latest .

# serve with hot reload at localhost
$ npm run dev

# how to run 
Locate to root of project then (where docker-compose.yml)

docker-compose up

# how to access 
stock-mvc-api

http://localhost:8080

stock-ui

http://localhost:5000


## swagger api documentation
You can find out API documentation via the following link
http://localhost:8080/swagger-ui.html

<!-- ROADMAP -->
## Roadmap

- [] Reactive api


<!-- CONTACT -->
## Contact
bababakshojaee@gmail.com



Project Link: https://github.com/babak-shojaee/stock

<p align="right">(<a href="#top">back to top</a>)</p>





[linkedin-url]: https://linkedin.com/in/babakshojaee
[product-screenshot]: images/stock-scrrenshot.png
