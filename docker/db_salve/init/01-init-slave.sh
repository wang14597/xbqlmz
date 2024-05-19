#!/bin/bash

# Wait for the master database to be ready
until mysql -h db-master -P 3306 -uroot -proot  -e "select 1"; do
  >&2 echo "Master is unavailable - sleeping"
  sleep 1
done

# Get the master status
MASTER_STATUS=$(mysql -h db-master -P 3306 -uroot -proot -e "SHOW MASTER STATUS\G")
FILE=$(echo "$MASTER_STATUS" | grep File | awk '{print $2}')
POSITION=$(echo "$MASTER_STATUS" | grep Position | awk '{print $2}')

# Configure the slave
mysql -uroot -proot <<-EOSQL
  CHANGE MASTER TO MASTER_HOST='db-master',MASTER_USER='replica',MASTER_PASSWORD='replica_password',MASTER_LOG_FILE='$FILE',MASTER_LOG_POS=$POSITION;
  START SLAVE;
EOSQL