package com.AL.task;

import javax.ejb.Local;

@Local
public interface LocalTaskRules<T> extends Task<T> {

}