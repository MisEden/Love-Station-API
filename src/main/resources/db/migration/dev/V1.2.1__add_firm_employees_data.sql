INSERT INTO firm_employees (id, role_id,
                  line_id, firm_id, account, password,
                  chinese_name, english_name,
                  email, birthday, identity_card,
                  gender, address, phone, cellphone,
                  verified,
                  agree_personal_information)

VALUES (CONVERT(uniqueidentifier, '405135d9-bfc0-4a74-b5c7-a2343d99e4ce'),
        CONVERT(uniqueidentifier, 'fb52636f-519d-42fd-b544-50e5364cbd42'),
        'line_id',
        CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        'firm_employee_account', '$2y$10$ogZkhZXzePMBhO4nunAWNOm7CW/Vz31eXj7Mui8n2Y1kbxJ8P4YdW',
        N'廠商', 'firm', 'firm@example.com', '108/08/02', 'X123456789', N'男',
        N'台北市', '02-12345678#123', '0912-345-678',
        null , 1),

       (CONVERT(uniqueidentifier, 'daebb557-8569-45a1-a5b2-69d1f5657a2c'),
        CONVERT(uniqueidentifier, 'fb52636f-519d-42fd-b544-50e5364cbd42'),
        'U56df696313ac84bb6d357e9e1ed6b725',
        CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        'firm_employee_wind', '$2a$10$yfZBz.WJOyDBGrq8CIUnsukirk4mZnnSn6VEjsD.yK8lddJ4igeXi',
        N'廠商小峰', 'wind', 'apollo0975@hotmail.com', '108/08/02', 'H123456789', N'男',
        N'桃園市', '02-12345678', '0912-345-678',
        1, 1),

       (CONVERT(uniqueidentifier, '4c5efe3d-f37f-4686-9971-cb31435bb3ba'),
        CONVERT(uniqueidentifier, 'fb52636f-519d-42fd-b544-50e5364cbd42'),
        'U952553d5c67f02ff49952eff4e5a9fc4',
        CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        'firm_employee_wang', '$2a$10$AA6JZuYW24xxdag3wD2yHu/u0iYwIZ7CwGenJhpJ6CBBf8av4ogdO',
        N'廠商小汪', 'wang', 'm10909117@mail.ntust.edu.tw', '108/08/02', 'F123456789', N'男',
        N'新北市', '02-12345678', '0912-345-678',
        1, 1),

       (CONVERT(uniqueidentifier, '39419d2d-db04-44e3-8829-5939fbe1cad1'),
        CONVERT(uniqueidentifier, 'fb52636f-519d-42fd-b544-50e5364cbd42'),
        'U87f0de3d0487a9173fefdb44db6168f3',
        CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        'firm_employee_chao', '$2a$10$AA6JZuYW24xxdag3wD2yHu/u0iYwIZ7CwGenJhpJ6CBBf8av4ogdO',
        N'廠商小趙', 'chao', 'm10909115@mail.ntust.edu.tw', '108/08/02', 'F129282171', N'男',
        N'新竹市', '02-12345678', '0912-345-678',
        1, 1),

       (CONVERT(uniqueidentifier, '60fb62e4-0dbc-4593-ba6f-4afd87b544ae'),
        CONVERT(uniqueidentifier, 'fb52636f-519d-42fd-b544-50e5364cbd42'),
        'line_id_ru',
        CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        'firm_employee_ru', '$2a$10$8dyi/lHUXqTC1ISLrKdbG./gKoDqHN41TEHuV2LnuttqQRH/iJLY2',
        N'廠商小茹', 'ru', 'm10809101@mail.ntust.edu.tw', '108/08/02', 'Z123456789', N'女',
        N'台北市', '02-12345678', '0912-345-678',
        1, 1);





