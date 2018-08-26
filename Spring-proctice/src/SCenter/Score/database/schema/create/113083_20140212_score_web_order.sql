CREATE TABLE SCORE_WEB_ORDER (
  ID bigint(20) NOT NULL auto_increment,
  SAVED_ON timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  CUSTOMER_ID varchar(20) default '',
  ORDER_ID varchar(20) default '',
  LINEITEM_ID varchar(20) default '',
  PROVIDER_ID varchar(20) default '',
  AGENT_ID varchar(20) default '',
  PAGE varchar(100) default '',
  KEY_NAME varchar(200) default NULL,
  UCID varchar(50) default NULL,
  PRIMARY KEY  (ID)  
);

CREATE INDEX ORDER_IDX ON SCORE_WEB_ORDER(ORDER_ID);
CREATE INDEX AGENT_IDX ON SCORE_WEB_ORDER(AGENT_ID);
CREATE INDEX UCIDX ON SCORE_WEB_ORDER(UCID);