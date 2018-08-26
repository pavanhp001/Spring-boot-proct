package com.AL.task;

import javax.ejb.Local;

@Local
public interface LocalTaskJob<T> extends Task<T> {

}