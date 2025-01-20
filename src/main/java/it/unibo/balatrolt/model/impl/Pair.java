package it.unibo.balatrolt.model.impl;

import java.util.Objects;

/**
 * Simple Pair class of two generic types 
 */
public class Pair<X,Y> {
	
	private final X e1;
	private final Y e2;
	
	public Pair(X x, Y y) {
		super();
		this.e1 = x;
		this.e2 = y;
	}

	public X get1() {
		return e1;
	}

	public Y get2() {
		return e2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(e1, e2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair other = (Pair) obj;
		return Objects.equals(e1, other.e1) && Objects.equals(e2, other.e2);
	}

	@Override
	public String toString() {
		return "Pair [e1=" + e1 + ", e2=" + e2 + "]";
	}
}
