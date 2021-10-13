FROM node:lts

WORKDIR /app


ENV PATH /app/node_modules/.bin:$PATH


COPY . /app/

RUN npm install


# copy production folder  to  app folder
COPY build /app

# start node
CMD serve -s build