CREATE TABLE room_state_changes
(
    id                uniqueidentifier primary key default NEWID(),

    room_state_id     uniqueidentifier not null,

    new_start_date    datetime         not null,
    new_end_date      datetime         not null,
    changed_item      nvarchar(200),   -- 取消入住/延後入住/延後退房/提前退房 --
    reason            nvarchar(200),
    denied_reason     nvarchar(200),

    referral_verified BIT,
    admin_verified    BIT,

    created_at        datetime         not null    default CURRENT_TIMESTAMP,
    updated_at        datetime         not null    default CURRENT_TIMESTAMP,
)

ALTER TABLE room_state_changes
    ADD CONSTRAINT room_state_changes_fk_room_state_id FOREIGN KEY (room_state_id) REFERENCES room_states (id);
