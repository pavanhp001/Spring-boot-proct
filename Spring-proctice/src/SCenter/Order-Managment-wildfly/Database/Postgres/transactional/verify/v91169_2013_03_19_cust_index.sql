SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='CUSTOMER_CONTEXT_IDX01') IN ('CUSTOMER_CONTEXT_IDX01');

SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='CUST_ATTRIB_IDX01') IN ('CUST_ATTRIB_IDX01');

SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='SALE_ORDER_CTXT_IDX01') IN ('SALE_ORDER_CTXT_IDX01');

SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='OM_LI_ATTRIB_IDX01') IN ('OM_LI_ATTRIB_IDX01');