COMMENT ON TABLE V_tx.cm_consumer_address IS 'This is a join table for customer and their addresses';

COMMENT ON COLUMN V_tx.cm_consumer_address.address_role IS 'Role of the address like BillingAddress ServiceAddress etc';
COMMENT ON COLUMN V_tx.cm_consumer_address.unique_id IS 'Unique Id';
COMMENT ON COLUMN V_tx.cm_consumer_address.address_id IS 'Address external id';
COMMENT ON COLUMN V_tx.cm_consumer_address.consumer_id IS 'Customer external id';