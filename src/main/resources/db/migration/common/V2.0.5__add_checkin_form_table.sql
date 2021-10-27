CREATE TABLE checkin_forms
(
    id            uniqueidentifier primary key default NEWID(),
    house_id      uniqueidentifier not null,
    room_state_id uniqueidentifier not null,
    created_at    datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE checkin_forms
    ADD CONSTRAINT checkin_form_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);
ALTER TABLE checkin_forms
    ADD CONSTRAINT checkin_form_fk_room_state_id FOREIGN KEY (room_state_id) REFERENCES room_states (id);