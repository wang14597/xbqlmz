CREATE USER 'replica'@'%' IDENTIFIED WITH 'mysql_native_password' BY 'replica_password';
GRANT REPLICATION SLAVE ON *.* TO 'replica'@'%';
FLUSH PRIVILEGES;
