CREATE TABLE checkin_applications
(
    id                                 uniqueidentifier primary key default NEWID(),
    user_id                            uniqueidentifier not null,

    referral_employee_id               uniqueidentifier not null,
    referral_date                      datetime         not null,

    admin_id                           uniqueidentifier,
    admin_date                         datetime,

    volunteer_id                       uniqueidentifier,
    volunteer_date                     datetime,

    firm_employee_id                   uniqueidentifier,
    firm_employee_date                 datetime,

    house_id                           uniqueidentifier not null,
    room_state_id                      uniqueidentifier not null,
    room_id                            uniqueidentifier not null,

    applicant_in                       BIT              not null,

    blood_type                         nvarchar(200)    not null,
    language                           nvarchar(200)    not null,
    special_medical_history            nvarchar(200)    not null,
    drug_allergy                       nvarchar(200)    not null,
    diagnosed_with                     nvarchar(200)    not null,
    overview_patient_condition         nvarchar(200)    not null,
    medicine                           nvarchar(200)    not null,
    user_identity                      nvarchar(200)    not null,
    self_care_ability                  nvarchar(200)    not null,
    attachment                         nvarchar(200)    not null,
    caregiver_name                     nvarchar(200)    not null,
    caregiver_relationship             nvarchar(200)    not null,
    caregiver_phone                    nvarchar(200)    not null,
    applicant_infectious_disease       nvarchar(200)    not null,
    caregiver_infectious_disease       nvarchar(200)    not null,
    one_emergency_contact_name         nvarchar(200)    not null,
    one_emergency_contact_relationship nvarchar(200)    not null,
    one_emergency_contact_phone        nvarchar(200)    not null,
    two_emergency_contact_name         nvarchar(200)    not null,
    two_emergency_contact_relationship nvarchar(200)    not null,
    two_emergency_contact_phone        nvarchar(200)    not null,
    application_reason                 nvarchar(200)    not null,
    first_verified                     BIT,
    first_verified_reason              nvarchar(200),
    second_verified                    BIT,
    rent_image                         nvarchar(max),
    affidavit_image                    nvarchar(max),

    checkin_notification_date          datetime,
    checkout_notification_date         datetime,
    created_at                         datetime         not null default CURRENT_TIMESTAMP,
    updated_at                         datetime         not null default CURRENT_TIMESTAMP
)

ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_referral_employee_id FOREIGN KEY (referral_employee_id) REFERENCES referral_employees (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_admin_id FOREIGN KEY (admin_id) REFERENCES admins (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_room_state_id FOREIGN KEY (room_state_id) REFERENCES room_states (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_room_id FOREIGN KEY (room_id) REFERENCES rooms (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_house_id FOREIGN KEY (house_id) REFERENCES houses (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_volunteer_id FOREIGN KEY (volunteer_id) REFERENCES volunteers (id);
ALTER TABLE checkin_applications
    ADD CONSTRAINT checkin_applications_fk_firm_employee_id FOREIGN KEY (firm_employee_id) REFERENCES firm_employees (id);


CREATE INDEX checkin_application_created_at_index ON checkin_applications (created_at);
CREATE INDEX checkin_application_first_verified_index ON checkin_applications (first_verified);
CREATE INDEX checkin_application_second_verified_index ON checkin_applications (second_verified);
