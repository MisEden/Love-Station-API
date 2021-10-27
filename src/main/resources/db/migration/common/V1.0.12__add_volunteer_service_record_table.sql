CREATE TABLE volunteer_service_record
(
    id                              uniqueidentifier primary key default NEWID(),
    checkin_application_id          uniqueidentifier not null,
    volunteer_id                    uniqueidentifier not null,
    house_id                        uniqueidentifier not null,
    room_id                         uniqueidentifier not null,
    service                         nvarchar(255),
    remark                          nvarchar(255),
    viewed                          nvarchar(255) default N'未檢視',

    created_at       datetime       not null    default CURRENT_TIMESTAMP,
    updated_at       datetime       not null    default CURRENT_TIMESTAMP
)

ALTER TABLE volunteer_service_record
    ADD CONSTRAINT volunteer_service_record_fk_checkin_application_id FOREIGN KEY (checkin_application_id) REFERENCES checkin_applications (id);
ALTER TABLE volunteer_service_record
    ADD CONSTRAINT volunteer_service_record_fk_landlord_id FOREIGN KEY (volunteer_id) REFERENCES volunteers (id);
ALTER TABLE volunteer_service_record
    ADD CONSTRAINT volunteer_service_record_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);
ALTER TABLE volunteer_service_record
    ADD CONSTRAINT volunteer_service_record_fk_room_id FOREIGN KEY (room_id) REFERENCES rooms (id);
--ALTER TABLE volunteer_service_record
--    ADD CONSTRAINT volunteer_service_record_df_viewed DEFAULT N'未檢視' FOR viewed;