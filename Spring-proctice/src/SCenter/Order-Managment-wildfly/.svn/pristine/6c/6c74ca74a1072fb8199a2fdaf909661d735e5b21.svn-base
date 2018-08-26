-- rollback system properties table
drop table sys_properties;


-- rollback newly added rollback cols

ALTER TABLE om_job DROP COLUMN external_id;
ALTER TABLE om_job DROP COLUMN locked_at;
ALTER TABLE om_job DROP COLUMN desc1;
ALTER TABLE om_job DROP COLUMN desc2;