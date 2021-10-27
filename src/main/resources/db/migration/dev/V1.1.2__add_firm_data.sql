INSERT INTO firms (id, name, address, phone,
            contact_people, contact_title, contact_phone, contact_email)

VALUES (CONVERT(uniqueidentifier, 'c39d554a-d407-4b0f-95b9-830ace721fdf'),
        N'信松清潔股份有限公司', N'台北市信義區市府路45號', '02-27491141',
        N'邱雅慧', N'行政組員', '02-27491141#220', 'chao0392@ssccc.com.tw'),

        (CONVERT(uniqueidentifier, '91bf1934-babb-4f6c-863a-469fd2874a4d'),
        N'大葉清潔公司', N'臺中市豐原區市政路4號', '04-25255162',
        N'李賀鴻', N'業務專員', '0934-449-377', 'li99931039288@gmail.com'),

        (CONVERT(uniqueidentifier, '93d6b739-96a7-4398-82b5-0a4c5f883972'),
        N'大鐵匠清潔公司', N'新北市板橋區金門街三段281巷93段2號9樓', '02-26828339',
        N'楊志傑', N'負責人', '0945-595-505', 'tpp0339@hotmail.com'),

        (CONVERT(uniqueidentifier, '3865a3f0-6421-4be4-8f9c-7037387d98b4'),
        N'昌慧地毯清潔', N'新北市三重區重新路四段130號', '0933-493-183',
        N'', N'', '', '')