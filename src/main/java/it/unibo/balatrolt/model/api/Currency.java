package it.unibo.balatrolt.model.api;

/**
 * An interface modelling the concept of a generic currency. 
 */
public interface Currency {
    /**
     * @return the current value of currency
     */
    int getValue();

    /**
     * Sets the value of the currency to value.
     * @param value to set
     * @throws IllegalArgumentException if value is negative
     */
    void setValue(int value);

    /**
     * Adds value to the current value of the currency.
     * @param value to add
     * @throws IllegalArgumentException if value is negative
     */
    void addValue(int value);
}
