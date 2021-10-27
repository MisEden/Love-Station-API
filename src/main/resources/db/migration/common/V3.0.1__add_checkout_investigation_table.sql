CREATE TABLE investigation
(
    id                                  uniqueidentifier primary key default NEWID(),
    room_state_id                       uniqueidentifier,
    service_efficiency                  TINYINT not null,
    service_attitude                    TINYINT not null,
    service_quality                     TINYINT not null,
    equipment_furniture                 TINYINT not null,
    equipment_electric_device           TINYINT not null,
    equipment_assistive                 TINYINT not null,
    equipment_bedding                   TINYINT not null,
    equipment_barrier_free_environment  TINYINT not null,
    environment_clean                   TINYINT not null,
    environment_comfort                 TINYINT not null,
    safety_firefighting                 TINYINT not null,
    safety_secom_emergency_system       TINYINT not null,

    created_at    datetime         not null    default CURRENT_TIMESTAMP,
    updated_at    datetime         not null    default CURRENT_TIMESTAMP
)

ALTER TABLE investigation
    ADD CONSTRAINT investigation_fk_room_state_id FOREIGN KEY (room_state_id) REFERENCES room_states (id);