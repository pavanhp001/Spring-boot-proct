SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CUSTOMER_INTERACTION' AND COLUMN_NAME ='NOTES' AND DATA_LENGTH=3000;