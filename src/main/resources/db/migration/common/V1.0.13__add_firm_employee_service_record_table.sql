CREATE TABLE firm_employee_service_record
(
    id                              uniqueidentifier primary key default NEWID(),
    checkin_application_id          uniqueidentifier not null,
    firm_employee_id                uniqueidentifier not null,
    house_id                        uniqueidentifier not null,
    room_id                         uniqueidentifier not null,
    service                         nvarchar(255),
    remark                          nvarchar(255),
    before_image                    nvarchar(255),
    after_image                     nvarchar(255),
    viewed                          nvarchar(255) not null default N'未檢視',

    created_at    datetime         not null    default CURRENT_TIMESTAMP,
    updated_at    datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE firm_employee_service_record
    ADD CONSTRAINT firm_employee_service_record_fk_checkin_application_id FOREIGN KEY (checkin_application_id) REFERENCES checkin_applications (id);
ALTER TABLE firm_employee_service_record
    ADD CONSTRAINT firm_employee_service_record_fk_firm_employee_id FOREIGN KEY (firm_employee_id) REFERENCES firm_employees (id);
ALTER TABLE firm_employee_service_record
    ADD CONSTRAINT firm_employee_service_record_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);
ALTER TABLE firm_employee_service_record
    ADD CONSTRAINT firm_employee_service_record_fk_room_id FOREIGN KEY (room_id) REFERENCES rooms (id);


