SELECT '0' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='CM_ADDRESS_EXTID_IDX01') in ('CM_ADDRESS_EXTID_IDX01');

SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='CM_ADDR_CONS_EXTID_IDX01') in ('CM_ADDR_CONS_EXTID_IDX01');

SELECT '1' AS Expected, COUNT(*) AS Actual FROM DUAL WHERE (SELECT INDEX_NAME FROM ALL_INDEXES WHERE INDEX_NAME ='OM_LINEITEM_NEWPH_IDX01') in ('OM_LINEITEM_NEWPH_IDX01');