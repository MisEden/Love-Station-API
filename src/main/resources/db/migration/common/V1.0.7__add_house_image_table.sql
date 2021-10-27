CREATE TABLE house_images
(
    id       uniqueidentifier primary key default NEWID(),
    image    varchar(max)     not null,
    house_id uniqueidentifier not null
)


ALTER TABLE house_images
    ADD CONSTRAINT house_images_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);