select * 
from pg_stat_activity
where (state like '%idle in transaction%');

select * from pg_stat_activity;

select application_name, state
from pg_stat_activity;

--select * from pg_prepared_xacts;
   
SELECT * FROM t_users_pk_int;

SELECT pg_export_snapshot();

SELECT * FROM t_users_pk_int;
DELETE FROM t_users_pk_int WHERE true ;

ROLLBACK;