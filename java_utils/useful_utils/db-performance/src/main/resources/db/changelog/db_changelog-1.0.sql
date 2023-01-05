--liquibase formatted sql

--changeset jdev:1
--comment: create schema test_schema
--rollback DROP SCHEMA IF EXISTS test_schema CASCADE;
CREATE SCHEMA IF NOT EXISTS test_schema AUTHORIZATION db_admin;