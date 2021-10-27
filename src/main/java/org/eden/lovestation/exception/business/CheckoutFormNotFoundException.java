package org.eden.lovestation.exception.business;

public class CheckoutFormNotFoundException extends Exception {
    public CheckoutFormNotFoundException() { super("無法根據條件取得退房回報紀錄(Checkout Form Not Found)"); }
}
