INSERT INTO room_state_changes (id,
                               room_state_id,
                               new_start_date,
                               new_end_date,
                               changed_item,
                               reason,
                               referral_verified,
                               admin_verified)

VALUES (CONVERT(uniqueidentifier, '52041937-8541-4402-bb93-e2cc704d55b5'),
        CONVERT(uniqueidentifier, '24F93F6D-05B2-428A-B578-8431D4BBE372'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        N'延後入住',
        N'買不到車票',
        0,
        0)
