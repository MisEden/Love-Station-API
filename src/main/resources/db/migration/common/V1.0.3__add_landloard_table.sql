CREATE TABLE landlords (
                           id uniqueidentifier primary key default NEWID(),
                           role_id uniqueidentifier not null,
                           line_id nvarchar(200) not null,
                           account nvarchar(200) not null,
                           password                   nvarchar(200) not null,
                           chinese_name               nvarchar(200) not null,
                           english_name               nvarchar(200),
                           email                      nvarchar(200) not null,
                           birthday                   nvarchar(200) not null,
                           identity_card              nvarchar(200) not null,
                           gender                     nvarchar(200) not null,
                           address                    nvarchar(200) not null,
                           phone                      nvarchar(200),
                           cellphone                  nvarchar(200) not null,
                           verified                   BIT,
                           agree_personal_information BIT           not null,
                           created_at                 datetime      not null default CURRENT_TIMESTAMP,
                           updated_at                 datetime      not null default CURRENT_TIMESTAMP,
);

ALTER TABLE landlords
    ADD CONSTRAINT landlord_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id);

CREATE UNIQUE INDEX landlord_account_index ON landlords (account);
--CREATE UNIQUE INDEX landlord_email_index ON landlords (email);
--CREATE UNIQUE INDEX landlord_line_id_index ON landlords (line_id);
CREATE UNIQUE INDEX landlord_identity_card_index ON landlords (identity_card);

