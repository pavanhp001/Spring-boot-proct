CREATE TABLE SYS_PROPERTIES
(
  ID NUMERIC(19, 0) NOT NULL
, NAME character varying(60) NOT NULL
, VALUE character varying(50)
, CREATE_DATE DATE
, CONTEXT character varying(25)
);

ALTER TABLE SYS_PROPERTIES ADD CONSTRAINT SYS_PROPERTIES_PK PRIMARY KEY( ID);

-- adding column for om_job table

ALTER TABLE om_job ADD COLUMN EXTERNAL_ID character varying(60);
ALTER TABLE om_job ADD COLUMN LOCKED_AT date;
ALTER TABLE om_job ADD COLUMN desc1 character varying(120);
ALTER TABLE om_job ADD COLUMN desc2 character varying(120);



 character varying(120);

 boolean DEFAULT false,
