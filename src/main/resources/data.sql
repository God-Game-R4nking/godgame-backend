-- 테이블이 존재하지 않는 경우에만 생성
CREATE TABLE IF NOT EXISTS music (
    music_id INT PRIMARY KEY,
    title VARCHAR(255),
    singer VARCHAR(255),
    music_link VARCHAR(255),
    era VARCHAR(50)
);

--Music 데이터 삽입
INSERT IGNORE INTO music (music_id, title, singer, music_link, era) VALUES
(1, '내사랑 내곁에', '김현식', 'https://www.youtube.com/watch?v=iJ6ThgYyhSs&t=66s', '1990'),
(2, '너를 사랑해', '한동준', 'https://www.youtube.com/watch?v=X_QNm7U1CUQ&t=53s', '1990'),
(3, '다시만난 너에게', '피노키오', 'https://www.youtube.com/watch?v=JV0vbhZu1EM&t=72s', '1990'),
(4, '나의 노래', '김광석', 'https://www.youtube.com/watch?v=z8w-9luYuR0&t=74s', '1990'),
(5, '아주 오래된 연인들', '015B', 'https://www.youtube.com/watch?v=S_S0jKvsP50&t=119s', '1990'),
(6, '이별의 그늘', '윤상', 'https://www.youtube.com/watch?v=Eg5ZiW66hoo&t=91s', '1990'),
(7, '10년전의 일기를 꺼내어', '봄여름가을겨울', 'https://www.youtube.com/watch?v=Kj1Hx5YDm0Y&t=106s', '1990'),
(8, 'Poison', '엄정화', 'https://www.youtube.com/watch?v=SOTV9wfow6Q&t=65s', '1990'),
(9, '내가 만일', '안치환', 'https://www.youtube.com/watch?v=DCxnYULJvbY&t=84s', '1990'),
(10, '도시인', '넥스트', 'https://www.youtube.com/watch?v=nrxUVaqkH_w&t=120s', '1990'),
(11, '애모', '김수희', 'https://www.youtube.com/watch?v=aIL1pgRwZbs&t=82s', '1990'),
(12, '너를 향한 마음', '이승환', 'https://www.youtube.com/watch?v=26lw0_Z8oAA&t=89s', '1990'),
(13, '이 밤이 지나도록', '푸른하늘', 'https://www.youtube.com/watch?v=sXayYIuGjBM&t=80s', '1990'),
(14, '희망사항', '변진섭', 'https://www.youtube.com/watch?v=s7deRrLIlME&t=24s', '1990'),
(15, '넋두리', '김현식', 'https://www.youtube.com/watch?v=mG5rXBpFGnc&t=116s', '1990'),
(16, '핑계', '김건모', 'https://www.youtube.com/watch?v=n7-y0X5reVc&t=38s', '1990'),
(17, '신 인류의 사랑', '015B', 'https://www.youtube.com/watch?v=GGLeqkNOeoI&t=49s', '1990'),
(18, '사랑을 위하여', '김종환', 'https://www.youtube.com/watch?v=9d9_BF0RUe0&t=54s', '1990'),
(19, '너에게 들려주고 싶은 이야기', '015B', 'https://www.youtube.com/watch?v=NZ2jDx5oSTA&t=130s', '1990'),
(20, '사랑일뿐야', '김민우', 'https://www.youtube.com/watch?v=ObXOb6Oo3fQ&t=85s', '1990'),
(21, '유리창엔 비', '햇빛촌', 'https://www.youtube.com/watch?v=DbTNzShDY9k&t=53s', '1990'),
(22, '달팽이', '패닉', 'https://www.youtube.com/watch?v=2VT7ZLprnBM&t=76s', '1990'),
(23, '시청 앞 지하철 역에서', '동물원', 'https://www.youtube.com/watch?v=9j5NRG-8XzU&t=75s', '1990'),
(24, '잘못된 만남', '김건모', 'https://www.youtube.com/watch?v=kXXFlw5n6LE&t=68s', '1990'),
(25, '꿈에서 본 거리', '푸른하늘', 'https://www.youtube.com/watch?v=cGw8uXNJhyg&t=132s', '1990'),
(26, '사랑할수록', '부활', 'https://www.youtube.com/watch?v=z3qLUZ6r2e8&t=81s', '1990'),
(27, '달의 몰락', '김현철', 'https://www.youtube.com/watch?v=nCL05mGSoQY&t=67s', '1990'),
(28, '기억의 습작', '전람회', 'https://www.youtube.com/watch?v=xcyrGn81NlM&t=131s', '1990'),
(29, '미소 속에 비친 그대', '신승훈', 'https://www.youtube.com/watch?v=kPJCwPtj-18&t=13s', '1990'),
(30, '나를 슬프게 하는 사람들', '김경호', 'https://www.youtube.com/watch?v=9MyrrNeTC1w&t=77s', '1990'),
(31, '또 다른 만남을 위해', '김민종', 'https://www.youtube.com/watch?v=iLPBYM8HCug&t=82s', '1990'),
(32, '그 후로 오랫동안', '신승훈', 'https://www.youtube.com/watch?v=hQRdatyDEBI&t=117s', '1990'),
(33, '당신은 어디 있나요', '양수경', 'https://www.youtube.com/watch?v=POF6nsoHBrk&t=67s', '1990'),
(34, '비오는 날 수채화', '김현식, 강인원, 권인하', 'https://www.youtube.com/watch?v=_j7QH5s3T04&t=58s', '1990'),
(35, '언제나 그 자리에', '신효범', 'https://www.youtube.com/watch?v=TCq62VcwsJw&t=83s', '1990'),
(36, '사랑을 할꺼야', '녹색지대', 'https://www.youtube.com/watch?v=CYwbHk55WPo&t=111s', '1990'),
(37, '오래전 그날', '윤종신', 'https://www.youtube.com/watch?v=UV2egmvSC3Y&t=106s', '1990'),
(38, '마지막 콘서트', '이승철', 'https://www.youtube.com/watch?v=g_yISzFoX1s&t=84s', '1990'),
(39, '김성호의 회상', '김성호', 'https://www.youtube.com/watch?v=HyLgtjX_J0s&t=63s', '1990'),
(40, '보고싶은 얼굴', '민해경', 'https://www.youtube.com/watch?v=J1xod3dlJaU&t=62s', '1990'),
(41, '넌 할 수 있어', '강산에', 'https://www.youtube.com/watch?v=39PyGezb61A&t=46s', '1990'),
(42, '그대품에 잠들었으면', '박정수', 'https://www.youtube.com/watch?v=VtCkejhdR9Q&t=89s', '1990'),
(43, '방황', '이승철', 'https://www.youtube.com/watch?v=O9-biTIbGmM&t=82s', '1990'),
(44, '너를 사랑하고도', '전유나', 'https://www.youtube.com/watch?v=jutfKMvcmKQ&t=109s', '1990'),
(45, '너의 결혼식', '윤종신', 'https://www.youtube.com/watch?v=KKuK0IPzyjc&t=110s', '1990'),
(46, '이 밤의 끝을 잡고', '솔리드', 'https://www.youtube.com/watch?v=j9VrNu94yno&t=103s', '1990'),
(47, '마법의 성', '더 클래식', 'https://www.youtube.com/watch?v=gGLwpLSL7uA&t=111s', '1990'),
(48, '먼지가 되어', '이윤수', 'https://www.youtube.com/watch?v=DfKjSwTMS6I&t=53s', '1990'),
(49, '그녀를 만나는 곳 100M 전', '이상우', 'https://www.youtube.com/watch?v=mETSLNQr3uw&t=61s', '1990'),
(50, '난 행복해', '이소라', 'https://www.youtube.com/watch?v=XwYfa51CnqI&t=122s', '1990'),
(51, '오늘 같은 밤이면', '박정운', 'https://www.youtube.com/watch?v=1fiGVZAHJXE&t=115s', '1990'),
(52, '보이지 않는 사랑', '신승훈', 'https://www.youtube.com/watch?v=eAnTsZgzW44&t=70s', '1990'),
(53, '그대안의 블루', '김현철, 이소라', 'https://www.youtube.com/watch?v=33FKnl23xko&t=55s', '1990'),
(54, '내게', '이승환', 'https://www.youtube.com/watch?v=Hk_fjGJXmvc&t=161s', '1990'),
(55, '난 알아요', '서태지와 아이들', 'https://www.youtube.com/watch?v=8J3m-ao43Bs&t=42s', '1990'),
(56, '널 사랑하니까', '신승훈', 'https://www.youtube.com/watch?v=TM4SxRDpKok&t=89s', '1990'),
(57, '먼 훗날', '박정운', 'https://www.youtube.com/watch?v=AbrkeyH-Z04&t=72s', '1990'),
(58, '슬픈 인연', '015B', 'https://www.youtube.com/watch?v=OflzrBPOkX0&t=99s', '1990'),
(59, '이별 아닌 이별', '이범학', 'https://www.youtube.com/watch?v=8yDC7DfcDF8&t=70s', '1990'),
(60, '가려진 시간 사이로', '윤상', 'https://www.youtube.com/watch?v=a-2j9vIKviE&t=74s', '1990'),
(61, '살다보면', '권진원', 'https://www.youtube.com/watch?v=VOCblhLRz8Q&t=41s', '1990'),
(62, '너에게 원한건', '노이즈', 'https://www.youtube.com/watch?v=4QtW0os17ok&t=56s', '1990'),
(63, '내가 아는 한가지', '이덕진', 'https://www.youtube.com/watch?v=L-QBh1UGV9A&t=83s', '1990'),
(64, 'Dreams Come True', 'S.E.S', 'https://www.youtube.com/watch?v=JR_FQP4ypuM&t=35s', '1990'),
(65, '그 아픔까지 사랑한거야', '조정현', 'https://www.youtube.com/watch?v=HFjbz9CbjWQ&t=93s', '1990'),
(66, '흩어진 나날들', '강수지', 'https://www.youtube.com/watch?v=ObM-_f99ZFo&t=79s', '1990'),
(67, '자아도취', '푸른하늘', 'https://www.youtube.com/watch?v=R_Fjws3kzqE&t=64s', '1990'),
(68, '나와 같다면', '김장훈', 'https://www.youtube.com/watch?v=9eqTPtCU-tA&t=144s', '1990'),
(69, '삐에로는 우릴 보고 웃지', '김완선', 'https://www.youtube.com/watch?v=09wueTgaoDg&t=81s', '1990'),
(70, '당신', '김정수', 'https://www.youtube.com/watch?v=F4uS8P762S4&t=185s', '1990'),
(71, '늦지않았음을', '송재호', 'https://www.youtube.com/watch?v=BRIeNCRhahI&t=113s', '1990'),
(72, '슬프도록 아름다운', 'K2 김성면', 'https://www.youtube.com/watch?v=2GPKjj_MKvM&t=112s', '1990'),
(73, '이별의 끝은 어디인가요', '양수경', 'https://www.youtube.com/watch?v=o6vFslltAhQ&t=108s', '1990'),
(74, '슬픈 표정 하지 말아요', '신해철', 'https://www.youtube.com/watch?v=g2HmkLLa5Fs&t=74s', '1990'),
(75, '대답 없는 너', '김종서', 'https://www.youtube.com/watch?v=93eJbUSLnHM&t=94s', '1990'),
(76, '내게로', '장혜진', 'https://www.youtube.com/watch?v=xD60DeKsMlw&t=47s', '1990'),
(77, '어떤이의 꿈', '봄여름가을겨울', 'https://www.youtube.com/watch?v=nENaIwxFOb8&t=87s', '1990'),
(78, '널 사랑하겠어', '동물원', 'https://www.youtube.com/watch?v=O-lBrUmPES0&t=57s', '1990'),
(79, '하룻밤의 꿈', '이상우', 'https://www.youtube.com/watch?v=I8rsGLA-oTo&t=90s', '1990'),
(80, '난 널 사랑해', '신효범', 'https://www.youtube.com/watch?v=DdGWd9DJiBw&t=63s', '1990'),
(81, '꿈', '이현우', 'https://www.youtube.com/watch?v=ByFSOy1s9LE&t=70s', '1990'),
(82, '칵테일 사랑', '마로니에', 'https://www.youtube.com/watch?v=CrYRAx4MWdc&t=87s', '1990'),
(83, '애송이의 사랑', '양파', 'https://www.youtube.com/watch?v=SLzzSM9CXG8&t=34s', '1990'),
(84, '입영열차안에서', '김민우', 'https://www.youtube.com/watch?v=lshFkigoxlE&t=80s', '1990'),
(85, '난 멈추지 않는다', '잼', 'https://www.youtube.com/watch?v=2X7TNUR_y_U&t=18s', '1990'),
(86, '걸어서 하늘까지', '장현철', 'https://www.youtube.com/watch?v=2LV4yclgVHs&t=29s', '1990'),
(87, '립스틱 짙게 바르고', '임주리', 'https://www.youtube.com/watch?v=UHCDbNpi9Xg&t=114s', '1990'),
(88, '외롭지만 혼자 걸을 수 있어', '봄여름가을겨울', 'https://www.youtube.com/watch?v=_NNM68K8FoE&t=82s', '1990'),
(89, '너를 처음만난 그때', '박준하', 'https://www.youtube.com/watch?v=f9cQ38JAk5E&t=87s', '1990'),
(90, '한사람을 위한 마음', '이오공감', 'https://www.youtube.com/watch?v=DokABcA8Iy8&t=114s', '1990'),
(91, '영원', '조관우', 'https://www.youtube.com/watch?v=IkCzwQU7IcI&t=82s', '1990'),
(92, '그녀와의 이별', '김현정', 'https://www.youtube.com/watch?v=SuK07VjiOm4&t=74s', '1990'),
(93, '그래도 이제는', '김종서', 'https://youtube.com/watch?v=rw6B7Ql-kew&t=118s', '1990'),
(94, '영원히 내게', '안상수', 'https://www.youtube.com/watch?v=mTw92yxhlUU&t=92s', '1990'),
(95, '그대 슬픔까지 사랑해', '심신', 'https://www.youtube.com/watch?v=r_WSczFV-78&t=97s', '1990'),
(96, '내가 너를 느끼듯이', '손지창', 'https://www.youtube.com/watch?v=-KfaawKdKJs&t=113s', '1990'),
(97, '추어그이 책장을 넘기면', '이선희', 'https://www.youtube.com/watch?v=1hq2iuEstm0&t=89s', '1990'),
(98, '사계', '노래를 찾는 사람들', 'https://www.youtube.com/watch?v=8mTefWWXR1M&t=4s', '1990'),
(99, '사랑의 서약', '한동준', 'https://www.youtube.com/watch?v=IFcJVapBW7g&t=79s', '1990'),
(100, '어떤 이별', '변진섭', 'https://www.youtube.com/watch?v=xsiAmfRMVRg&t=65s', '1990'),
(101, 'Gee', '소녀시대', 'https://www.youtube.com/watch?v=U7mPqycQ0tQ&t=79s', '2000'),
(102, '점점', '브라운 아이즈', 'https://www.youtube.com/watch?v=X_sIOs-V3PY&t=136s', '2000'),
(103, '사랑했나봐', '윤도현', 'https://www.youtube.com/watch?v=NOPIFkU_NOk&t=64s', '2000'),
(104, '마지막 인사', '빅뱅', 'https://www.youtube.com/watch?v=PQ3gphHww1Q&t=86s', '2000'),
(105, '사랑의 時(시)', '엠씨더맥스', 'https://www.youtube.com/watch?v=yTa8T9kTce0&t=74s', '2000'),
(106, '벌써 일년', '브라운 아이즈', 'https://www.youtube.com/watch?v=LZlIqfMn4cc&t=125s', '2000'),
(107, 'No.1', '보아', 'https://www.youtube.com/watch?v=ceZc-5p3g1w&t=41s', '2000'),
(108, '하루 하루', '빅뱅', 'https://www.youtube.com/watch?v=8OAQ6RuYFGE&t=87s', '2000'),
(109, 'I Love You', '포지션', 'https://www.youtube.com/watch?v=8o4imlaBbko&t=200s', '2000'),
(110, 'So Hot', '원더걸스', 'https://www.youtube.com/watch?v=lmun5PO54VE&t=56s', '2000'),
(111, '거짓말', 'god', 'https://www.youtube.com/watch?v=I3FHA_qASro&t=100s', '2000'),
(112, 'Tell me', '원더걸스', 'https://www.youtube.com/watch?v=BlHv3BbBv6A&t=70s', '2000'),
(113, '아시나요', '조성모', 'https://www.youtube.com/watch?v=niiZThi8Eog&t=262s', '2000'),
(114, '우린 제법 잘 어울려요', '성시경', 'https://www.youtube.com/watch?v=t3RXFXPpYhc&t=71s', '2000'),
(115, '거짓말', '빅뱅', 'https://www.youtube.com/watch?v=2Cv3phvP8Ro&t=86s', '2000'),
(116, 'Never Ending Story', '부활', 'https://www.youtube.com/watch?v=6fc6gqM6XLA&t=73s', '2000'),
(117, 'With Me', '휘성', 'https://www.youtube.com/watch?v=Cn_fknjlkz4&t=76s', '2000'),
(118, '미안해요', '김건모', 'https://www.youtube.com/watch?v=EmoBlPyQGdM&t=103s', '2000'),
(119, '제자리 걸음', '김종국', 'https://www.youtube.com/watch?v=LsqgtiELbc4&t=52s', '2000'),
(120, '열정', 'SE7EN', 'https://www.youtube.com/watch?v=GsU2oznbDn8&t=63s', '2000'),
(121, '사랑해도 헤어질 수 있다면...', '신승훈', 'https://www.youtube.com/watch?v=fqf8fzkyivQ&t=129s', '2000'),
(122, '사랑앓이', 'FT아일랜드', 'https://www.youtube.com/watch?v=gnLwCb8Cz7I&t=52s', '2000'),
(123, '사랑..그게 뭔데', '양파', 'https://www.youtube.com/watch?v=l-idSBcy4kM&t=100s', '2000'),
(124, '정말 사랑했을까', '브라운 아이드 소울', 'https://www.youtube.com/watch?v=DN32n65kk0A&t=103s', '2000'),
(125, 'Break Away', '빅마마', 'https://www.youtube.com/watch?v=NFqE2NkJYjo&t=95s', '2000'),
(126, '총맞은것처럼', '백지영', 'https://www.youtube.com/watch?v=uSdlduWm4HM&t=58s', '2000'),
(127, '죄와벌', 'SG 워너비', 'https://www.youtube.com/watch?v=yMAzoEHbmzg&t=143s', '2000'),
(128, 'I Don\'t Care', '2NE1', 'https://www.youtube.com/watch?v=4MgAxMO1KD0&t=88s', '2000'),
(129, '미인', '이기찬', 'https://www.youtube.com/watch?v=9Zrz0pEsVAE&t=109s', '2000'),
(130, '내 사람', 'SG 워너비', 'https://www.youtube.com/watch?v=7lT1Wt41gDs&t=179s', '2000'),
(131, '친구여', '조pd', 'https://www.youtube.com/watch?v=qq234KZh3jM&t=26s', '2000'),
(132, 'Nobody', '원더걸스', 'https://www.youtube.com/watch?v=QZBn1e9pr2Q&t=136s', '2000'),
(133, '이럴거면', '아이비', 'https://www.youtube.com/watch?v=hi0zI8xUntk&t=61s', '2000'),
(134, '사랑은...향기를 남기고', '테이', 'https://www.youtube.com/watch?v=lBWVCpA9_R0&t=67s', '2000'),
(135, '또 한번 사랑은 가고', '이기찬', 'https://www.youtube.com/watch?v=PgT2epR0iRc&t=109s', '2000'),
(136, '비몽', '코요태', 'https://www.youtube.com/watch?v=9HhRZwHmpU4&t=65s', '2000'),
(137, '아틀란티스 소녀', '보아', 'https://www.youtube.com/watch?v=skbnuIhVQUA&t=76s', '2000'),
(138, '세글자', '엠투엠', 'https://www.youtube.com/watch?v=LL1qwk3E8rY&t=161s', '2000'),
(139, 'LOVE ALL', 'H7美人', 'https://www.youtube.com/watch?v=y4qzDx_t3ew&t=74s', '2000'),
(140, '하기 힘든 말', '이승기', 'https://www.youtube.com/watch?v=9lMotttjxYg&t=83s', '2000'),
(141, 'Lollipop', '빅뱅, 2NE1', 'https://www.youtube.com/watch?v=zIRW_elc-rY&t=61s', '2000'),
(142, '소원을 말해봐', '소녀시대', 'https://www.youtube.com/watch?v=6SwiSpudKWI&t=69s', '2000'),
(143, 'Fly', '에픽하이', 'https://www.youtube.com/watch?v=cKn18VbnPPc&t=190s', '2000'),
(144, '다시 사랑한다 말할까', '김동률', 'https://www.youtube.com/watch?v=gKVeXCV4Agw&t=133s', '2000'),
(145, '사랑은 가슴이 시킨다', '버즈', 'https://www.youtube.com/watch?v=eQd2OT5MSuU&t=151s', '2000'),
(146, 'Love', '브라운아이드걸스', 'https://www.youtube.com/watch?v=QT4fRKguwO0&t=62s', '2000'),
(147, '초련', '클론', 'https://www.youtube.com/watch?v=5n2-rkDwy0Q&t=37s', '2000'),
(148, '좋은사람', '박효신', 'https://www.youtube.com/watch?v=_6S8FpriFDw&t=50s', '2000'),
(149, '먼곳에서', '박효신', 'https://www.youtube.com/watch?v=ZN678NPqqBU&t=128s', '2000'),
(150, '가시나무', '조성모', 'https://www.youtube.com/watch?v=POu_1kHWNC8&t=80s', '2000'),
(151, '결혼을 할 거라면', '쿨', 'https://www.youtube.com/watch?v=n-5yKlQ3dSU&t=72s', '2000'),
(152, '나의 연인', '임창정', 'https://www.youtube.com/watch?v=uVyVAumtsoc&t=100s', '2000'),
(153, 'Fire', '2NE1', 'https://www.youtube.com/watch?v=49AfuuRbgGo&t=82s', '2000'),
(154, 'Untouchable', 'Big4', 'https://www.youtube.com/watch?v=ghYbMe9X6E0&t=74s', '2000'),
(155, '멍', '김현정', 'https://www.youtube.com/watch?v=zojBIStEPmc&t=90s', '2000'),
(156, '사랑 후에', '신혜성, 린', 'https://www.youtube.com/watch?v=-OFqmZEW2u4&t=118s', '2000'),
(157, '8282', '다비치', 'https://www.youtube.com/watch?v=IwibOy34oAw&t=84s', '2000'),
(158, '사랑은 하나다', '테이', 'https://www.youtube.com/watch?v=8qlEau08xV8&t=81s', '2000'),
(159, '태양을 피하는 방법', '비', 'https://www.youtube.com/watch?v=VGa2_bAHeQ8&t=46s', '2000'),
(160, '10 Minutes', '이효리', 'https://www.youtube.com/watch?v=iKdr44yEBQU&t=17s', '2000'),
(161, '청첩장', '김건모', 'https://www.youtube.com/watch?v=P1JLCrTpDS4&t=191s', '2000'),
(162, '사랑 안해', '백지영', 'https://www.youtube.com/watch?v=jN0uXBwKn8w&t=91s', '2000'),
(163, 'Abracadabra', '브라운아이드걸스', 'https://www.youtube.com/watch?v=ofwFr8o8p0Y&t=48s', '2000'),
(164, '그곳에 서서', '박효신', 'https://www.youtube.com/watch?v=bGctVt-LX3I&t=158s', '2000'),
(165, 'Heartbreaker', 'G-DRAGON', 'https://www.youtube.com/watch?v=LOXEVd-Z7NE&t=47s', '2000'),
(166, '화장을 고치고', '왁스', 'https://www.youtube.com/watch?v=luwdlYsCQ6M&t=180s', '2000'),
(167, '서커스', 'MC몽', 'https://www.youtube.com/watch?v=YdOreUgDH5o&t=42s', '2000'),
(168, '당신은 모르실거야', '핑클', 'https://www.youtube.com/watch?v=YdOreUgDH5o&t=99s', '2000'),
(169, '휠릴리', '이수영', 'https://www.youtube.com/watch?v=9QtogU-UlfU&t=128s', '2000'),
(170, '흔들린 우정', '홍경민', 'https://www.youtube.com/watch?v=SnGpl6AkvSY&t=86s', '2000'),
(171, 'U-Go-Girl', '이효리', 'https://www.youtube.com/watch?v=v8Y3NL1KeNQ&t=82s', '2000'),
(172, '그 남자 그 여자', '바이브', 'https://www.youtube.com/watch?v=N1q3XKfmI5w&t=74s', '2000'),
(173, '길', 'god', 'https://www.youtube.com/watch?v=OFlxQZNWNMU&t=199s', '2000'),
(174, '어쩌다', '윤건', 'https://www.youtube.com/watch?v=j91wzDEOiNM&t=138s', '2000'),
(175, '오 가니', '컨츄리 꼬꼬', 'https://www.youtube.com/watch?v=7lCDLACrLcY&t=112s', '2000'),
(176, 'My Name', '보아', 'https://www.youtube.com/watch?v=mR8i0fRVmSA&t=44s', '2000'),
(177, '그녀가 웃잖아', '김형중', 'https://www.youtube.com/watch?v=7RhnhD95NC8&t=143s', '2000'),
(178, '전설속의 누군가처럼', '신승훈', 'https://www.youtube.com/watch?v=z0PKL5o7big&t=152s', '2000'),
(179, '헤어지지 못하는 여자, 떠나가지 못하는 남자', '리쌍', 'https://www.youtube.com/watch?v=3rYL8AHJaTc&t=99s', '2000'),
(180, '미안해요', '거미', 'https://www.youtube.com/watch?v=ERlf_9BW4iA&t=85s', '2000'),
(181, '불치병', '휘성', 'https://www.youtube.com/watch?v=tVoY5bn2fr8&t=52s', '2000'),
(182, '비행기', '거북이', 'https://www.youtube.com/watch?v=q3outEcc-HE&t=80s', '2000'),
(183, '보고 싶다', '김범수', 'https://www.youtube.com/watch?v=dLm19MP26D4&t=90s', '2000'),
(184, 'Missing You', '플라이 투 더 스카이', 'https://www.youtube.com/watch?v=hE8kr4UuTTQ&t=90s', '2000'),
(185, '보통날', 'god', 'https://www.youtube.com/watch?v=EiBsfvqGiT0&t=45s', '2000'),
(186, '진실', '쿨', 'https://www.youtube.com/watch?v=EERsK-qmdzU&t=114s', '2000'),
(187, 'Goodbye', '이수영', 'https://www.youtube.com/watch?v=R3NaG5tbXWw&t=94s', '2000'),
(188, '유혹의 소나타', '아이비', 'https://www.youtube.com/watch?v=Q_plceCKQX8&t=73s', '2000'),
(189, 'Run To You', 'DJ DOC', 'https://www.youtube.com/watch?v=cENP3aul9lQ&t=64s', '2000'),
(190, '남자를 몰라', '버즈', 'https://www.youtube.com/watch?v=lnoY6MxJGao&t=129s', '2000'),
(191, '야상곡', '김윤아', 'https://www.youtube.com/watch?v=kWFLjnPAhcM&t=115s', '2000'),
(192, '꿈에', '박정현', 'https://www.youtube.com/watch?v=aX-T7gRmsBM&t=140s', '2000'),
(193, '잊을께', '윤도현 밴드', 'https://www.youtube.com/watch?v=b95rNS49yO0&t=72s', '2000'),
(194, '영원', '스카이', 'https://www.youtube.com/watch?v=Y6tkwg2PP3k&t=199s', '2000'),
(195, 'Special Night', '양파', 'https://www.youtube.com/watch?v=gVYFyWzAMmA&t=52s', '2000'),
(196, '아로하', '쿨', 'https://www.youtube.com/watch?v=004x09gOAJI&t=156s', '2000'),
(197, 'Heartbeat', '2 PM', 'https://www.youtube.com/watch?v=bKtvDv7eykg&t=111s', '2000'),
(198, 'One More Time', '쥬얼리', 'https://www.youtube.com/watch?v=rgZ1mG-vw6g&t=70s', '2000'),
(199, '바람만바람만', '김종국, SG 워너비', 'https://www.youtube.com/watch?v=gEZHjx1RM9c&t=156s', '2000'),
(200, '까만안경', '이루, Daylight', 'https://www.youtube.com/watch?v=A9thrRZrA2c&t=136s', '2000'),
(201, '벚꽃 엔딩', '버스커 버스커', 'https://www.youtube.com/watch?v=tXV7dfvSefo&t=81s', '2010'),
(202, '밤편지', '아이유', 'https://www.youtube.com/watch?v=BzYnNdJhZQw&t=106s', '2010'),
(203, '야생화', '박효신', 'https://www.youtube.com/watch?v=OxgiiyLp5pk&t=106s', '2010'),
(204, '봄날', '방탄소년단', 'https://www.youtube.com/watch?v=xEeFrLSkMm8&t=101s', '2010'),
(205, '금요일에 만나요', '아이유', 'https://www.youtube.com/watch?v=EiVmQZwJhsA&t=39s', '2010'),
(206, '첫눈처럼 너에게 가겠다', '에일리', 'https://www.youtube.com/watch?v=gWZos5_TgVI&t=42s', '2010'),
(207, '봄 사랑 벚꽃 말고', 'HIGH4, 아이유', 'https://www.youtube.com/watch?v=ouR4nn1G9r4&t=44s', '2010'),
(208, '모든 날, 모든 순간', '폴킴', 'https://www.youtube.com/watch?v=EXV8TwTo0A0&t=56s', '2010'),
(209, '어디에도', '엠씨더맥스', 'https://www.youtube.com/watch?v=va5rf20Un24&t=83s', '2010'),
(210, '바람기억', '나얼', 'https://www.youtube.com/watch?v=f5ShDNOqq1E&t=124s', '2010'),
(211, '선물', '멜로망스', 'https://www.youtube.com/watch?v=qYYJqWsBb1U&t=62s', '2010'),
(212, '크리스마스니까', '성시경, 박효신, 이석훈, 서인국, 빅스', 'https://www.youtube.com/watch?v=JkRKxxLiDNI&t=63s', '2010'),
(213, '너의 의미', '아이유', 'https://www.youtube.com/watch?v=4L-H_cXSNhQ&t=126s', '2010'),
(214, '좋니', '윤종신', 'https://www.youtube.com/watch?v=b1kQvZhQ6_M&t=68s', '2010'),
(215, '비도 오고 그래서', '헤이즈', 'https://www.youtube.com/watch?v=afxLaQiLu-o&t=66s', '2010'),
(216, 'CHEER UP', '트와이스', 'https://www.youtube.com/watch?v=c7rCyll5AeY&t=68s', '2010'),
(217, '우주를 줄게', '볼빨간사춘기', 'https://www.youtube.com/watch?v=9U8uA702xrE&t=54s', '2010'),
(218, '미리 메리 크리스마스', '아이유', 'https://www.youtube.com/watch?v=WEKLt7XkWVk&t=88s', '2010'),
(219, '또 다시 사랑', '임창정', 'https://www.youtube.com/watch?v=7e54Bvrf3Vs&t=67s', '2010'),
(220, '널 사랑하지 않아', '어반자카파', 'https://www.youtube.com/watch?v=MfYPKZl7W1w&t=64s', '2010'),
(221, '썸', '소유, 정기고', 'https://www.youtube.com/watch?v=Y-FhDScM_2w&t=55s', '2010'),
(222, '사랑을 했다', 'iKON', 'https://www.youtube.com/watch?v=vecSVX1QYbQ&t=67s', '2010'),
(223, '좋은 날', '아이유', 'https://www.youtube.com/watch?v=jeqdYqsrsA0&t=124s', '2010'),
(224, '한여름밤의 꿀', 'San E, 레이나', 'https://www.youtube.com/watch?v=0pWz9xztrHE&t=83s', '2010'),
(225, 'Way Back Home', '숀', 'https://www.youtube.com/watch?v=amOSaNX7KJg&t=77s', '2010'),
(226, '이 소설의 끝을 다시 써보려 해', '한동근', 'https://www.youtube.com/watch?v=fmiEBlS5dCk&t=75s', '2010'),
(227, '끝사랑', '김범수', 'https://www.youtube.com/watch?v=N6ndPiblDPg&t=108s', '2010'),
(228, 'DNA', '방탄소년단', 'https://www.youtube.com/watch?v=MBdVXkSdhwU&t=88s', '2010'),
(229, '빨간 맛', '레드벨벳', 'https://www.youtube.com/watch?v=WyiIGEHQP8o&t=62s', '2010'),
(230, '내가 저지른 사랑', '임창정', 'https://www.youtube.com/watch?v=L-2M_-QLs8k&t=58s', '2010'),
(231, '자니', '프라이머리', 'https://www.youtube.com/watch?v=sQxrSj6g-3o&t=57s', '2010'),
(232, '팔레트', '아이유', 'https://www.youtube.com/watch?v=d9IxdwEFk1c&t=56s', '2010'),
(233, '좋다고 말해', '볼빨간사춘기', 'https://www.youtube.com/watch?v=y5MAgMVwfFs&t=60s', '2010'),
(234, '걱정말아요 그대', '이적', 'https://www.youtube.com/watch?v=028Vn61oCDo&t=45s', '2010'),
(235, '여수 밤바다', '버스커 버스커', 'https://www.youtube.com/watch?v=qcijCmUkqrc&t=83s', '2010'),
(236, '너랑 나', '아이유', 'https://www.youtube.com/watch?v=NJR8Inf77Ac&t=45s', '2010'),
(237, 'Beautiful', 'Crush', 'https://www.youtube.com/watch?v=W0cs6ciCt_k&t=51s', '2010'),
(238, '뚜두뚜두', '블랙핑크', 'https://www.youtube.com/watch?v=IHNzOHi8sJs&t=77s', '2010'),
(239, 'TT', '트와이스', 'https://www.youtube.com/watch?v=ePpPVE-GGJw&t=87s', '2010'),
(240, '너를 만나', '폴킴', 'https://www.youtube.com/watch?v=YBzJ0jmHv-4&t=85s', '2010'),
(241, '지나오다', '닐로', 'https://www.youtube.com/watch?v=eQPjjXatjjA&t=53s', '2010'),
(242, '그날처럼', '장덕철', 'https://www.youtube.com/watch?v=v6_GwXU1lkg&t=74s', '2010'),
(243, '나만 안되는 연애', '볼빨간사춘기', 'https://www.youtube.com/watch?v=iLlLLBuuvVU&t=58s', '2010'),
(244, '한숨', '이하이', 'https://www.youtube.com/watch?v=5iSlfF8TQ9k&t=87s', '2010'),
(245, 'D', 'DEAN', 'https://www.youtube.com/watch?v=eelfrHtmk68&t=68s', '2010'),
(246, 'REALLY REALLY', 'WINNER', 'https://www.youtube.com/watch?v=4tBnF46ybZk&t=55s', '2010'),
(247, '너였다면', '정승환', 'https://www.youtube.com/watch?v=T8D21-S-_dM&t=41s', '2010'),
(248, '여행', '볼빨간사춘기', 'https://www.youtube.com/watch?v=xRbPAVnqtcs&t=51s', '2010'),
(249, '그리워하다', '비투비', 'https://www.youtube.com/watch?v=wDkjWSt3HOM&t=131s', '2010'),
(250, '마지막처럼', '블랙핑크', 'https://www.youtube.com/watch?v=Amq-qlqbjYA&t=64s', '2010'),
(251, '뱅뱅뱅', '빅뱅', 'https://www.youtube.com/watch?v=2ips2mM7Zqw&t=74s', '2010'),
(252, '눈의 꽃', '박효신', 'https://www.youtube.com/watch?v=nQuUfQB0dSw&t=94s', '2010'),
(253, '에너제틱', 'Wanna One', 'https://www.youtube.com/watch?v=EVaV7AwqBWg&t=59s', '2010'),
(254, 'All For You', '서인국, 정은지', 'https://www.youtube.com/watch?v=Q_GyneFGQ74&t=74s', '2010'),
(255, '불장난', '블랙핑크', 'https://www.youtube.com/watch?v=9pdj4iJD08s&t=58s', '2010'),
(256, '양화대교', 'Zion.T', 'https://www.youtube.com/watch?v=uLUvHUzd4UA&t=65s', '2010'),
(257, '안아줘', '정준일', 'https://www.youtube.com/watch?v=1zp7MV26B24&t=60s', '2010'),
(258, '소녀', '오혁', 'https://www.youtube.com/watch?v=bLoO0FSXncg&t=62s', '2010'),
(259, '오늘부터 우리는', '여자친구', 'https://www.youtube.com/watch?v=YYHyAIFG3iI&t=36s', '2010'),
(260, '비가 오는 날엔', '비스트', 'https://www.youtube.com/watch?v=nI8halon2HQ&t=68s', '2010'),
(261, '썸 탈꺼야', '볼빨간사춘기', 'https://www.youtube.com/watch?v=hZmoMyFXDoI&t=40s', '2010'),
(262, '시차', '우원재', 'https://www.youtube.com/watch?v=vdwEE1mwjOo&t=57s', '2010'),
(263, '어땠을까', '싸이', 'https://www.youtube.com/watch?v=z6In9jqBxyM&t=73s', '2010'),
(264, '돌아오지마', '헤이즈', 'https://www.youtube.com/watch?v=VZ9PvjrT_aY&t=255s', '2010'),
(265, '눈, 코, 입', '태양', 'https://www.youtube.com/watch?v=UwuAPyOImoI&t=83s', '2010'),
(266, '비', '폴킴', 'https://www.youtube.com/watch?v=Q0sGc0WUago&t=90s', '2010'),
(267, '시간을 달려서', '여자친구', 'https://www.youtube.com/watch?v=0VKcLPdY9lI&t=65s', '2010'),
(268, '삐삐', '아이유', 'https://www.youtube.com/watch?v=nM0xDI5R50E&t=61s', '2010'),
(269, '숨', '박효신', 'https://www.youtube.com/watch?v=oBKpJiVEcnU&t=94s', '2010'),
(270, '이럴거면 그러지말지', '백아연', 'https://www.youtube.com/watch?v=x815A21RIto&t=51s', '2010'),
(271, 'I', '태연', 'https://www.youtube.com/watch?v=4OrCA1OInoo&t=63s', '2010'),
(272, '위잉위잉', '혁오', 'https://www.youtube.com/watch?v=IUoTjkS242c&t=71s', '2010'),
(273, '뿜뿜', '모모랜드', 'https://www.youtube.com/watch?v=JQGRg8XBnB4&t=68s', '2010'),
(274, '광화문에서', '규현', 'https://www.youtube.com/watch?v=rUbq_IXBaYg&t=72s', '2010'),
(275, '이 사랑', '다비치', 'https://www.youtube.com/watch?v=XyzaMpAVm3s&t=41s', '2010'),
(276, 'OOH-AHH하게', '트와이스', 'https://www.youtube.com/watch?v=0rtV5esQT6I&t=53s', '2010'),
(277, '그대가 분다', '엠씨더맥스', 'https://www.youtube.com/watch?v=JQ6wTmaEyL4&t=100s', '2010'),
(278, '우연히 봄', '로꼬, 유주', 'https://www.youtube.com/watch?v=GjyMuHmzxVE&t=60s', '2010'),
(279, '그대라는 사치', '한동근', 'https://www.youtube.com/watch?v=WYy2fROj7uU&t=78s', '2010'),
(280, '스물셋', '아이유', 'https://www.youtube.com/watch?v=42Gtm4-Ax2U&t=75s', '2010'),
(281, 'Dance The Night Away', '트와이스', 'https://www.youtube.com/watch?v=Fm5iP0S1z9w&t=91s', '2010'),
(282, '못해', '포맨', 'https://www.youtube.com/watch?v=rAyhaiiNgUo&t=79s', '2010'),
(283, '처음엔 사랑이란게', '버스커 버스커', 'https://www.youtube.com/watch?v=KEk98JAPt80&t=91s', '2010'),
(284, '너의 모든 순간', '성시경', 'https://www.youtube.com/watch?v=Dbxzh078jr4&t=76s', '2010'),
(285, '그때 헤어지면 돼', '로이킴', 'https://www.youtube.com/watch?v=SkN_hWI6n28&t=69s', '2010'),
(286, '그 중에 그대를 만나', '이선희', 'https://www.youtube.com/watch?v=IAyMtl9FRHI&t=42s', '2010'),
(287, '오늘 취하면', '수란', 'https://www.youtube.com/watch?v=8LsKFMtOr8I&t=96s', '2010'),
(288, '나의 옛날이야기', '아이유', 'https://www.youtube.com/watch?v=npttud7NkL0&t=67s', '2010'),
(289, '피 땀 눈물', '방탄소년단', 'https://www.youtube.com/watch?v=hmE9f-TEutc&t=120s', '2010'),
(290, '200%', '악동뮤지션', 'https://www.youtube.com/watch?v=0Oi8jDMvd_w&t=57s', '2010'),
(291, 'Boys And Girls', '지코', 'https://www.youtube.com/watch?v=avoCSYEWhR0&t=72s', '2010'),
(292, 'Marry Me', '마크툽, 구윤회', 'https://www.youtube.com/watch?v=czH-H8zJJY8&t=57s', '2010'),
(293, '?', '프라이머리', 'https://www.youtube.com/watch?v=wo0bnb5U64E&t=82s', '2010'),
(294, '별이 빛나는 밤', '마마무', 'https://www.youtube.com/watch?v=LjUXm0Zy_dk&t=65s', '2010'),
(295, '오랜 날 오랜 밤', '악동뮤지션', 'https://www.youtube.com/watch?v=wEQpfil0IYA&t=75s', '2010'),
(296, 'all of my life', '박원', 'https://www.youtube.com/watch?v=CbNmRJCkwQs&t=125s', '2010'),
(297, '같은 시간 속의 너', '나얼', 'https://www.youtube.com/watch?v=Q_YdBd05FJs&t=106s', '2010'),
(298, '꺼내 먹어요', 'Zion.T', 'https://www.youtube.com/watch?v=Ibb5RhoKfzE&t=35s', '2010'),
(299, '봄이 좋냐', '10CM', 'https://www.youtube.com/watch?v=cIGgSI1uhKI&t=63s', '2010'),
(300, 'You Are My Everything', '거미', 'https://www.youtube.com/watch?v=ToASX6axGuw&t=83s', '2010'),
(301, '아무노래', '지코', 'https://www.youtube.com/watch?v=UuV2BmJ1p_I&t=10s', '2020'),
(302, 'Meteor', '창모', 'https://www.youtube.com/watch?v=lOrU0MH0bMk&t=74s', '2020'),
(303, '아로하', '조정석', 'https://www.youtube.com/watch?v=3DOkxQ3HDXE&t=81s', '2020'),
(304, '흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야', '장범준', 'https://www.youtube.com/watch?v=YBEUXfT7_48&t=12s', '2020'),
(305, '에잇', '아이유', 'https://www.youtube.com/watch?v=TgOu00Mf3kI&t=64s', '2020'),
(306, 'Blueming', '아이유', 'https://www.youtube.com/watch?v=D1PvIWdJ8xo&t=47s', '2020'),
(307, '늦은 밤 너의 집 앞 골목길에서', '노을', 'https://www.youtube.com/watch?v=kFhf7pjRRjA&t=43s', '2020'),
(308, '시작', '가호', 'https://www.youtube.com/watch?v=6LDg0YGYVw4&t=46s', '2020'),
(309, '작은 것들을 위한 시', '방탄소년단', 'https://www.youtube.com/watch?v=XsX3ATc3FbA&t=82s', '2020'),
(310, 'Psycho', '레드벨벳', 'https://www.youtube.com/watch?v=uR8Mrt1IpXg&t=57s', '2020'),
(311, '어떻게 이별까지 사랑하겠어, 널 사랑하는 거지', '악동뮤지션', 'https://www.youtube.com/watch?v=m3DZsBw5bnE&t=85s', '2020'),
(312, 'Dynamite', '방탄소년단', 'https://www.youtube.com/watch?v=gdZLi9oWNZg&t=66s', '2020'),
(313, '마음을 드려요', '아이유', 'https://www.youtube.com/watch?v=69fhfXQv1rg&t=69s', '2020'),
(314, '처음처럼', '엠씨더맥스', 'https://www.youtube.com/watch?v=3XUe2PsadEk&t=77s', '2020'),
(315, 'How you like that', '블랙핑크', 'https://www.youtube.com/watch?v=ioNng23DkIM&t=39s', '2020'),
(316, '살짝 설렜어', '오마이걸', 'https://www.youtube.com/watch?v=iDjQSdN_ig8&t=47s', '2020'),
(317, 'ON', '방탄소년단', 'https://www.youtube.com/watch?v=mPVDGOVjRQ0&t=107s', '2020'),
(318, '오늘도 빛나는 너에게', '마크툽', 'https://www.youtube.com/watch?v=fs8a6bU2N9E&t=50s', '2020'),
(319, 'Downtown Baby', '블루', 'https://www.youtube.com/watch?v=P07XG1P0ums&t=53s', '2020'),
(320, '마리아', '화사', 'https://www.youtube.com/watch?v=tDukIfFzX18&t=72s', '2020'),
(321, '어떻게 지내', '오반', 'https://www.youtube.com/watch?v=v2jEdnznd3A&t=81s', '2020'),
(322, '모든 날, 모든 순간', '폴킴', 'https://www.youtube.com/watch?v=EXV8TwTo0A0&t=56s', '2020'),
(323, '다시 여기 바닷가', '싹쓰리', 'https://www.youtube.com/watch?v=ESKfHHtiSjs&t=87s', '2020'),
(324, 'Dolphin', '오마이걸', 'https://www.youtube.com/watch?v=oaRTMjLdiDw&t=36s', '2020'),
(325, '사랑하게 될 줄 알았어', '전미도', 'https://www.youtube.com/watch?v=rOCymN-Rwiw&t=79s', '2020'),
(326, 'Celebrity', '아이유', 'https://www.youtube.com/watch?v=0-q1KafFCLU&t=42s', '2020'),
(327, '롤린', '브레이브걸스', 'https://www.youtube.com/watch?v=-Axm4IYHVYk&t=38s', '2020'),
(328, 'Next Level', '에스파', 'https://www.youtube.com/watch?v=4TWR90KJl84&t=51s', '2020'),
(329, '라일락', '아이유', 'https://www.youtube.com/watch?v=v7bnOxV4jAc&t=54s', '2020'),
(330, '밤하늘의 별을', '경서', 'https://www.youtube.com/watch?v=NuTNPV72rFo&t=41s', '2020'),
(331, '신호등', '이무진', 'https://www.youtube.com/watch?v=SK6Sm2Ki9tI&t=79s', '2020'),
(332, 'Butter', '방탄소년단', 'https://www.youtube.com/watch?v=WMweEpGlu_U&t=36s', '2020'),
(333, '내 손을 잡아', '아이유', 'https://www.youtube.com/watch?v=BYQBs_4-MOo&t=86s', '2020'),
(334, '밝게 빛나는 별이 되어 비춰줄게', '송이한', 'https://www.youtube.com/watch?v=pgMcZSseBaE&t=62s', '2020'),
(335, '바라만 본다', 'MSG워너비', 'https://www.youtube.com/watch?v=-rM5JjNfPHA&t=57s', '2020'),
(336, '잠이 오질 않네요', '장범준', 'https://www.youtube.com/watch?v=SM_rmTBs5lo&t=50s', '2020'),
(337, '헤픈 우연', '헤이즈', 'https://www.youtube.com/watch?v=AJPLgrfBiBo&t=18s', '2020'),
(338, 'Lovesick Girls', '블랙핑크', 'https://www.youtube.com/watch?v=dyRsYk0LyA8&t=54s', '2020'),
(339, '취기를 빌려', '산들', 'https://www.youtube.com/watch?v=zX1y04hHZTQ&t=91s', '2020'),
(340, 'VVS', '미란이, 먼치맨, Khundi Panda, 머쉬베놈', 'https://www.youtube.com/watch?v=mZInUHwmzN8&t=128s', '2020'),
(341, '운전만해', '브레이브걸스', 'https://www.youtube.com/watch?v=4HjcypoChfQ&t=65s', '2020'),
(342, 'Dun Dun Dance', '오마이걸', 'https://www.youtube.com/watch?v=HzOjwL7IP_o&t=83s', '2020'),
(343, '오래된 노래', '스탠딩에그', 'https://www.youtube.com/watch?v=tsbssnjL6Cg&t=75s', '2020'),
(344, '그날에 나는 맘이 편했을까', '이예준', 'https://www.youtube.com/watch?v=Jqbe1Wdlkt4&t=58s', '2020'),
(345, 'Life Goes On', '방탄소년단', 'https://www.youtube.com/watch?v=-5q5mZbe3V8&t=91s', '2020'),
(346, 'Love Day', '양요섭, 정은지', 'https://www.youtube.com/watch?v=Xxz4uZKbZYQ&t=61s', '2020'),
(347, '나랑 같이 걸을래', '적재', 'https://www.youtube.com/watch?v=Vn2vi9cz6Tg&t=58s', '2020'),
(348, 'ASAP', 'STAYC', 'https://www.youtube.com/watch?v=NsY-9MCOIAQ&t=44s', '2020'),
(349, '안녕', '폴킴', 'https://www.youtube.com/watch?v=_niSIiVMEos&t=62s', '2020'),
(350, 'Love Poem', '아이유', 'https://www.youtube.com/watch?v=OcVmaIlHZ1o&t=64s', '2020'),
(351, 'Love Dive', '아이브', 'https://www.youtube.com/watch?v=Y8JFxS1HlDo&t=41s', '2020'),
(352, 'Tomboy', '(여자)아이들', 'https://www.youtube.com/watch?v=Jh4QFaPmdss&t=59s', '2020'),
(353, '취중고백', '김민석', 'https://www.youtube.com/watch?v=b54x0fD1DaM&t=59s', '2020'),
(354, '사랑인가 봐', '멜로망스', 'https://www.youtube.com/watch?v=UoBsiQW23IY&t=44s', '2020'),
(355, '사랑은 늘 도망가', '임영웅', 'https://www.youtube.com/watch?v=LKQ-18LoFQk&t=59s', '2020'),
(356, 'ELEVEN', '아이브', 'https://www.youtube.com/watch?v=--FmExEAsM8&t=42s', '2020'),
(357, '봄여름가을겨울', '빅뱅', 'https://www.youtube.com/watch?v=eN5mG_yMDiM&t=101s', '2020'),
(358, '다정히 내 이름을 부르면', '경서예지, 전건호', 'https://www.youtube.com/watch?v=YPvrhziJAno&t=51s', '2020'),
(359, 'That That', '싸이', 'https://www.youtube.com/watch?v=8dJyRm2jJ-U&t=63s', '2020'),
(360, '정이라고 하자', 'BIG Naughty', 'https://www.youtube.com/watch?v=DYrY1E4-9NI&t=68s', '2020'),
(361, 'Weekend', '태연', 'https://www.youtube.com/watch?v=RmuL-BPFi2Q&t=66s', '2020'),
(362, 'INVU', '태연', 'https://www.youtube.com/watch?v=AbZH7XWDW_k&t=57s', '2020'),
(363, '회전목마', 'sokodomo', 'https://www.youtube.com/watch?v=tnAxZipkuWw&t=100s', '2020'),
(364, 'Feel My Rhythm', '레드벨벳', 'https://www.youtube.com/watch?v=R9At2ICm4LQ&t=62s', '2020'),
(365, 'Hype Boy', '뉴진스', 'https://www.youtube.com/watch?v=11cta61wi0g&t=52s', '2020'),
(366, 'GANADARA', '박재범', 'https://www.youtube.com/watch?v=gFb1TftvdoM&t=136s', '2020'),
(367, 'Attention', '뉴진스', 'https://www.youtube.com/watch?v=js1CtxSY38I&t=129s', '2020'),
(368, 'After Like', '아이브', 'https://www.youtube.com/watch?v=F0B7HDiY-10&t=35s', '2020'),
(369, '나의 X에게', '경서', 'https://www.youtube.com/watch?v=S1JB_T6Fths&t=72s', '2020'),
(370, '그때 그 순간 그대로', 'WSG워너비', 'https://www.youtube.com/watch?v=8XGhoj0Dvxs&t=59s', '2020'),
(371, 'LOVE me', '비오', 'https://www.youtube.com/watch?v=ulYfmWSjG9M&t=50s', '2020'),
(372, '리무진', '비오', 'https://www.youtube.com/watch?v=BRtDS6NDLJw&t=77s', '2020'),
(373, '드라마', '아이유', 'https://www.youtube.com/watch?v=vORDkdgLzEs&t=27s', '2020'),
(374, 'strawberry moon', '아이유', 'https://www.youtube.com/watch?v=sqgxcCjD04s&t=61s', '2020'),
(375, '너의 모든 순간', '성시경', 'https://www.youtube.com/watch?v=Dbxzh078jr4&t=76s', '2020'),
(376, 'Ditto', '뉴진스', 'https://www.youtube.com/watch?v=pSUydWEqKwE&t=137s', '2020'),
(377, 'I AM', '아이브', 'https://www.youtube.com/watch?v=6ZUIwj3FgUY&t=51s', '2020'),
(378, 'OMG', '뉴진스', 'https://www.youtube.com/watch?v=_ZAgIHmHLdc&t=143s', '2020'),
(379, '사건의 지평선', '윤하', 'https://www.youtube.com/watch?v=BBdC1rl5sKY&t=96s', '2020'),
(380, 'Kitsch', '아이브', 'https://www.youtube.com/watch?v=pG6iaOMV46I&t=42s', '2020'),
(381, '퀸카', '(여자)아이들', 'https://www.youtube.com/watch?v=7HDeem-JaSY&t=60s', '2020'),
(382, '헤어지자 말해요', '박재정', 'https://www.youtube.com/watch?v=yFlxYHjHYAw&t=70s', '2020'),
(383, 'Spicy', '에스파', 'https://www.youtube.com/watch?v=Os_heh8vPfs&t=42s', '2020'),
(384, 'Antifragile', '르세라핌', 'https://www.youtube.com/watch?v=pyf8cbqyfPs&t=64s', '2020'),
(385, 'Super shy', '뉴진스', 'https://www.youtube.com/watch?v=ArmDp-zijuc&t=54s', '2020'),
(386, '파이팅 해야지', '부석순', 'https://www.youtube.com/watch?v=mBXBOLG06Wc&t=44s', '2020'),
(387, 'Unforgiven', '르세라핌', 'https://www.youtube.com/watch?v=UBURTj20HXI&t=100s', '2020'),
(388, '꽃', '지수', 'https://www.youtube.com/watch?v=YudHcBIxlYw&t=50s', '2020'),
(389, 'Teddy Bear', '스테이씨', 'https://www.youtube.com/watch?v=SxHmoifp0oQ&t=41s', '2020'),
(390, 'Seven', '정국', 'https://www.youtube.com/watch?v=QU9c0053UAU&t=48s', '2020'),
(391, '심', 'DK', 'https://www.youtube.com/watch?v=rb_Q1M2tKAM&t=102s', '2020'),
(392, '이브, 프시케 그리고 푸른 수염의 아내', '르세라핌', 'https://www.youtube.com/watch?v=dZs_cLHfnNA&t=78s', '2020'),
(393, 'Candy', 'NCT Dream', 'https://www.youtube.com/watch?v=zuoSn3ObMz4&t=97s', '2020'),
(394, 'ETA', '뉴진스', 'https://www.youtube.com/watch?v=jOTfBlKSQYY&t=71s', '2020'),
(395, '해요', '#안녕', 'https://www.youtube.com/watch?v=NaI7e4-ELXs&t=109s', '2020'),
(396, '새삥', '지코', 'https://www.youtube.com/watch?v=4Dnwcd0_Ji0&t=10s', '2020'),
(397, '손오공', '세븐틴', 'https://www.youtube.com/watch?v=-GQg25oP0S4&t=55s', '2020'),
(398, 'Nxde', '(여자)아이들', 'https://www.youtube.com/watch?v=fCO7f0SmrDc&t=65s', '2020'),
(399, 'Love Lee', '악동뮤지션', 'https://www.youtube.com/watch?v=EIz09kLzN9k&t=52s', '2020'),
(400, '우리들의 블루스', '임영웅', 'https://www.youtube.com/watch?v=JPPIrXdocjQ&t=61s', '2020');


-- 테이블이 존재하지 않는 경우에만 생성
CREATE TABLE IF NOT EXISTS country_image (
    country_image_id LONG PRIMARY KEY,
    country_name VARCHAR(255),
    country_image_link VARCHAR(255)
);

--Country Image 데이터 삽입
INSERT IGNORE INTO country_image (country_image_id, country_name, country_image_link) VALUES
(1, '가나', ''),
(2, '과테말라', ''),
(3, '그리스', ''),
(4, '나이지리아', ''),
(5, '남아프리카공화국', ''),
(6, '네덜란드', ''),
(7, '네팔', ''),
(8, '노르웨이', ''),
(9, '뉴질랜드', ''),
(10, '대만', ''),
(11, '덴마크', ''),
(12, '독일', ''),
(13, '라오스', ''),
(14, '라트비아', ''),
(15, '러시아', ''),
(16, '레바논', ''),
(17, '루마니아', ''),
(18, '룩셈부르크', ''),
(19, '리투아니아', ''),
(20, '말레이시아', ''),
(21, '멕시코', ''),
(22, '모나코', ''),
(23, '모로코', ''),
(24, '미국', ''),
(25, '미얀마', ''),
(26, '방글라데시', ''),
(27, '베트남', ''),
(28, '벨기에', ''),
(29, '북한', ''),
(30, '불가리아', ''),
(31, '브라질', ''),
(32, '사우디아라비아', ''),
(33, '세네갈', ''),
(34, '소말리아', ''),
(35, '수단', ''),
(36, '스웨덴', ''),
(37, '스위스', ''),
(38, '슬로바키아', ''),
(39, '슬로베니아', ''),
(40, '시리아', ''),
(41, '싱가폴', ''),
(42, '아르헨티나', ''),
(43, '알제리', ''),
(44, '에콰도르', ''),
(45, '에티오피아', ''),
(46, '엘살바도르', ''),
(47, '영국', ''),
(48, '온두라스', ''),
(49, '우루과이', ''),
(50, '우즈베키스탄', ''),
(51, '우크라이나', ''),
(52, '웨일스', ''),
(53, '이라크', ''),
(54, '이란', ''),
(55, '이스라엘', ''),
(56, '이탈리아', ''),
(57, '인도', ''),
(58, '인도네시아', ''),
(59, '일본', ''),
(60, '자메이카', ''),
(61, '조지아', ''),
(62, '중국', ''),
(63, '짐바브웨', ''),
(64, '체코', ''),
(65, '칠레', ''),
(66, '카메룬', ''),
(67, '카자흐스탄', ''),
(68, '카타르', ''),
(69, '캄보디아', ''),
(70, '캐나다', ''),
(71, '케냐', ''),
(72, '코스타리카', ''),
(73, '코트디부아르', ''),
(74, '콜롬비아', ''),
(75, '쿠바', ''),
(76, '쿠에이트', ''),
(77, '크로아티아', ''),
(78, '탄자니아', ''),
(79, '태국', ''),
(80, '토고', ''),
(81, '튀르키예', ''),
(82, '파라과이', ''),
(83, '파키스탄', ''),
(84, '파푸아뉴기니', ''),
(85, '팔레스타인', ''),
(86, '페루', ''),
(87, '포르투갈', ''),
(88, '폴란드', ''),
(89, '프랑스', ''),
(90, '핀란드', ''),
(91, '필리핀', ''),
(92, '한국', ''),
(93, '헝가리', ''),
(94, '호주', ''),
(95, '홍콩', ''),
(96, '르완다', ''),
(97, '스코틀랜드', ''),
(98, '스페인', ''),
(99, '이집트', ''),
(100, '앙골라', '');