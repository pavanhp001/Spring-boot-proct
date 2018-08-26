ALTER TABLE V_tx.cm_account drop COLUMN consumer_id;

ALTER TABLE V_tx.cm_account add COLUMN consumer_id bigint NOT NULL;



alter table V_tx.om_job drop column  locked_at;
alter table V_tx.om_job add column locked_at date default null;


alter table V_tx.om_job drop column  effective_from_on;
alter table V_tx.om_job add column effective_from_on date default null;

alter table V_tx.om_job drop column  effective_to_on;
alter table V_tx.om_job add column effective_to_on date default null;


ALTER TABLE V_tx.OM_LINE_ITEM DROP COLUMN DESIRED_START_EE_ON;
ALTER TABLE V_tx.OM_LINE_ITEM DROP COLUMN SCHEDULED_START_EE_ON;
ALTER TABLE V_tx.OM_LINE_ITEM DROP COLUMN ACTUAL_START_EE_ON;
ALTER TABLE V_tx.OM_LINE_ITEM DROP COLUMN DISCONNECT_EE_ON;
ALTER TABLE V_tx.OM_LINE_ITEM DROP COLUMN DESIRED_START_REQ;


ALTER TABLE CM_CONSUMER DROP COLUMN   CUSTOMER_TYPE;

alter table om_job drop column customer_id;

DROP SEQUENCE  V_tx.cm_payevent_seq ;
DROP SEQUENCE  V_tx.cm_pay_event_stat_seq ;

alter table V_tx.cm_payevent add column cc_disclosure boolean;