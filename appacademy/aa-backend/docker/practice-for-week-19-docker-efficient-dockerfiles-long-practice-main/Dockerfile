FROM ubuntu

ADD . /app

RUN apt-get update
RUN apt-get upgrade -y
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y npm nodejs
RUN apt-get install -y openssh-server nginx
RUN cd /app && npm install

CMD sshd & cd /app && npm start