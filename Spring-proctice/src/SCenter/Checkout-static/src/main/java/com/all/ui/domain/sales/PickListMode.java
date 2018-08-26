package com.AL.ui.domain.sales;

import java.math.BigInteger;

public class PickListMode {
	private boolean pickAll;
	private boolean pickN;
	private boolean pickOne;
	private boolean pickUpToN;
	private BigInteger n;
	
	public boolean isPickAll() {
		return pickAll;
	}
	public void setPickAll(boolean pickAll) {
		this.pickAll = pickAll;
	}
	public boolean isPickN() {
		return pickN;
	}
	public void setPickN(boolean pickN) {
		this.pickN = pickN;
	}
	public boolean isPickOne() {
		return pickOne;
	}
	public void setPickOne(boolean pickOne) {
		this.pickOne = pickOne;
	}
	public boolean isPickUpToN() {
		return pickUpToN;
	}
	public void setPickUpToN(boolean pickUpToN) {
		this.pickUpToN = pickUpToN;
	}
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}	
}
