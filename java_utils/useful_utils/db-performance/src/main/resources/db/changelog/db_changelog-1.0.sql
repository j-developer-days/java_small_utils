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

--changeset jdev:5
--comment: create new table t_user_numbers
--rollback DROP TABLE IF EXISTS t_user_numbers CASCADE;
CREATE TABLE IF NOT EXISTS t_user_numbers(
	id_small SMALLSERIAL NOT NULL,
	id SERIAL NOT NULL,
	id_bigserial BIGSERIAL NOT NULL,
	age SMALLINT NOT NULL,
	age_int INTEGER NOT NULL,
	big_number BIGINT NOT NULL,
	price REAL NOT NULL,
	total_price DOUBLE PRECISION NOT NULL,

	CONSTRAINT t_user_numbers__id__pk PRIMARY KEY(id)
) TABLESPACE db_performance_ts;