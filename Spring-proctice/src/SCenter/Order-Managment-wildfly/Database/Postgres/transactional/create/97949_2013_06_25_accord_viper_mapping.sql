CREATE TABLE V_tx.om_V_accord_order_mapping
(
   order_ext_id numeric NOT NULL,
   li_ext_id numeric NOT NULL,
   accord_order_id numeric NOT NULL,
   CONSTRAINT pk_vpr_accrd_liext PRIMARY KEY (li_ext_id, accord_order_id)
);