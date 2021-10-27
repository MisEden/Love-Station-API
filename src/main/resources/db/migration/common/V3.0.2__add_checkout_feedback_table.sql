CREATE TABLE checkout_feedback
(
    id                              uniqueidentifier primary key default NEWID(),
    room_state_id                   uniqueidentifier,

    bedding                                  nvarchar(255),
    bedding_feedback                         nvarchar(255),
    bathroom                                 nvarchar(255),
    bathroom_feedback                        nvarchar(255),
    refrigerator                             nvarchar(255),
    refrigerator_feedback                    nvarchar(255),
    private_item                             nvarchar(255),
    private_item_feedback                    nvarchar(255),
    garbage                                  nvarchar(255),
    garbage_feedback                         nvarchar(255),
    doors_windows_power                      nvarchar(255),
    doors_windows_power_feedback             nvarchar(255),
    security_notification                    nvarchar(255),
    security_notification_feedback           nvarchar(255),
    return_key                               nvarchar(255),
    return_key_feedback                      nvarchar(255),
    return_checkin_file                      nvarchar(255),
    return_checkin_file_feedback             nvarchar(255),

    created_at    datetime         not null    default CURRENT_TIMESTAMP,
    updated_at    datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE checkout_feedback
    ADD CONSTRAINT checkout_feedback_fk_room_state_id FOREIGN KEY (room_state_id) REFERENCES room_states (id);