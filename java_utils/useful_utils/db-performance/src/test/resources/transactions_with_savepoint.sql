--https://www.postgresql.org/docs/15/sql-savepoint.html
--https://www.postgresql.org/docs/15/sql-rollback-to.html
--https://www.postgresql.org/docs/15/sql-release-savepoint.html

--transaction will insert the values Postgresql, MySQL, not insert Oracle.(savepoint and rollback to savepoint) -----1
BEGIN;
	SELECT * FROM t_users_pk_int;
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

	INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
    ROLLBACK TO SAVEPOINT savepoint1;
   
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction will insert the values Postgresql, Oracle, MySQL, not insert MariaDb.(2 savepoints with the same names and one rollback to save point)-----2
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
    ROLLBACK TO SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction will insert the values Postgresql, Oracle, MySQL, not insert MariaDb.(2 savepoints with the same names and one rollback to save point and one releasesavepoint)-----3
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
    ROLLBACK TO SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction will insert the values Postgresql, MySQL, not insert Oracle, MariaDb.(1 savepoint and one rollback to save point and one releasesavepoint)-----4
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
    ROLLBACK TO SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction with ERROR! (1 savepoint and one rollback to save point and 2 releasesavepoints)-----5
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
    ROLLBACK TO SAVEPOINT savepoint1;
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction with ERROR! (1 savepoint and 2 releasesavepoints)-----6
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction inserted all 4 entries! (1 savepoint and 1 releasesavepoints)-----7
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction rollbacked all 4 entries, result is empty table! (1 savepoint and 1 releasesavepoints)-----8
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;--5
ROLLBACK;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction with ERROR! (1 savepoint and 1 releasesavepoints and one ROLLBACK TO SAVEPOINT)-----9
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql');
    SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--2
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('Oracle');
   	SELECT * FROM t_users_pk_int;--3
   	
    INSERT INTO t_users_pk_int(firstname) VALUES ('MariaDb');
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;--4
   	
   	ROLLBACK TO SAVEPOINT savepoint1;
    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL');
   	SELECT * FROM t_users_pk_int;--5
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--transaction with ERROR! (2 savepoints with the same names, after ROLLBACK TO SAVEPOINT, RELEASE SAVEPOINT and again ROLLBACK TO SAVEPOINT)-----10
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');
    SAVEPOINT savepoint1;
    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql14');
    SAVEPOINT savepoint1;
    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql12');

    -- rollback to the second savepoint
    ROLLBACK TO SAVEPOINT savepoint1;
    SELECT * FROM t_users_pk_int;               -- shows rows Postgresql15 and Postgresql14

    -- release the second savepoint
    RELEASE SAVEPOINT savepoint1;

    -- rollback to the first savepoint
    ROLLBACK TO SAVEPOINT savepoint1;
    SELECT * FROM t_users_pk_int;               -- shows only row Postgresql15
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------













--inserted both -----11
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;

    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');
    SAVEPOINT savepoint1;
    
   	INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL8');
   	select pg_sleep(5);
   	RELEASE SAVEPOINT savepoint1;
   	SELECT * FROM t_users_pk_int;
COMMIT;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


--SQL Error [25P01]: ERROR: RELEASE SAVEPOINT can only be used in transaction blocks
BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;
	
	BEGIN;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');
	    SAVEPOINT savepoint1;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('MySQL8');
	COMMIT;
	RELEASE SAVEPOINT savepoint1;
END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;
	
	BEGIN;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');
	    SAVEPOINT savepoint1;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql14');
	    SAVEPOINT savepoint1;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql12');
	
	    -- rollback to the second savepoint
	    ROLLBACK TO SAVEPOINT savepoint1;
	    SELECT * FROM t_users_pk_int;               -- shows rows Postgresql15 and Postgresql14
	
	    -- rollback to the first savepoint
	    ROLLBACK TO SAVEPOINT savepoint1;
	    SELECT * FROM t_users_pk_int;               -- shows only row Postgresql15 and Postgresql14
	ROLLBACK;
--	COMMIT;
	SELECT * FROM t_users_pk_int;--empty if ROLLBACK
END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------


BEGIN;
	SELECT * FROM t_users_pk_int;--1
	DELETE FROM t_users_pk_int;
	COMMIT AND CHAIN;
	
	BEGIN;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql15');
	    SAVEPOINT savepoint1;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql14');
	    SAVEPOINT savepoint1;
	    INSERT INTO t_users_pk_int(firstname) VALUES ('Postgresql12');
	
	    -- rollback to the second savepoint
	    ROLLBACK TO SAVEPOINT savepoint1;
	   	RELEASE SAVEPOINT savepoint1;
	    SELECT * FROM t_users_pk_int;               -- shows rows Postgresql15 and Postgresql14
	
	    -- rollback to the first savepoint
	    ROLLBACK TO SAVEPOINT savepoint1;
	    SELECT * FROM t_users_pk_int;               -- shows only row Postgresql15
--	ROLLBACK;
	COMMIT;
	SELECT * FROM t_users_pk_int;--empty if ROLLBACK
END;
---------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------