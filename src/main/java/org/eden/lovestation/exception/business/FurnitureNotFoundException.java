package org.eden.lovestation.exception.business;

public class FurnitureNotFoundException extends Exception {
    public FurnitureNotFoundException() {
        super("無法根據條件取得家具(Furniture Not Found)");
    }
}
