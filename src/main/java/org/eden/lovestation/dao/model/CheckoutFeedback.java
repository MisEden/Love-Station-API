package org.eden.lovestation.dao.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checkout_feedback")
@Data
public class CheckoutFeedback {

    @GenericGenerator(name = "generator", strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Id
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "room_state_id")
    private RoomState roomStates;

//    private String feedback;

    private String bedding;
    private String beddingFeedback;

    private String bathroom;
    private String bathroomFeedback;

    private String refrigerator;
    private String refrigeratorFeedback;

    private String privateItem;
    private String privateItemFeedback;

    private String garbage;
    private String garbageFeedback;

    private String doorsWindowsPower;
    private String doorsWindowsPowerFeedback;

    private String securityNotification;
    private String securityNotificationFeedback;

    private String returnKey;
    private String returnKeyFeedback;

    private String returnCheckinFile;
    private String returnCheckinFileFeedback;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    public Date updatedAt;


    public void setRoomStates(RoomState roomState){
        this.roomStates = roomState;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

//    public void setFeedback(String checkoutFeedback){
//        this.feedback = checkoutFeedback;
//    }

}
