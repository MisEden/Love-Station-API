package org.eden.lovestation.exception.business;

public class PrivateFurnitureNotFoundException extends Exception {
    public PrivateFurnitureNotFoundException() {
        super("無法根據條件取得房間家具紀錄(PublicFurniture Not Found)");
    }
}
