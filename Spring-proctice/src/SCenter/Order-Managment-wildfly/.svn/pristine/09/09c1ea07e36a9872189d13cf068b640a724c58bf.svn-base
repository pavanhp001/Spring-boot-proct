--pay event

 


CREATE TABLE V_tx.cm_payevent
(
  id numeric(19,0) NOT NULL,
  amount numeric(19,3),
  billing_info_ex_id character varying(255),
  confirm_num character varying(255),
  cvv character varying(255),
  event_type character varying(255),
  lineitem_id character varying(255),
  order_id character varying(255),
  pay_status character varying(255),
  trans_date timestamp(6) without time zone,
  billing_info_id bigint NOT NULL,
  external_id character varying(255),
  currentstatus_id numeric(19,0),
  consumer_id bigint,
  cc_disclosure boolean DEFAULT false
);

alter table cm_payevent drop column cc_disclosure;
alter table cm_payevent add column cc_disclosure boolean default FALSE;
 
 
  
  

ALTER TABLE V_TX.CM_PAYEVENT ADD CONSTRAINT pk_cm_payevent  PRIMARY KEY (id);

ALTER TABLE V_TX.CM_PAYEVENT ADD CONSTRAINT fk_cm_payment_consumer FOREIGN KEY( CONSUMER_ID ) REFERENCES CM_CONSUMER( CONSUMER_ID );

ALTER TABLE V_TX.CM_PAYEVENT ADD CONSTRAINT fk_cm_payment_billing FOREIGN KEY ( BILLING_INFO_ID ) REFERENCES CM_BILLING_INFORMATION ( ID );


-- payment EVENT status

CREATE TABLE V_TX.CM_PAY_EVENT_STAT
(
  ID NUMERIC(19, 0) NOT NULL
, AGENT_EXT_ID character varying(255)
, CREATED_AT TIMESTAMP(6)
, EXTERNAL_ID character varying(255)
, STATUS character varying(255)
);

ALTER TABLE V_TX.CM_PAY_EVENT_STAT ADD CONSTRAINT pk_cm_pay_event_stat PRIMARY KEY (id);


-- payment event status reasons

CREATE TABLE V_TX.CM_PAY_EVENT_STAT_REASONS
(
  CM_PAY_EVENT_STAT_ID NUMERIC(19, 0) NOT NULL
, ELEMENT character varying(255)
, LISTORDER NUMERIC(10, 0) NOT NULL
);

ALTER TABLE V_TX.CM_PAY_EVENT_STAT_REASONS ADD CONSTRAINT pk_cm_pay_event_reason PRIMARY KEY(CM_PAY_EVENT_STAT_ID);

ALTER TABLE V_TX.CM_PAY_EVENT_STAT_REASONS ADD CONSTRAINT fk_pay_evnt_stat_reason FOREIGN KEY ( CM_PAY_EVENT_STAT_ID ) REFERENCES CM_PAY_EVENT_STAT ( ID );



-- CM_PAYEVENT_CM_PAY_EVENT_STAT


CREATE TABLE V_TX.CM_PAYEVENT_CM_PAY_EVENT_STAT
(
  CM_PAYEVENT_ID NUMERIC(19, 0) NOT NULL
, PAYMENTSTATUSHISTORY_ID NUMERIC(19, 0) NOT NULL
, LISTORDER NUMERIC(10, 0) NOT NULL
);

ALTER TABLE V_TX.CM_PAYEVENT_CM_PAY_EVENT_STAT ADD CONSTRAINT pk_cm_payevent_cm_pay_event_stat PRIMARY KEY(CM_PAYEVENT_ID);

ALTER TABLE V_TX.CM_PAYEVENT_CM_PAY_EVENT_STAT ADD CONSTRAINT uq_cm_payevent_cm_pay_event_stat UNIQUE ( PAYMENTSTATUSHISTORY_ID ) ;

ALTER TABLE V_TX.CM_PAYEVENT_CM_PAY_EVENT_STAT ADD CONSTRAINT fk_cm_payevent_cm_pay_event_stat_hist FOREIGN KEY ( PAYMENTSTATUSHISTORY_ID ) REFERENCES CM_PAY_EVENT_STAT ( ID );

ALTER TABLE V_TX.CM_PAYEVENT_CM_PAY_EVENT_STAT ADD CONSTRAINT fk_cm_payevent_cm_pay_event_stat FOREIGN KEY ( CM_PAYEVENT_ID ) REFERENCES CM_PAYEVENT ( ID );


alter table cm_payevent drop column billing_info_id;
alter table cm_payevent add column billing_info_id bigint default null;
