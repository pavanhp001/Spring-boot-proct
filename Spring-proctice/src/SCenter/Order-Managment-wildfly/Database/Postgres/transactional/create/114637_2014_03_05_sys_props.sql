INSERT INTO sys_properties(id, name, value, create_date, context) VALUES (6, 'broadcast.max.threadpool.size', '75', sysdate,'COMMON');


INSERT INTO V_tx.sys_properties(id, name, value, create_date, context) VALUES (7, 'broadcast.min.threadpool.size', '25', sysdate,'COMMON');

INSERT INTO V_tx.sys_properties(id, name, value, create_date, context) VALUES (8, 'broadcast.monitor.delay.seconds', '60', sysdate,'COMMON');

commit;