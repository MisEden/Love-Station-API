package org.eden.lovestation.exception.business;

public class PublicFurnitureNotFoundException extends Exception {
    public PublicFurnitureNotFoundException() {
        super("無法根據條件取得棧點家具紀錄(PublicFurniture Not Found)");
    }
}
