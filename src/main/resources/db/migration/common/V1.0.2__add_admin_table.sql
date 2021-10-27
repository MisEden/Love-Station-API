CREATE TABLE admins
(
    id         uniqueidentifier primary key default NEWID(),
    line_id    nvarchar(200),
    role_id    uniqueidentifier not null,
    name       nvarchar(200)    not null,
    account    nvarchar(200)    not null,
    password   nvarchar(200)    not null,
    email      nvarchar(200)    not null,
    verified                   BIT,
    created_at datetime         not null    default CURRENT_TIMESTAMP,
    updated_at datetime         not null    default CURRENT_TIMESTAMP,
);

ALTER TABLE admins
    ADD CONSTRAINT admin_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id);

CREATE UNIQUE INDEX admin_account_index ON admins (account);
CREATE UNIQUE INDEX admin_email_index ON admins (email);