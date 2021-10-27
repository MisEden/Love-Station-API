INSERT INTO admins(id, role_id,
                   line_id, account, password,
                   name, email, verified)
VALUES (CONVERT(uniqueidentifier, '201c200d-27cc-4a2d-8768-bd2b8853ccba'),
        CONVERT(uniqueidentifier, 'cfbecb52-7f4d-417c-ab6f-b3343eacf4fb'),
        '',
        'admin_account', '$2y$10$/IKAM.S7I3QR34biPspz7uogckYV/sYlQi9z4NUdu1fu0YVJkp.De',
        N'管理者', 'admin.temp@tempmail.com', 1),

        (CONVERT(uniqueidentifier, '882BF4CA-631F-4E72-968D-AE3F425A68EB'),
         CONVERT(uniqueidentifier, 'CA1ABEFC-A2BE-4713-AC86-C913F698D190'),
         '',
         'admin_read', '$2a$10$yfZBz.WJOyDBGrq8CIUnsukirk4mZnnSn6VEjsD.yK8lddJ4igeXi',
         N'管理小讀', 'admin.temp1@tempmail.com', 1),

         (CONVERT(uniqueidentifier, '68FF8213-A401-42E7-B916-4F46512D2C6A'),
          CONVERT(uniqueidentifier, '9196B03E-5EED-44B1-B656-47EAB377002E'),
          '',
          'admin_write', '$2a$10$yfZBz.WJOyDBGrq8CIUnsukirk4mZnnSn6VEjsD.yK8lddJ4igeXi',
          N'管理小寫', 'admin.temp2@tempmail.com', 1);