CREATE TABLE rooms
(
    id         uniqueidentifier primary key default NEWID(),
    number     int              not null,
    house_id   uniqueidentifier not null,
    disable    bit              default 0,
    created_at datetime         not null    default CURRENT_TIMESTAMP,
    updated_at datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE rooms
    ADD CONSTRAINT room_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);

CREATE INDEX room_number_index ON rooms (number);
CREATE INDEX room_created_at_index ON rooms (created_at);