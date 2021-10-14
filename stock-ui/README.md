

## Build Setup
git clone https://github.com/babak-shojaee/stock.git

# open app directory
$ cd stock-ui

# install dependencies
$ npm i || npm install

# build for production
$ npm run build

# for docker build
docker build -t stock-ui:latest .

# serve with hot reload at localhost
$ npm run dev

