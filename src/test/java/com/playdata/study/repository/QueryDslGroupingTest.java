package com.playdata.study.repository;

import com.playdata.study.dto.GroupAverageResponse;
import com.playdata.study.entity.Group;
import com.playdata.study.entity.Idol;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.playdata.study.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslGroupingTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private IdolRepository idolRepository;

    @Autowired
    JPAQueryFactory factory;


    @Test
    @DisplayName("나이가 24세 이상인 아이돌은 존재한다.")
    void testAge() {
        // given
        int age = 24;
        // when
        List<Idol> idols = factory.selectFrom(idol)
                .where(idol.age.goe(age))
                .fetch();

        // then

        System.out.println("\n\n\n");
        System.out.println(idols.size());
        System.out.println("\n\n\n");

        assertFalse(idols.isEmpty());
    }

    @Test
    @DisplayName("이름에 '김'이 포함된 아이돌을 조회하고, 실제 '김'이 포함되었는지 확인.")
    void testNameCondition() {
        // given

        String name = "김";

        // when
        List<Idol> idols = factory.selectFrom(idol)
                .where(idol.idolName.contains(name))
                .fetch();

        // then
        for (Idol idol : idols) {
            System.out.println("\n\n\n");
            System.out.println(idol.getIdolName());
            System.out.println("\n\n\n");
            assertTrue(idol.getIdolName().contains(name));
        }
    }

    @Test
    @DisplayName("나이가 20세에서 25세 사이인 아이돌은 존재한다.")
    void testAge2() {
        // given

        // when

        List<Idol> idols = factory.selectFrom(idol)
                .where(idol.age.between(20, 25))
                .fetch();

        // then
        System.out.println("\n\n\n");
        System.out.println(idols.size());
        System.out.println("\n\n\n");
        assertFalse(idols.isEmpty());
    }

    @Test
    @DisplayName("'르세라핌' 그룹에 속한 인원은 두명일 것이다.")
    void testGroupCondition() {
        // given
        // when
        List<Idol> idols = factory.selectFrom(idol)
                .where(idol.group.groupName.eq("르세라핌"))
                .fetch();

        // then
        System.out.println("\n\n\n");
        System.out.println(idols.size());

        assertEquals(2, idols.size());
    }


    @Test
    @DisplayName("SELECT 절에서 원하는 컬럼만 지정 조회")
    void tupleTest() {
        // given

        // when

        // 여러개의 컬럼(필드)들을 선택(select)할 때 사용하는 반환 타입
        // DTO로 매핑하지 않고 복수의 값을 직접 다룰 수 있게 해주는 QueryDSL 전용 객체
        List<Tuple> idolList = factory.select(idol.idolName, idol.age)
                .from(idol)
                .fetch();
        // then
        for (Tuple tuple : idolList) {
            System.out.println("tuple = " + tuple);
            String name = tuple.get(idol.idolName);
            int age = tuple.get(idol.age);

            System.out.printf("name = %s, age = %d\n", name, age);
        }
    }


    @Test
    @DisplayName("그룹화 기본")
    void groupByTest() {
        // given

        // when

        Integer totalAge = factory.select(idol.age.sum())
                .from(idol)
                .fetchOne();
        // then
        System.out.println("totalAge = " + totalAge);
    }


    @Test
    @DisplayName("그룹별 인원 수 세기")
    void groupByCountTest() {
        // given

        // when

        List<Tuple> list = factory.select(idol.group.groupName, idol.count())
                .from(idol)
                .groupBy(idol.group.id)
                .fetch();

        // then

        for (Tuple tuple : list) {
            String s = tuple.get(idol.group.groupName);
            Long l = tuple.get(idol.count());
            System.out.printf("그룹명 = %s, 인원수 = %d\n", s, l);
        }
    }


    @Test
    @DisplayName("성별별 아이돌 인원수 세기")
    void groupByGenderTest() {
        // given

        // when

        List<Tuple> list = factory.select(idol.gender, idol.count())
                .from(idol)
                .groupBy(idol.gender)
                .fetch();

        // then
        for (Tuple tuple : list) {
            String s = tuple.get(idol.gender);
            Long l = tuple.get(idol.count());
            System.out.printf("성별 = %s, 인원수 = %d\n", s, l);
        }

    }


    @Test
    @DisplayName("그룹별로 그룹명과 평균나이를 조회 (평균나이가 20 ~ 25세만 대상으로)")
    void averageAgeTest() {
        // given

        // when

        List<Tuple> list = factory.select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch();

        // then
        for (Tuple tuple : list) {
            String s = tuple.get(idol.group.groupName);
            Double v = tuple.get(idol.age.avg());
            System.out.printf("그룹명 = %s, 평균나이 = %.2f세\n", s, v);
        }

    }

    @Test
    @DisplayName("그룹별로 그룹명과 평균나이를 조회하여 DTO로 처리")
    void groupAverageDtoTest() {
        // given

        // when

        List<GroupAverageResponse> dtoList = factory.select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch()
                .stream()
                .map(GroupAverageResponse::from)
                .toList();

        // then

        dtoList.forEach(System.out::println);
    }


    @Test
    @DisplayName("그룹별로 그룹명과 평균나이를 조회하여 DTO로 처리 ver2")
    void groupAvgAgeDtoTestV2() {
        // given

        // when
        List<GroupAverageResponse> dtoList = factory
                .select(
                        // 조회 결과를 원하는 DTO 타입으로 바로 매핑할 때 사용하는 메서드
                        Projections.constructor(
                                GroupAverageResponse.class,
                                idol.group.groupName,
                                idol.age.avg()
                        )
                )
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch();

        // then
        dtoList.forEach(System.out::println);
    }

    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("뉴진스");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, leSserafim, "여");
        Idol idol2 = new Idol("사쿠라", 26, leSserafim, "여");
        Idol idol3 = new Idol("가을", 22, ive, "여");
        Idol idol4 = new Idol("리즈", 20, ive, "여");
        Idol idol5 = new Idol("장원영", 20, ive, "여");
        Idol idol6 = new Idol("안유진", 21, ive, "여");
        Idol idol7 = new Idol("카즈하", 21, leSserafim, "여");
        Idol idol8 = new Idol("RM", 29, bts,"남");
        Idol idol9 = new Idol("정국", 26, bts,"남");
        Idol idol10 = new Idol("해린", 18, newjeans, "여");
        Idol idol11 = new Idol("혜인", 16, newjeans, "여");

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
    }

}