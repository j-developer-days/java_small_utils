--liquibase formatted sql

--changeset jdev:1
--comment: create schema test_schema
--rollback DROP SCHEMA IF EXISTS test_schema CASCADE;
CREATE SCHEMA IF NOT EXISTS test_schema AUTHORIZATION db_admin;

--changeset jdev:2
--comment: DROP SCHEMA test_schema
DROP SCHEMA IF EXISTS test_schema CASCADE;

--changeset jdev:3
--comment: create new table t_users_pk_int
--rollback DROP TABLE IF EXISTS t_users_pk_int CASCADE;
CREATE TABLE IF NOT EXISTS t_users_pk_int(
	id serial NOT NULL,
	firstname VARCHAR(100) NOT NULL,

	CONSTRAINT t_users_pk_int__id__pk PRIMARY KEY(id)
) TABLESPACE db_performance_ts;

--changeset jdev:4
--comment: create new table t_users_pk_uuid
--rollback DROP TABLE IF EXISTS t_users_pk_uuid CASCADE;
CREATE TABLE IF NOT EXISTS t_users_pk_uuid(
	id UUID NOT NULL,
	firstname VARCHAR(100) NOT NULL,

	CONSTRAINT t_users_pk_uuid__id__pk PRIMARY KEY(id)
) TABLESPACE db_performance_ts;