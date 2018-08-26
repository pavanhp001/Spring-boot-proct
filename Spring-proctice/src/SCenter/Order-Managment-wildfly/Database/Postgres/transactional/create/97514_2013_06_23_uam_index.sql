create index concurrently uam_userlogin_idx01 on V_tx.uam_user (user_login);

create index concurrently uam_usr_mdata_idx01 on V_tx.uam_user_metadata (name,value);