CREATE TABLE checkin_form_confirm
(
    id              uniqueidentifier primary key default NEWID(),
    checkin_id      uniqueidentifier not null,
    lock            nvarchar(255)   not null,
    power           nvarchar(255)   not null,
    convention      nvarchar(255)   not null,
    contract        nvarchar(255)   not null,
    security        nvarchar(255)   not null,
    heater          nvarchar(255)   not null,
    created_at      datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE checkin_form_confirm
    ADD CONSTRAINT checkin_form_confrim_checkin_id FOREIGN KEY (checkin_id) REFERENCES checkin_forms (id);