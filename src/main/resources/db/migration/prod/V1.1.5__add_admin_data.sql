INSERT INTO admins(id, role_id,
                   line_id, account, password,
                   name, email, verified)
VALUES (CONVERT(uniqueidentifier, '201c200d-27cc-4a2d-8768-bd2b8853ccba'),
        CONVERT(uniqueidentifier, 'cfbecb52-7f4d-417c-ab6f-b3343eacf4fb'),
        '',
        'admin_account', '$2y$10$/IKAM.S7I3QR34biPspz7uogckYV/sYlQi9z4NUdu1fu0YVJkp.De',
        N'管理者', 'admin.temp@tempmail.com', 1);