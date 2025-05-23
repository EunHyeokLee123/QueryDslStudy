package com.playdata.study.repository;

import com.playdata.study.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.playdata.study.entity.QGroup.*;
import static com.playdata.study.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslJoinTest {


    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    JPAQueryFactory factory;

    
    @Test
    @DisplayName("inner join 예제")
    void innerJoinTest() {
        // given
        
        // when

        List<Idol> idolList = factory.select(idol)
                .from(idol)
                // innerJoin의 첫번째 파라미터는 from절에 쓴 entity의 연관객체를 작성
                // 두번째 파라미터는 실제로 조인할 대상 (Q엔터티)
                .innerJoin(idol.group, group)
                .fetch();

        // then

        System.out.println("\n\n\n");
        idolList.forEach(idol -> {

            System.out.println(idol);
            System.out.println("Group: "+idol.getGroup());
        });

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

        Idol idol1 = new Idol("김채원", 24,  leSserafim,"여");
        Idol idol2 = new Idol("사쿠라", 26,  leSserafim,"여");
        Idol idol3 = new Idol("가을", 22,  ive,"여");
        Idol idol4 = new Idol("리즈", 20,  ive,"여");
        Idol idol5 = new Idol("장원영", 20,  ive,"여");
        Idol idol6 = new Idol("안유진", 21,  ive,"여");
        Idol idol7 = new Idol("카즈하", 21,  leSserafim,"여");
        Idol idol8 = new Idol("RM", 29,  bts,"남");
        Idol idol9 = new Idol("정국", 26, bts,"남");
        Idol idol10 = new Idol("해린", 18 , newjeans,"여");
        Idol idol11 = new Idol("혜인", 16,  newjeans,"여");
        Idol idol12 = new Idol("김종국", 48,  null,"남");
        Idol idol13 = new Idol("아이유", 31, null,"여");


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
        idolRepository.save(idol12);
        idolRepository.save(idol13);


        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);


    }


}