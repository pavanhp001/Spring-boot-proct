--VERIFY COL IS ADDED
SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='OM_LINE_ITEM' AND COLUMN_NAME ='ON_DELIVERY_PRICE';