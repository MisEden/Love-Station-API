CREATE TABLE room_states
(
    id         uniqueidentifier primary key default NEWID(),
    state      nvarchar(200)    not null, -- 空房、預約中(待審核的申請單)、已訂房 --
    start_date datetime         not null,
    end_date   datetime         not null,
    user_id    uniqueidentifier not null,
    room_id    uniqueidentifier not null,
    created_at datetime         not null    default CURRENT_TIMESTAMP,
    updated_at datetime         not null    default CURRENT_TIMESTAMP,
    deleted    bit                          default 0
)

ALTER TABLE room_states
    ADD CONSTRAINT room_state_fk_room_id FOREIGN KEY (room_id) REFERENCES rooms (id);

ALTER TABLE room_states
    ADD CONSTRAINT room_state_fk_user_id FOREIGN KEY (user_id) REFERENCES users (id);

CREATE INDEX room_state_created_at_index ON room_states (created_at);
CREATE INDEX room_state_deleted_index ON room_states (deleted);