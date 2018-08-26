COMMENT ON TABLE V_tx.om_line_item_om_stat_rec IS 'Join table for lineitem and its status';

COMMENT ON COLUMN V_tx.om_line_item_om_stat_rec.om_line_item_id IS 'Reference to lineitem id';
COMMENT ON COLUMN V_tx.om_line_item_om_stat_rec.historicstatus_id IS 'Reference to status id';
COMMENT ON COLUMN V_tx.om_line_item_om_stat_rec.listorder IS 'sequential order number';

