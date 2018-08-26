
DROP INDEX cm_address_extid_idx01;

CREATE INDEX concurrently cm_addr_cons_extid_idx01 ON V_tx.cm_address USING btree (consumer_external_id);

CREATE INDEX concurrently om_lineitem_newph_idx01 ON V_tx.om_line_item (new_phone);