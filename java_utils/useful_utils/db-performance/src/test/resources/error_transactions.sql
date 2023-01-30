--transaction COMMIT 1 - unique constraint, in - SELECT * FROM t_users_pk_int; - state is - idle in transaction (aborted)
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');

COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT 2 - unique constraint, in - SELECT * FROM t_users_pk_int; - state is - idle in transaction (aborted)
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');

END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction ROLLBACK 3
BEGIN;
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');

ROLLBACK;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--to fix - SQL Error [25P02]: ERROR: current transaction is aborted, commands ignored until end of transaction block
ROLLBACK;
COMMIT;


--transaction COMMIT 4
DO $$
BEGIN
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');

END;
$$
LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction COMMIT 5
DO $$
BEGIN
DELETE FROM t_users_pk_int;

INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
COMMIT;
INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');

END;
$$
LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------



--https://www.postgresql.org/docs/current/errcodes-appendix.html
--transaction COMMIT 6 ROLLBACK
DO $$
DECLARE
	text_var1 text;
	text_var2 text;
	text_var3 text;
BEGIN
	DELETE FROM t_users_pk_int;
	
	INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
	INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'JAVA');
	COMMIT;

EXCEPTION
	WHEN SQLSTATE '23505' THEN
    	GET STACKED DIAGNOSTICS text_var1 = MESSAGE_TEXT,
	                          text_var2 = PG_EXCEPTION_DETAIL,
	                          text_var3 = PG_EXCEPTION_HINT;
        RAISE NOTICE 'exception - %, %, %', text_var1, text_var2, text_var3;
       	ROLLBACK;
	WHEN OTHERS THEN
		GET STACKED DIAGNOSTICS text_var1 = MESSAGE_TEXT,
	                          text_var2 = PG_EXCEPTION_DETAIL,
	                          text_var3 = PG_EXCEPTION_HINT;
        RAISE NOTICE 'exception - %, %, %', text_var1, text_var2, text_var3;
		RAISE NOTICE 'EXCEPTION!';
	
RAISE NOTICE 'BEFORE END!';
END;
$$
LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------

--transaction COMMIT 7 ROLLBACK
DO $$
DECLARE
	text_var1 text;
	text_var2 text;
	text_var3 text;
BEGIN
	DELETE FROM t_users_pk_int;
	
	INSERT INTO t_users_pk_int (id, firstname) VALUES (27169, 'C#');
	INSERT INTO t_users_pk_int (id, firstname) VALUES (-27169, 'JAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVA1');
	COMMIT;

EXCEPTION
WHEN SQLSTATE '23505' THEN
    	GET STACKED DIAGNOSTICS text_var1 = MESSAGE_TEXT,
                          text_var2 = PG_EXCEPTION_DETAIL,
                          text_var3 = PG_EXCEPTION_HINT;
        RAISE NOTICE 'exception - %, %, %', text_var1, text_var2, text_var3;
       	ROLLBACK;
WHEN OTHERS THEN
	RAISE NOTICE 'EXCEPTION!';
	ROLLBACK;

RAISE NOTICE 'BEFORE END!';
END;
$$
LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------

