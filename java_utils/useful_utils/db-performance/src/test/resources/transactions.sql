--transaction COMMIT 1
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');

COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT AND CHAIN && COMMIT - 2
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
COMMIT AND CHAIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
COMMIT;


--transaction COMMIT AND CHAIN && ROLLBACK - 3
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
COMMIT AND CHAIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
ROLLBACK;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction ROLLBACK AND CHAIN && COMMIT - 4
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
ROLLBACK AND CHAIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
COMMIT;


--transaction ROLLBACK AND CHAIN && ROLLBACK - 5
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
ROLLBACK AND CHAIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
ROLLBACK;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT - the same as #2 - 6
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
COMMIT;
BEGIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT with warning - there is no transaction in progress - 7
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
COMMIT;
--BEGIN;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27167, 'PHP');
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction ROLLBACK - 8
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');

ROLLBACK;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT && ROLLBACK - 9
BEGIN;
	BEGIN;
	DELETE FROM t_users_pk_int;
	COMMIT;
	
	INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
	INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
	ROLLBACK;
END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction ROLLBACK && COMMIT - 10
BEGIN;
	BEGIN;
	DELETE FROM t_users_pk_int;
	ROLLBACK;
	
	INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
	INSERT INTO t_users_pk_int (id, firstname) VALUES (-2374, 'JAVA');
	COMMIT;
END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


