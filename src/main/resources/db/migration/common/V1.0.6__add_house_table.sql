CREATE TABLE houses
(
    id                   uniqueidentifier primary key default NEWID(),
    serial               SMALLINT         not null,
    city                 nvarchar(200)    not null,
    name                 nvarchar(200)    not null,
    introduction         nvarchar(max)    not null,
    square_footage       nvarchar(200)    not null,
    room_layout          nvarchar(200)    not null,
    total_floor          nvarchar(200)    not null,
    room_description     nvarchar(200)    not null,
    style                nvarchar(200)    not null,
    feature              nvarchar(200)    not null,
    landlord_id          uniqueidentifier not null,
    traffic              nvarchar(200)    not null,
    address              nvarchar(200)    not null,
    near_hospital        nvarchar(200)    not null,
    life_function        nvarchar(200)    not null,
    planimetric_map      nvarchar(max)    not null,
    full_degree_panorama nvarchar(max)    not null,
    disable    bit              default 0,
    created_at           datetime         not null default CURRENT_TIMESTAMP,
    updated_at           datetime         not null default CURRENT_TIMESTAMP
);

ALTER TABLE houses
    ADD CONSTRAINT house_fk_landlord_id FOREIGN KEY (landlord_id) REFERENCES landlords (id);
CREATE INDEX house_created_at_index ON houses (created_at);