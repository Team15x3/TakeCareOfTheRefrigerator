package com.team15x3.caucse.takecareoftherefrigerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UseByDate {


    private ArrayList<String> categoryList = new ArrayList<String>(Arrays.asList(
            "냉면/메밀", "파스타", "당면", "우동 숙면", "쌀국수/월남쌈", "국수/칼국수/콩국수", "컵라면", "봉지라면",
            "분유이유식", "아기과자", "임산 수유부용 식품", "냉장두유", "선물용두유", "아기두유", "성인두유", "아기음료",
            "곡물우유", "연유", "아이스크림", "버터/마가린/생크림", "치즈", "떠먹는 요구르트", "마시는 요구르트", "멸균우유", "딸기/초코/커피우유", "어린이우유", "고칼슘우유", "저지방/무지방우유", "우유",
            "엿", "수입초콜릿/사탕", "젤리", "껌", "막대/츄잉사탕", "사탕", "초코바/양갱", "초콜릿", "카라멜",
            "상온소세지", "시리얼", "빵류", "수입과자", "한과/전통과자", "맛밤",
            "파이/카스타드/소프트쿠키", "쿠키/비스킷", "쌀/옥수수과자", "스낵",
            "커피믹스", "헤즐넛,카푸치노향커피믹스", "블랙믹스(설탕함유)", "블랙믹스(무설탕)", "원두커피", "인스턴트커피", "캡슐커피", "코코아/핫초코", "프림", "아이스커피/아이스티", "녹차/현미녹차", "둘글레차/옥수수차/메밀차",
            "홍차/허브차/보이차", "주전자용차(알곡/티백)", "율무차/땅콩차/견과차", "유자차/모과차", "한차/생강차/전통차", "기타차류", "다이어트차",
            "토마토/망고/자몽주스", "포도/사과주스", "오렌지/감귤주스", "탄산수", "식혜/수정과/전통음료", "수입생수", "어린이음료", "국내생수", "음료세트", "티음료", "커피음료", "무알콜맥주", "스포츠/이온/비타민/숙취해소",
            "환타/웰치스/레몬에이드류", "콜라/사이다", "냉장과일주스/쿨피스류", "기타과일주스", "매실/알로에/블루베리주스",
            "기타가공품", "어육가공품", "건조저장육류",
            "건포도/건과일", "땅콩/아몬드류",
            "빵/떡믹스", "밀가루/요리가루", "기타조미료", "식품첨가물", "인공감미료", "후추/향신료/와사비", "조미료", "참꺠/들깨", "고춧가루", "소금", "설탕",
            "대두유/옥수수유", "기타기름", "드레싱", "양념/소스", "파스타소스", "케챱/마요네즈",
            "물엿/액젓", "식초/음용식초", "올리브유/포도씨유", "현미유/쌀눈유/해바라기유", "카롤라유", "참기름/들기름",
            "초고추장/볶음고추장", "고추장", "간장", "쌈장", "된장",
            "곡류가공품", "팥빙수재료", "농산통조림", "과일통조림", "고등어/꽁치/골뱅이/번데기", "반찬통조림/닭가슴살", "스팸/돈육통조림", "참치캔",
            "카레/즉석카레", "햇반/즉석밥/누룽지", "선식", "즉석국/밥양념", "죽/스프", "덮밥/덮밥소스", "짜장/즉석짜장",
            "소주", "맥주", "막걸리", "기타주류",
            "또띠아", "간식/디저트/샐러드", "보쌈/야식류", "순대/족발", "볶음류", "삼각김밥/죽/면류", "국/탕/찌개", "냉동 밥/스파게티", "찜",
            "식빵/일반빵", "베이킹도구/재료", "냉동생지/즉석빵", "호두파이/와플/츄러스", "베이클/머핀/도너츠", "롤케익/카스텔라/쿠키", "케익/케익선물세트", "찐빵/호빵", "과일잼", "땅콩버터",
            "백설기떡", "떡국떡/떡볶이떡", "특산물음식", "떡",
            "콩식품", "청국장/찌게양념/소스", "젓갈", "간장/양념게장", "수산반찬", "축산반찬", "농산반찬", "단무지/무쌈/무절임",
            "양파/파/부추김치/기타", "갓김치/고들빼기", "물김치/백김치/동치미", "깍두기/총각/열무김치", "혼합김치", "일반김치", "절임배추/김치양념", "묵은지", "맛김치/볶음김치",
            "피자", "만두피", "냉동간식", "냉동반찬", "만두",
            "소시지/베이컨", "햄/김밥재료", "냉장간식", "냉장면류", "맛살", "유부", "어묵",
            "국산꿀", "수입꿀", "꿀가공품", "로얄젤리/프로폴리스",
            "인삼차", "인삼", "건강즙/과일즙", "산삼배양근", "어린이홍삼", "홍삼선물세트", "홍인삼음료/홍삼차/사탕", "홍삼절편/정과/양갱", "홍삼분말/캡슐/환", "홍삼액", "홍삼뿌리군/농축액",
            "통조림/쨈", "커피/차", "과자/캔디", "면류/시리얼/즉석식품", "우유/요구르트/치즈/유가공", "음료/건강식품", "분말류/장류/유지류/소스류", "친환경 가공 선물세트", "냉장냉동",
            "한차", "건강차재료",
            "다이어트보조식품", "헬스보충식",
            "종합비타민", "비오틴", "비타민A", "비파민B", "비타민C", "비타민D", "비타민E", "빌베리추출물", "오메가3", "감마리놀렌산", "철분", "엽산", "칼슘", "아연",
            "초유", "마그네슘", "쏘팔메토", "글루코사민", "루테인", "콜라겐", "하알루론산", "코엔자임큐텐", "키토산",
            "스쿠알렌", "유산균", "알로에", "식이섬유", "클로렐라", "스피루리나", "기타 건강식품", "일반의약품", "어린이건강", "밀크씨슬", "녹차추출물", "옥타코사놀", "엠에스엠", "공액리놀렌산", "대두이소플라본",
            "매실추출물", "백수오", "레시틴", "가르시니아캄보지아추출물", "회화나무 열매 추출물", "홍경전 추출물", "난소화성말토덱스트린", "은행잎추출물", "셀레늄", "베타카로틴", "피크노제놀-프랑스해안송껍질추출물", "잔티젠",
            "메추리알",
            "계육",
            "혼합곡", "기능성잡곡",
            "수수", "조류", "기장", "깨", "율무/녹두", "기타잡곡",
            "보리", "콩", "팥", "서리태",
            "찹쌀", "현미", "흑미",
            "쌀",
            "두부/콩나물",
            "조미김/생김",
            "건오징어/어포/육포"));


    private ArrayList<Integer> usebydateList = new ArrayList<Integer>(Arrays.asList(
            240, 10, 10, 10,  10 ,  10 ,  240 ,  240 ,
            30, 30, 30, 30,  30 ,  30 ,  30 ,  30 ,
            50, 10, 30, 30,  70 ,  10 ,  10 ,  50 ,  40 ,  40 ,  50 ,  50 ,  50 ,
            50, 50, 50, 50,  50 ,  50 ,  50 ,  50 ,  50 ,
            30, 30, 20, 30,  30 ,  30 ,
            30, 30, 90, 90,
            50, 50, 50, 50,  50 ,  50 ,  50 ,  50 ,  50 ,  100 ,  100 ,  100 ,
            90, 90, 90, 90,  90 ,  90 ,  90 ,
            30, 30, 30, 30,  30 ,  30 ,  30 ,  30 ,  30 ,  30 ,  30 ,  30 ,  30 ,
            90, 90, 20, 20,  50 ,
            30, 30, 30,
            30, 150,
            20, 100, 100, 100,  100 ,  100 ,  100 ,  100 ,  100 ,  100 ,  100 ,
            100, 1800, 50, 50,  50 ,  100 ,
            1800, 1800, 1800, 1800,  1800 ,  900 ,
            360, 720, 360, 360,  360 ,
            100, 100, 1800, 300,  300 ,  100 ,  100 ,  1800 ,
            100, 300, 300, 100,  100 ,  100 ,  100 ,
            3600, 100, 10, 100,
            15, 15, 10, 10, 10, 5, 5, 300, 10,
            15 ,  10 ,  10 ,  2 ,  2 ,  10 ,  2 ,  20 ,  50 ,  50 ,
            30 ,  30 ,  30 ,  30 ,
            300 ,  30 ,  30 ,  10 , 30 ,  30 ,  30 ,  10 ,
            180 ,  180 ,  180 ,  180 ,  180 ,  180 ,  180 ,  180 ,  180 ,
            300 ,  150 ,  360 ,  300 ,  360 ,
            30 ,  30 ,  30 ,  30 ,  30 ,  30 ,  30 ,
            300 ,  300 ,  500 ,  500 ,
            400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,
            2000 ,  30 ,  50 ,  30 ,  30 ,  50, 50 ,  30 ,  30 ,
            4100 ,  100 ,
            400 ,  400 ,
            400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,
            400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,
            400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,
            400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,  400 ,
            20 ,
            300 ,
            500 ,  500 ,
            500 ,  300 ,  300 ,  300 ,  300 ,  300 ,
            500 ,  300 ,  300 ,  300 ,
            500 ,  500 ,  500 ,
            500 ,
            50 ,
            300 ,
            30 ));



    public String getUseByDate(String category, String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
            Date stand_date = formatter.parse(date);

            int index = 0;

            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).compareTo(category) == 0) {
                    index = i;
                    break;
                }
            }

            Integer integer = usebydateList.get(index);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(stand_date);

            calendar.add(Calendar.DAY_OF_YEAR, integer.intValue());


            String usebydate = formatter.format(calendar.getTime());

            return usebydate;


        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

}