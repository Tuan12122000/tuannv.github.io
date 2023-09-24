FROM openjdk:17-jdk-slim

ENV HOME=/data

ENV TZ=Asia/Ho_Chi_Minh
RUN set -x \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

WORKDIR $HOME

LABEL maintainer="tuan"

RUN apt-get update; apt-get install -y supervisor
RUN mkdir -p /data/thanhtoan

COPY ./app.jar $HOME/thanhtoan/
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 8080

CMD ["/usr/bin/supervisord"]
