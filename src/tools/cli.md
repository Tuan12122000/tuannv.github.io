Cài đặt Dịch Vụ
===============

Chuẩn bị
--------
```bash
$ mkdir -p /data/thanhtoan
```

Cài đặt Service ThanhToan & MySQL
---------------------------------

```bash
$ docker build -t thanhtoan .
$ cp app.jar /data/thanhtoan
$ docker-compose up -d

$ docker ps
CONTAINER ID   IMAGE              COMMAND                  CREATED             STATUS             PORTS                                                  NAMES
a280ea571e86   thanhtoan:latest   "/usr/bin/supervisord"   16 minutes ago      Up 16 minutes      0.0.0.0:8080->8080/tcp, :::8080->8080/tcp              thanhtoan
d2fe6f6e212b   mysql:8            "docker-entrypoint.s…"   About an hour ago   Up About an hour   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp   database
```

Xem logs Serivce ThanhToan:
---------------------------
```bash
$ docker exec -it thanhtoan bash
root@a280ea571e86:~# cd /var/log/supervisor/
root@a280ea571e86:/var/log/supervisor# tail -f -n1000 thanhtoan-std*

HOAC

$ /var/lib/docker/volumes/soi_thanhtoan-logs/_data
$ tail -f -n1000 thanhtoan-std*
```

Cập nhật Database:
------------------
```bash
$ docker cp database.sql database:/
$ docker exec -it database bash
bash-4.4# mysql -uroot -p
Enter password:
mysql> source \database.sql
```

Chạy lại docker-compose
-----------------------
```bash
$ docker-compose down
$ docker-compose up -d
```
