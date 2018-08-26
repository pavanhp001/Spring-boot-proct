ALTER SEQUENCE V_tx.CM_CONSUMER_BEAN_SEQ RESTART WITH 200000000;

ALTER SEQUENCE V_tx.OM_SALESORDER_BEAN_SEQ RESTART WITH 200000000;

ALTER SEQUENCE V_tx.om_line_item_bean_seq RESTART WITH 200000000;

ALTER TABLE V_tx.om_line_item  ALTER COLUMN li_create_date TYPE timestamp without time zone;

ALTER TABLE V_tx.om_sel_dialogue  ALTER COLUMN dialogue_value TYPE character varying(8000);