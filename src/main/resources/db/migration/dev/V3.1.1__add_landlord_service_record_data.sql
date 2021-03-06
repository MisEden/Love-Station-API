INSERT INTO landlord_service_record (id,
                                     checkin_application_id,
                                     landlord_id,
                                     house_id,
                                     room_id,
                                     service,
                                     remark)

VALUES (CONVERT(uniqueidentifier, '6c1a8d89-1a6f-47c9-9563-1be9bb68a480'),
        CONVERT(uniqueidentifier, 'A472853E-3B1E-42D2-8C50-A299BF1ABB70'),
        CONVERT(uniqueidentifier, 'D941F8D9-503F-4BDE-802B-948E5DA51501'),
        CONVERT(uniqueidentifier, '0706A3BC-4470-4D21-A118-212C7BBEB3EB'),
        CONVERT(uniqueidentifier, '5B586F4D-85AA-4B7E-9EDB-271090ECB4AA'),
        N'帶入房',
        N'無'),

       (CONVERT(uniqueidentifier, '9ee330c9-9c17-41da-bac0-7b133ac72ca7'),
        CONVERT(uniqueidentifier, 'EE4B246A-132D-43AD-A460-D217A09C01AF'),
        CONVERT(uniqueidentifier, 'D941F8D9-503F-4BDE-802B-948E5DA51501'),
        CONVERT(uniqueidentifier, '0706A3BC-4470-4D21-A118-212C7BBEB3EB'),
        CONVERT(uniqueidentifier, '4C691C7C-3B85-4D31-B70A-BDD8519C9AC4'),
        N'送餐',
        N'協助買飯'),

        (CONVERT(uniqueidentifier, 'B46529F0-C453-46E6-AFA9-C803D57BBE4F'),
         CONVERT(uniqueidentifier, '5A07D42E-935F-47A1-A31F-EB4CE275C5C3'),
         CONVERT(uniqueidentifier, 'D941F8D9-503F-4BDE-802B-948E5DA51501'),
         CONVERT(uniqueidentifier, '76524EE9-4B82-4D9A-AB03-4EAEDA3116B4'),
         CONVERT(uniqueidentifier, 'B02C12CF-53A2-4948-921E-9DE1234066B0'),
         N'更換家電',
         N'電風扇兩台');
