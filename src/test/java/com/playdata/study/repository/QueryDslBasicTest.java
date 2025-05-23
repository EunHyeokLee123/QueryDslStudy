package com.playdata.study.repository;

import com.playdata.study.entity.Idol;
import com.playdata.study.entity.Group;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.playdata.study.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class QueryDslBasicTest {

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



    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim, "여");
        Idol idol2 = new Idol("사쿠라", 26, leSserafim, "여");
        Idol idol3 = new Idol("가을", 22, ive, "여");
        Idol idol4 = new Idol("리즈", 20, ive, "여");

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

    }

}