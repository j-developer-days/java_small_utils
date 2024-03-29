--liquibase formatted sql

--changeset jdev_root:1 runInTransaction:false
--comment: drop database, table space and role
DROP DATABASE IF EXISTS db_performance;
DROP TABLESPACE IF EXISTS db_performance_ts;
DROP ROLE IF EXISTS db_admin;

--changeset jdev_root:2 runInTransaction:false
--comment: create database, table space and role
CREATE ROLE db_admin WITH SUPERUSER CONNECTION limit 50 LOGIN PASSWORD '12345I#k' VALID UNTIL '2023-06-01' ;
-- mkdir -p /home/java_dev/postgesql_table_spaces/db_performance_ts
-- sudo chown postgres:postgres /home/java_dev/postgesql_table_spaces/db_performance_ts
CREATE TABLESPACE db_performance_ts OWNER db_admin LOCATION '/home/java_dev/postgesql_table_spaces/db_performance_ts';
CREATE DATABASE db_performance WITH OWNER db_admin TABLESPACE db_performance_ts;