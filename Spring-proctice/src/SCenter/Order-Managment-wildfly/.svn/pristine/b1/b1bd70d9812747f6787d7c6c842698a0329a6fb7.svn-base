--Customer changes

ALTER TABLE V_tx.cm_consumer
   ALTER COLUMN best_time_to_call1 TYPE timestamp with time zone;

ALTER TABLE V_tx.cm_consumer
   ALTER COLUMN best_time_to_call2 TYPE timestamp with time zone;

ALTER TABLE V_tx.cm_consumer ADD COLUMN dt_agent_id character varying(50);

ALTER TABLE V_tx.cm_consumer ADD COLUMN dt_created date;


-- Customer interaction changes

ALTER TABLE V_TX.CM_CUSTOMER_INTERACTION  ADD IS_SYS_CREATED NUMERIC(9,0)   DEFAULT 1;
ALTER TABLE V_TX.CM_CUSTOMER_INTERACTION  ADD LI_EXT_ID NUMERIC(19,0)   DEFAULT 0;

-- lineitem changes

ALTER TABLE V_TX.OM_LINE_ITEM  ADD LI_STATE NUMERIC default 0 ;

-- Customer account changes

CREATE SEQUENCE V_tx.cm_account_seq
  INCREMENT 1
  START 1
  CACHE 20;


  CREATE TABLE V_tx.cm_account
(
  id bigint NOT NULL,
  acct_status character varying(255),
  acct_type character varying(255),
  acct_type_ind character varying(255),
  addr_ref character varying(255),
  bill_acct_num character varying(255),
  billing_phone character varying(255),
  consent_to_cc character varying(255),
  consumer_ref character varying(255),
  credit_alert character varying(255),
  credit_card_ref character varying(255),
  credit_check character varying(255),
  external_id bigint,
  is_authorized integer,
  pin character varying(255),
  prev_addr_ind character varying(255),
  provider_type character varying(255),
  sec_ans character varying(255),
  sec_creditcard character varying(255),
  sec_question character varying(255),
  sec_tc_accept character varying(255),
  partial_ssn character varying(255),
  supp_indicator character varying(255),
  consumer_id bigint NOT NULL,
  CONSTRAINT cm_account_pkey PRIMARY KEY (id ),
  CONSTRAINT FK_CM_ACCT_CONSUMER FOREIGN KEY (consumer_id)
      REFERENCES V_tx.cm_consumer (consumer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);





CREATE   INDEX  IDX_CM_ACCT_CUST ON  V_tx.CM_ACCOUNT(CONSUMER_ID);
CREATE   INDEX  IDX_CM_ACCT_CUST_EXTID ON  V_tx.CM_ACCOUNT(CONSUMER_REF);



