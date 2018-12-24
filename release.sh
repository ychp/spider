#!/usr/bin/env bash
set -e
echo 'pull newest code'
git checkout master
git pull

local_path=`pwd`

echo 'package'
mvn clean package -Dmaven.test.skip -U

echo 'make image'
cp ${local_path}/web-starter/target/spider.jar ${local_path}/docker/prod/
cd ${local_path}/docker/prod
docker build . -t spider:1.0

echo 'start service'
if [ -f ~/spider.sh ];
then
    sh ~/spider.sh
else
    echo 'if [ -f /var/run/spider.pid ];' >> ~/spider.sh
    echo 'then' >> ~/spider.sh
    echo '  pid=`cat /var/run/spider.pid`' >> ~/spider.sh
    echo '  docker stop $pid' >> ~/spider.sh
    echo '  docker rm $pid' >> ~/spider.sh
    echo '  rm /var/run/spider.pid' >> ~/spider.sh
    echo 'fi;' >> ~/spider.sh
    echo 'pid=`docker run -p 8200:8200 -v /var/log/spider:/var/log/spider --link mysql:mysql --link redis:redis --name spider \' >> ~/spider.sh

    echo "请输入数据库名称:"
    read mysql_database

    echo "请输入数据库密码:"
    read mysql_password

    echo '-e MYSQL_HOST=mysql \' >> ~/spider.sh
    echo '-e MYSQL_PORT=3306 \' >> ~/spider.sh
    echo '-e MYSQL_NAME=root \' >> ~/spider.sh
    echo '-e MYSQL_DATABASE='${mysql_database}' \' >> ~/spider.sh
    echo '-e MYSQL_PASSWORD='${mysql_password}' \' >> ~/spider.sh

    echo "请输入redis密码:"
    read redis_password

    echo '-e REDIS_HOST=redis \' >> ~/spider.sh
    echo '-e REDIS_PORT=6379 \' >> ~/spider.sh
    echo '-e REDIS_AUTH='${redis_password}' \' >> ~/spider.sh

    echo "请输入对象存储服务类型:"
    read file_type
    echo '-e FILE_TYPE='${file_type}' \' >> ~/spider.sh

    if [ "$file_type" == "cos" ];
    then
        echo "请输入COS secretId:"
        read cos_secret_id
        echo '-e COS_SECRET_ID='${cos_secret_id}' \' >> ~/spider.sh

        echo "请输入COS secretKey:"
        read cos_secret_key
        echo '-e COS_SECRET_KEY='${cos_secret_key}' \' >> ~/spider.sh

        echo "请输入COS appId:"
        read cos_app_id
        echo '-e COS_APP_ID='${cos_app_id}' \' >> ~/spider.sh

        echo "请输入COS bucketName:"
        read cos_bucket_name
        echo '-e COS_BUCKET_NAME='${cos_bucket_name}' \' >> ~/spider.sh

        echo "请输入COS region:"
        read cos_region
        echo '-e COS_REGION='${cos_region}' \' >> ~/spider.sh
    fi;

    echo '-dit spider:1.0`' >> ~/spider.sh
    echo 'echo $pid >> /var/run/spider.pid' >> ~/spider.sh
    echo 'tail -f /var/log/spider/root.log' >> ~/spider.sh
    sh ~/spider.sh
fi;
