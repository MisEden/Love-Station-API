package org.eden.lovestation.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CheckinCheckRequest {
    private String roomStateId;
    private List<String> existPublicFurnitures;
    private List<String> brokenPublicFurnitures;
    private List<String> existPrivateFurnitures;
    private List<String> brokenPrivateFurnitures;
    private String lock;
    private String power;
    private List<String> convention;
    private String contract;
    private String security;
    private String heater;

    public String getExistPublicFurnituresConverted() {
        return String.join(",", this.existPublicFurnitures);
    }
    public String getBrokenPublicFurnituresConverted() {
        return String.join(",", this.brokenPublicFurnitures);
    }
    public String getExistPrivateFurnituresConverted() {
        return String.join(",", this.existPrivateFurnitures);
    }
    public String getBrokenPrivateFurnituresConverted() {
        return String.join(",", this.brokenPrivateFurnitures);
    }
    public String getConventionConverted() {
        return String.join(",", this.convention);
    }
}
