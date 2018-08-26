ALTER TABLE V_tx.om_line_item ADD COLUMN event_type int;

ALTER TABLE V_tx.om_line_item ADD COLUMN is_event_selected boolean;

ALTER TABLE V_tx.om_line_item ADD COLUMN is_event_completed boolean;

UPDATE V_tx.om_line_item set event_type=0 WHERE event_type IS NULL;
commit;

CREATE TABLE CM_CUSTOMER_SURVEY
(
  ID NUMBER NOT NULL
, SURVEY_ID NUMBER
, CONSUMER_ID NUMBER
);

ALTER TABLE CM_CUSTOMER_SURVEY ADD CONSTRAINT PK_CM_CUSTOMER_SURVEY PRIMARY KEY(ID);

CREATE SEQUENCE CM_CUST_CSAT_SURVEY_SEQ INCREMENT BY 1 START WITH 1 CACHE 20;


CREATE TABLE om_event_type
(
   id numeric,
   event_type integer,
   event_name character varying(100),
   CONSTRAINT pk_om_event_type PRIMARY KEY (id)
);

COMMENT ON COLUMN om_event_type.id IS 'primary key';
COMMENT ON COLUMN om_event_type.event_type IS 'Stores event type. For eg
1 = TPV
2 = Warm transfer';
COMMENT ON COLUMN om_event_type.event_name IS 'Store short desc about event';
COMMENT ON TABLE om_event_type
  IS 'This tables store staic read only information about different types of event like warm transfer event, TPV etc';

INSERT INTO om_event_type(id, event_type, event_name) VALUES (1, 1, 'TPV');

INSERT INTO om_event_type(id, event_type, event_name) VALUES (2, 2, 'Warm Transfer');

commit;
