COMMENT ON TABLE V_tx.cm_customer_context IS 'This table store customer context information';

COMMENT ON COLUMN V_tx.cm_customer_context.id IS 'Primary key';
COMMENT ON COLUMN V_tx.cm_customer_context.name IS 'Name of the context attribute';
COMMENT ON COLUMN V_tx.cm_customer_context.entity_name IS 'Entity Name of the context attribute. Can be used to group of attributes';
COMMENT ON COLUMN V_tx.cm_customer_context.value IS 'Value of attribute';
COMMENT ON COLUMN V_tx.cm_customer_context.consumer_id IS 'Reference to customer';

  
   
   
   