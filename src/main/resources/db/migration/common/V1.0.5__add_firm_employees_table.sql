CREATE TABLE firm_employees (
                       id uniqueidentifier primary key default NEWID(),
                       line_id nvarchar(200) not null,
                       role_id uniqueidentifier not null,
                       firm_id uniqueidentifier not null,
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

ALTER TABLE firm_employees
    ADD CONSTRAINT firm_employees_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE firm_employees
    ADD CONSTRAINT firm_employees_fk_firm_id FOREIGN KEY (firm_id) REFERENCES firms (id);

CREATE UNIQUE INDEX firm_employees_account_index ON firm_employees (account);
--CREATE UNIQUE INDEX firm_employees_email_index ON firm_employees (email);
--CREATE UNIQUE INDEX firm_employees_line_id_index ON firm_employees (line_id);
CREATE UNIQUE INDEX firm_employees_identity_card_index ON firm_employees (identity_card);