# README_prod

## 系統需求

- ubuntu 20.04
- java 11
- SQL Server 2019
- Docker
- Docker-compose

## 只有OS 重零開始裝環境

執行環境腳本，會裝好所有環境

```bash
sh enviroment.sh 
```

## Docker 運行

砍掉並重啟docker，只要重新執行docker腳本 sh ./dockerbuild.sh 即可

DB資料會全部消失

### local (本地開發環境)

```bash
sh ./dockerbuild.sh local
```

### dev (測試環境)

```bash
sh ./dockerbuild.sh dev
```

- 自動啟動SQL Server and Redis container
- maven 打包成 jar 並且啟動love-station-api container
- Domain: [https://api.loveeden.tk](https://api.loveeden.tk/)
- 沒有連線到外部資料庫，使用docker內的DB的話，用dev環境即可

### production (上線環境)

```bash
sh ./dockerbuild.sh prod
```

- 自動啟動Redis container and 連線到remote SQL Server
- maven 打包 jar 並且啟動love-station-api container
- Domain: [https://ehome.eden.org.tw](https://ehome.eden.org.tw/)

### 上線 SSH

- IP：210.61.226.87 (從外部連線）

    api application server

    - port -8080

## [application.properties](http://application.properties) 相關配置

- SQL server 2019
    - username : sa
    - password : LoveStation123!
    - port : 1433
- JWT token 過期時效
    - jwt.lifeTime=86400 （秒）
- 發送 email
    - 填入負責代發email的帳密

    ```
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=edenlovestation@gmail.com
    spring.mail.password=edenlovestation123
    ```

- line bot channel token
    - 各個角色的 line bot channel token 複製貼在這裡
- 圖片資料夾
    - 存在 ./storage 下面各個資料夾

## 安裝第三方SSL憑證和nginx

- [https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04](https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-20-04)

## nginx轉port到對應domain

裝完nginx以後，nginx.conf加入下面幾行，使得domain對應到本地web port和api port

```bash
##
# Basic Settings
###

server {
  server_name ehome.eden.org.tw;
  location / {
    proxy_pass http://127.0.0.1:80;
    client_max_body_size 100m;
  }
}

server {
  server_name ehomeline.eden.org.tw;
  location / {
    proxy_pass http://127.0.0.1:8080;
    client_max_body_size 100m;
  }
}
```

## Database Schema

![dbo](./dbo_prod.png)