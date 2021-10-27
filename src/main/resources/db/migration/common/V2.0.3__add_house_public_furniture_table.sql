CREATE TABLE house_public_furniture
(
    id           uniqueidentifier primary key default NEWID(),
    house_id     uniqueidentifier not null,
    furniture_id uniqueidentifier not null,
    created_at   datetime         not null    default CURRENT_TIMESTAMP,
);

ALTER TABLE house_public_furniture
    ADD CONSTRAINT house_public_furniture_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);
ALTER TABLE house_public_furniture
    ADD CONSTRAINT house_public_furniture_fk_furniture_id FOREIGN KEY (furniture_id) REFERENCES public_furniture (id);