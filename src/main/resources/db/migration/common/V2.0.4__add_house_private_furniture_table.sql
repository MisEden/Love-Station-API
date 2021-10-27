CREATE TABLE house_private_furniture
(
    id           uniqueidentifier primary key default NEWID(),
    room_id     uniqueidentifier not null,
    furniture_id uniqueidentifier not null,
    created_at   datetime         not null    default CURRENT_TIMESTAMP,
);

ALTER TABLE house_private_furniture
    ADD CONSTRAINT house_private_furniture_fk_house_id FOREIGN KEY (room_id) REFERENCES rooms (id);
ALTER TABLE house_private_furniture
    ADD CONSTRAINT house_private_furniture_fk_furniture_id FOREIGN KEY (furniture_id) REFERENCES private_furniture (id);