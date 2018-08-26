ALTER TABLE V_tx.om_sales_order ADD COLUMN guid character varying(250);

CREATE INDEX concurrently omso_guid_idx01 ON V_tx.om_sales_order(guid);