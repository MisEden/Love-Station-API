package org.eden.lovestation.dto.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SendCheckoutInvestigationRequest {

    @Size(min = 0, max = 200)
    private short serviceEfficiency;
    @Size(min = 0, max = 200)
    private short serviceAttitude;
    @Size(min = 0, max = 200)
    private short serviceQuality;
    @Size(min = 0, max = 200)
    private short equipmentFurniture;
    @Size(min = 0, max = 200)
    private short equipmentElectricDevice;
    @Size(min = 0, max = 200)
    private short equipmentAssistive;
    @Size(min = 0, max = 200)
    private short equipmentBedding;
    @Size(min = 0, max = 200)
    private short equipmentBarrierFreeEnvironment;
    @Size(min = 0, max = 200)
    private short environmentClean;
    @Size(min = 0, max = 200)
    private short environmentComfort;
    @Size(min = 0, max = 200)
    private short safetyFirefighting;
    @Size(min = 0, max = 200)
    private short safetySecomEmergencySystem;

    public short getServiceEfficiency(){
        return serviceEfficiency;
    }

}
