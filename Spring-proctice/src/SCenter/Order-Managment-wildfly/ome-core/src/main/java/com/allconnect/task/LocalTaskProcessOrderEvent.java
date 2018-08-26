package com.AL.task;

import javax.ejb.Local;

@Local
public interface LocalTaskProcessOrderEvent<T> extends  Task<T> {

}
