INSERT INTO landlords(id, role_id,
                  line_id, account, password,
                  chinese_name, english_name,
                  email, birthday, identity_card,
                  gender, address, phone, cellphone,
                  verified,
                  agree_personal_information)

VALUES (CONVERT(uniqueidentifier, '2d715366-fb97-4e85-adc8-210886710b17'),
        CONVERT(uniqueidentifier, 'a066e927-acf1-408b-a711-9dc2dc630e36'),
        'test123',
        'landlord_account', '$2y$10$8Vg6fn3xw.WFPQaQuVX17u6jgw08ucUy69e5XrI8QeAqXlOqUbK8.',
        N'系統房東', 'landlord', 'landlord@example.com', '1997-01-02', 'X123456789', N'男',
        N'台北市', '02-12345678#123', '0912-345-678',
        1 , 1);




