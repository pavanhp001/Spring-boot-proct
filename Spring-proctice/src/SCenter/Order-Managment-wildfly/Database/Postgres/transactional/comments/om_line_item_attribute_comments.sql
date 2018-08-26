COMMENT ON TABLE V_tx.om_li_attribute IS 'LineItem attribute informaiton';

COMMENT ON COLUMN V_tx.om_li_attribute.id IS 'Primary key';
COMMENT ON COLUMN V_tx.om_li_attribute.name IS 'Name of the attribute';
COMMENT ON COLUMN V_tx.om_li_attribute.value IS 'Value of the attribute';
COMMENT ON COLUMN V_tx.om_li_attribute.description IS 'Description of the attribute';
COMMENT ON COLUMN V_tx.om_li_attribute.line_item_id IS 'Reference lineitem id';
COMMENT ON COLUMN V_tx.om_li_attribute.source IS 'Source of the attribute';
