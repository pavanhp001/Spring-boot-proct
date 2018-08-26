
ALTER TABLE V_tx.cm_consumer
   ALTER COLUMN best_time_to_call1 TYPE timestamp without time zone;

ALTER TABLE V_tx.cm_consumer
   ALTER COLUMN best_time_to_call2 TYPE timestamp without time zone;

ALTER TABLE V_tx.cm_consumer DROP COLUMN dt_agent_id;
ALTER TABLE V_tx.cm_consumer DROP COLUMN dt_created;

ALTER TABLE V_TX.CM_CUSTOMER_INTERACTION  DROP IS_SYS_CREATED;
ALTER TABLE V_TX.CM_CUSTOMER_INTERACTION  DROP LI_EXT_ID ;


ALTER TABLE V_TX.OM_LINE_ITEM  DROP LI_STATE NUMERIC;

DROP SEQUENCE V_TX.CM_ACCOUNT_SEQ;
DROP TABLE V_TX.CM_ACCOUNT;