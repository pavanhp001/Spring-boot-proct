package com.A.V.gateway;

public interface ClientService<T> {

	public T send(T order);

	public String extract(String orderRR);
}
