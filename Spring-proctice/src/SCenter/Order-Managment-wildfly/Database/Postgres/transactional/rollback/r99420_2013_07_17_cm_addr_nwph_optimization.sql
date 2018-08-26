-- Drop newly created index and re-create the one that was just dropped

DROP INDEX V_tx.cm_addr_cons_extid_idx01;

CREATE INDEX concurrently cm_address_extid_idx01 ON V_tx.cm_address USING btree (external_id);

DROP INDEX V_tx.om_lineitem_newph_idx01;
-- End


