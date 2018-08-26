CREATE TABLE sys_config(
  id bigint NOT NULL AUTO_INCREMENT,
  context varchar(25),
  create_date timestamp,
  data_type int8,
  name varchar(25) NOT NULL,
  value varchar(100),
  PRIMARY KEY (id)
)
