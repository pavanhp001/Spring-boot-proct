ALTER TABLE V_tx.cm_customer_interaction ADD COLUMN service_type character varying(200);

CREATE INDEX concurrently cm_intn_ordid_idx01 ON V_tx.cm_customer_interaction(order_ext_id);

CREATE INDEX concurrently cm_cons_name_idx01  ON V_tx.cm_consumer(lastname , firstname );

CREATE INDEX concurrently cm_addr_zip_idx01  ON V_tx.cm_address(postal_code);