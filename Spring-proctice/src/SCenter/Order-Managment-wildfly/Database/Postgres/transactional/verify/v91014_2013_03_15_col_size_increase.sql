SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='BIRTH_INFO' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='DRIVER_LICENSE_NO' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='SSN' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='PIN' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='SECURITY_ANSWER' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_CONSUMER' AND COLUMN_NAME ='PARTNER_SSN' AND DATA_LENGTH=5000;


SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='EXPIRE_MONTH' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='EXPIRE_YEAR' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='CREDIT_CARD_NUMBER' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='CARD_HOLDER_NAME' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='CHECKING_ACCOUNT_NUMBER' AND DATA_LENGTH=5000;

SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='CM_BILLING_INFORMATION' AND COLUMN_NAME ='ROUTING_NUMBER' AND DATA_LENGTH=5000;


SELECT '1' as EXPECTED, COUNT(*) as ACTUAL FROM ALL_TAB_COLUMNS WHERE TABLE_NAME ='OM_JOB' AND COLUMN_NAME ='STATUS_QUEUED' AND DATA_LENGTH=50;





