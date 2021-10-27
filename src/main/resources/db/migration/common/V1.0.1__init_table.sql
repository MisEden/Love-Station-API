CREATE TABLE roles
(
    id   uniqueidentifier primary key default NEWID(),
    name nvarchar(200) not null
);

CREATE TABLE referrals
(
    id                    uniqueidentifier primary key default NEWID(),
    hospital_chinese_name nvarchar(200) not null,
    hospital_english_name nvarchar(200) not null,
    number                nvarchar(200) not null,
    city                  nvarchar(200) not null
);

CREATE TABLE firms
(
    id                      uniqueidentifier primary key default NEWID(),
    name                    nvarchar(200) not null,
    address                 nvarchar(200) not null default '',
    phone                   nvarchar(200) not null default '',
    contact_people          nvarchar(200) not null default '',
    contact_title           nvarchar(200) not null default '',
    contact_phone           nvarchar(200) not null default '',
    contact_email           nvarchar(200) not null default ''
);

CREATE TABLE referral_titles
(
    id   uniqueidentifier primary key default NEWID(),
    name nvarchar(200) not null
);

CREATE TABLE referral_employees
(
    id                         uniqueidentifier primary key default NEWID(),
    line_id                    nvarchar(200)    not null,
    referral_id                uniqueidentifier not null,
    role_id                    uniqueidentifier not null,
    name                       nvarchar(200)    not null,
    referral_title_id          uniqueidentifier not null,
    work_identity              nvarchar(200)    not null,
    image                      nvarchar(max)    not null,
    account                    nvarchar(200)    not null,
    password                   nvarchar(200)    not null,
    email                      nvarchar(200)    not null,
    gender                     nvarchar(200)    not null,
    address                    nvarchar(200)    not null,
    phone                      nvarchar(200) default '',
    cellphone                  nvarchar(200)    not null default '',
    verified                   BIT,
    agree_personal_information BIT              not null,
    created_at                 datetime         not null default CURRENT_TIMESTAMP,
    updated_at                 datetime         not null default CURRENT_TIMESTAMP,
);

ALTER TABLE referral_employees
    ADD CONSTRAINT referral_employees_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE referral_employees
    ADD CONSTRAINT referral_employees_fk_referral_id FOREIGN KEY (referral_id) REFERENCES referrals (id);
ALTER TABLE referral_employees
    ADD CONSTRAINT referral_employees_fk_referral_title_id FOREIGN KEY (referral_title_id) REFERENCES referral_titles (id);

CREATE UNIQUE INDEX referral_employee_account_index ON referral_employees (account);
--CREATE UNIQUE INDEX referral_employee_email_index ON referral_employees (email);
--CREATE UNIQUE INDEX referral_employee_line_id_index ON referral_employees (line_id);


CREATE TABLE referral_numbers
(
    id                   nvarchar(200) primary key,
    referral_employee_id uniqueidentifier not null,
    identity_card        nvarchar(200)    not null
);

ALTER TABLE referral_numbers
    ADD CONSTRAINT referral_numbers_fk_referral_employee_id FOREIGN KEY (referral_employee_id) REFERENCES referral_employees (id);

CREATE UNIQUE INDEX referral_number_identity_card_index on referral_numbers (identity_card);

CREATE TABLE users
(
    id                         uniqueidentifier primary key default NEWID(),
    referral_number_id         nvarchar(200)    not null,
    role_id                    uniqueidentifier not null,
    line_id                    nvarchar(200)    not null,
    account                    nvarchar(200)    not null,
    password                   nvarchar(200)    not null,
    chinese_name               nvarchar(200)    not null,
    english_name               nvarchar(200),
    email                      nvarchar(200)    not null,
    birthday                   nvarchar(200)    not null,
    blood_type                 nvarchar(200)    not null,
    identity_card              nvarchar(200)    not null,
    gender                     nvarchar(200)    not null,
    address                    nvarchar(200)    not null,
    phone                      nvarchar(200),
    cellphone                  nvarchar(200)    not null,
    verified                   BIT              ,
    agree_personal_information BIT              not null,
    created_at                 datetime         not null    default CURRENT_TIMESTAMP,
    updated_at                 datetime         not null    default CURRENT_TIMESTAMP,
);

ALTER TABLE users
    ADD CONSTRAINT users_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE users
    ADD CONSTRAINT users_fk_referral_number_id FOREIGN KEY (referral_number_id) REFERENCES referral_numbers (id);

CREATE UNIQUE INDEX user_account_index ON users (account);
CREATE UNIQUE INDEX user_identity_card_index ON users (identity_card);
--CREATE UNIQUE INDEX user_email_index ON users (email);
--CREATE UNIQUE INDEX user_line_id_index ON users (line_id);
CREATE INDEX user_created_at_index on users (created_at);
