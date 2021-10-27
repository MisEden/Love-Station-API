CREATE TABLE checkin_form_private_furniture
(
    id           uniqueidentifier primary key default NEWID(),
    checkin_id   uniqueidentifier not null,
    furniture_id uniqueidentifier not null,
    broken       BIT,
    created_at   datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE checkin_form_private_furniture
    ADD CONSTRAINT checkin_form_private_furniture_fk_checkin_id FOREIGN KEY (checkin_id) REFERENCES checkin_forms (id);
ALTER TABLE checkin_form_private_furniture
    ADD CONSTRAINT checkin_form_private_furniture_fk_furniture_id FOREIGN KEY (furniture_id) REFERENCES private_furniture (id);