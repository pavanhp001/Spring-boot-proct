COMMENT ON TABLE V_tx.cm_cust_attribute IS 'This table store customer extra information as name value pair';

COMMENT ON COLUMN V_tx.cm_cust_attribute.id IS 'Primary key';
COMMENT ON COLUMN V_tx.cm_cust_attribute.source IS 'Source of the attribute';
COMMENT ON COLUMN V_tx.cm_cust_attribute.name IS 'Name of the attribute';   
COMMENT ON COLUMN V_tx.cm_cust_attribute.value IS 'Value of the attribute';   
COMMENT ON COLUMN V_tx.cm_cust_attribute.description IS 'Description of the attribute';   
COMMENT ON COLUMN V_tx.cm_cust_attribute.consumer_id IS 'Reference to customer id';  
   
   
  