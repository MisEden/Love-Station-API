CREATE TABLE private_furniture
(
    id          uniqueidentifier    primary key default NEWID(),
    name        nvarchar(200)       not null,
    precedence  TINYINT             not null,
    created_at  datetime            not null       default CURRENT_TIMESTAMP,
    updated_at  datetime            not null       default CURRENT_TIMESTAMP
);
