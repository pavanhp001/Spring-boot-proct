ALTER TABLE V_tx.cm_account drop COLUMN consumer_id;

ALTER TABLE V_tx.cm_account add COLUMN consumer_id bigint NULL;


alter table V_tx.om_job drop column  locked_at;
alter table V_tx.om_job add column locked_at timestamp default null;

alter table V_tx.om_job drop column  effective_from_on;
alter table V_tx.om_job add column effective_from_on timestamp default null;

alter table V_tx.om_job drop column  effective_to_on;
alter table V_tx.om_job add column effective_to_on timestamp default null;


ALTER TABLE V_tx.OM_LINE_ITEM ADD COLUMN   desired_start_ee_on timestamp(6) without time zone NULL;
ALTER TABLE V_tx.OM_LINE_ITEM ADD COLUMN   scheduled_start_ee_on timestamp(6) without time zone NULL;
ALTER TABLE V_tx.OM_LINE_ITEM ADD COLUMN   actual_start_ee_on timestamp(6) without time zone NULL;
ALTER TABLE V_tx.OM_LINE_ITEM ADD COLUMN   disconnect_ee_on timestamp(6) without time zone NULL;
ALTER TABLE V_tx.OM_LINE_ITEM ADD COLUMN   desired_start_req  text NULL;

ALTER TABLE V_tx.CM_CONSUMER ADD COLUMN CUSTOMER_TYPE character varying(15) NULL;


alter table V_tx.om_job add column customer_id character varying(30) default '-1';

 CREATE SEQUENCE  V_tx.cm_payevent_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;


  CREATE SEQUENCE  V_tx.cm_pay_event_stat_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;

alter table V_tx.cm_payevent drop column cc_disclosure;
alter table V_tx.cm_payevent add column cc_disclosure boolean default FALSE;
