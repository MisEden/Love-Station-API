CREATE TABLE public_furniture
(
    id              uniqueidentifier    primary key default NEWID(),
    name            nvarchar(200)       not null,
    precedence      TINYINT             not null,
    created_at      datetime            not null       default CURRENT_TIMESTAMP,
    updated_at      datetime            not null       default CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX public_furniture_name_index ON public_furniture (name);