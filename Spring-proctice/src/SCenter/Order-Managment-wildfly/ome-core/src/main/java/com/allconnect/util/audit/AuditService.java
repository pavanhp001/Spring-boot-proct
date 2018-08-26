package com.AL.util.audit;
 
 

public interface AuditService<T> {

	Boolean audit(  final T auditable);
  
}
