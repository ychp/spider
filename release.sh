#!/usr/bin/env bash
set -e
echo 'pull newest code'
git checkout master
git pull

local_path=`pwd`

echo 'package'
mvn clean package -Dmaven.test.skip

echo 'make image'
cp ${local_path}/web/target/blog.jar ${local_path}/docker/prod/
cd ${local_path}/docker/prod
docker build . -t blog:1.0

echo 'start service'
if [ -f ~/blog.sh ];
then
    sh ~/blog.sh
else
    echo 'if [ -f /var/run/blog-api.pid ];' >> ~/blog.sh
    echo 'then' >> ~/blog.sh
    echo '  pid=`cat /var/run/blog-api.pid`' >> ~/blog.sh
    echo '  docker stop $pid' >> ~/blog.sh
    echo '  docker rm $pid' >> ~/blog.sh
    echo '  rm /var/run/blog-api.pid' >> ~/blog.sh
    echo 'fi;' >> ~/blog.sh
    echo 'pid=`docker run -p 8100:8100 -v /var/log/blog_new:/var/log/blog --link mysql:mysql --link redis:redis --name blog \' >> ~/blog.sh

    echo "请输入数据库名称:"
    read mysql_database

    echo "请输入数据库密码:"
    read mysql_password

    echo '-e MYSQL_HOST=mysql \' >> ~/blog.sh
    echo '-e MYSQL_PORT=3306 \' >> ~/blog.sh
    echo '-e MYSQL_NAME=root \' >> ~/blog.sh
    echo '-e MYSQL_DATABASE='${mysql_database}' \' >> ~/blog.sh
    echo '-e MYSQL_PASSWORD='${mysql_password}' \' >> ~/blog.sh

    echo "请输入redis密码:"
    read redis_password

    echo '-e REDIS_HOST=redis \' >> ~/blog.sh
    echo '-e REDIS_PORT=6379 \' >> ~/blog.sh
    echo '-e REDIS_AUTH='${redis_password}' \' >> ~/blog.sh

    echo "请输入es链接:"
    read es_host

    echo "请输入es端口:"
    read es_port

    echo "请输入es集群名称:"
    read es_cluster_name

    echo '-e ES_HOST='${es_host}' \' >> ~/blog.sh
    echo '-e ES_PORT='${es_port}' \' >> ~/blog.sh
    echo '-e ES_CLUSTER_NAME='${es_cluster_name}' \' >> ~/blog.sh

    echo "请输入对象存储服务类型:"
    read file_type
    echo '-e FILE_TYPE='${file_type}' \' >> ~/blog.sh

    if [ "$file_type" == "cos" ];
    then
        echo "请输入COS secretId:"
        read cos_secret_id
        echo '-e COS_SECRET_ID='${cos_secret_id}' \' >> ~/blog.sh

        echo "请输入COS secretKey:"
        read cos_secret_key
        echo '-e COS_SECRET_KEY='${cos_secret_key}' \' >> ~/blog.sh

        echo "请输入COS appId:"
        read cos_app_id
        echo '-e COS_APP_ID='${cos_app_id}' \' >> ~/blog.sh

        echo "请输入COS bucketName:"
        read cos_bucket_name
        echo '-e COS_BUCKET_NAME='${cos_bucket_name}' \' >> ~/blog.sh

        echo "请输入COS region:"
        read cos_region
        echo '-e COS_REGION='${cos_region}' \' >> ~/blog.sh
    fi;

    echo '-dit blog:1.0`' >> ~/blog.sh
    echo 'echo $pid >> /var/run/blog-api.pid' >> ~/blog.sh
    echo 'tail -f /var/log/blog_new/root.log' >> ~/blog.sh
    sh ~/blog.sh
fi;
