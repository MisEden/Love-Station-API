INSERT INTO houses(id,serial, city, name, introduction, square_footage, room_layout, total_floor, style, feature, room_description,
                   landlord_id,
                   traffic, address, near_hospital, life_function, planimetric_map, full_degree_panorama)

VALUES (CONVERT(uniqueidentifier, '0ec6472c-eca6-416b-ab11-0c88b8973a97'), 3, N'08.台中市',
        N'台中雙十棧', N'(製作中，單位後續再補充)', N'37',
        N'3房2廳2衛', N'8', N'電梯大樓', N'無障礙衛浴,電動床,洗澡椅,備有緊急救援通報系統', N'3人房1間、2人房1間',
        CONVERT(uniqueidentifier, '2d715366-fb97-4e85-adc8-210886710b17'),
        N'位於一中商圈旁，近中國醫藥大學附設醫院、中山醫學大學附設醫院、台中公園。',
        N'台中市北區雙十路一段', N'中國醫藥大學附設醫院、中山醫學大學附設醫院、台中榮總',
        N'超商、一中商圈、台中公園',
        N'/storage/house-panimetric-map/taichung.jpg', N'系統建置中'),

       (CONVERT(uniqueidentifier, '0706a3bc-4470-4d21-a118-212c7bbeb3eb'), 1, N'02.台北市',
        N'台北南海棧', N'伊甸長期在社會服務的崗位上，以實際行動支持弱勢居住的權益，抱持哪裡有需要，哪裡就有伊甸的精神，整合內外部資源的連結與運用，提供多元化居住服務。102年間居住台北市重殘獨居的郭老先生，將其南海路房舍捐贈予伊甸基金會，由伊甸負責照顧郭老先生後續的生活直至終老，開啟國內首創的「附負擔捐贈以房養老」實驗案。103年至今，伊甸陸續啟用台北南海、新北淡水、高雄鹽埕、台中雙十等四個棧點，幫助248個遠地就醫家庭，服務超過760人次，提供6795天的住宿服務，幫助病友省下約543萬元的住宿費用。隨著愛心棧服務啟動，住宿需求也逐年增加。特別位於台北市士林、北投區一帶的台北榮民總醫院、振興醫院、陽明醫院、新光醫院等，更是許多來自外縣市或離島的病友就醫轉診時的首選。',
        N'27',
        N'2房2廳1衛', N'2', N'電梯大廈', N'無障礙衛浴,電動床,房內備有緊急救援通報系統', N'3人房1間、2人房1間',
        CONVERT(uniqueidentifier, '2d715366-fb97-4e85-adc8-210886710b17'),
        N'中正紀念堂捷運站、三元街口公車站',
        N'台北市中正區南海路', N'臺大醫院、臺大兒童醫院、台北馬偕醫院、三總汀州院區、台北榮總、振興醫院',
        N'超商、超市、南機場夜市、台北植物園、國立歷史博物館等',
        N'/storage/house-panimetric-map/taipei.jpg', N'系統建置中'),

       (CONVERT(uniqueidentifier, '1b422fcf-3ed4-4c3a-b150-2757dcd2ab28'), 2, N'03.新北市',
        N'新北淡水棧', N'(製作中，單位後續再補充)',
        N'7',
        N'2房0廳2衛', N'8', N'社區式獨立套房', N'無障礙衛浴,電動床,房內備有緊急救援通報系統,社區內有投幣式自助洗烘衣機,投幣式加水站', N'2人房2間',
        CONVERT(uniqueidentifier, '2d715366-fb97-4e85-adc8-210886710b17'),
        N'淡海輕軌淡金北新站、捷運紅樹林站、北新路口公車站',
        N'新北市淡水區淡金路一段', N'和信醫院、淡水馬偕醫院、台北榮總、振興醫院',
        N'超商、超市、大賣場、淡江大學生活圈',
        N'/storage/house-panimetric-map/newtaipei.jpg', N'系統建置中'),

        (CONVERT(uniqueidentifier, '76524ee9-4b82-4d9a-ab03-4eaeda3116b4'), 4, N'15.高雄市',
        N'高雄鹽埕棧', N'(製作中，單位後續再補充)',
        N'7',
        N'2房0廳2衛', N'5', N'大樓式獨立套房', N'無障礙衛浴,電動床,房內備有緊急救援通報系統', N'兩間獨立套房',
        CONVERT(uniqueidentifier, '2d715366-fb97-4e85-adc8-210886710b17'),
        N'鹽埕埔捷運站',
        N'高雄市鹽埕區北斗街', N'高雄醫學大學附設醫院、高雄長庚醫院、市立大同醫院、阮綜合醫院',
        N'超商、超市、大賣場、愛河風景區、駁二藝術特區',
        N'/storage/house-panimetric-map/kohsiung.jpg', N'/storage/house-full-degree-panorama/kaohsiung_01.jpg')


