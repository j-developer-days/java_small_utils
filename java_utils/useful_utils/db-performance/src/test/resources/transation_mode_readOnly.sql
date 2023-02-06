BEGIN;
	SET TRANSACTION READ ONLY;
	SELECT * FROM t_users_pk_int;
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

	INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------

BEGIN;
	SET TRANSACTION READ WRITE;
	SELECT * FROM t_users_pk_int;
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

	INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------