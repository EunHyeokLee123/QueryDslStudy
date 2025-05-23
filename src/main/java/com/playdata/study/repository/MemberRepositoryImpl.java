package com.playdata.study.repository;

import com.playdata.study.entity.Member;
import com.playdata.study.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.playdata.study.entity.QMember.*;

// QueryDSL용 인터페이스의 구현체는 반드시 이름이 Impl로 끝나야 자동으로 인식되어서
// 원본 인터페이스 타입(MemberRepository)의 객체로도 사용이 가능합니다.
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public List<Member> findByName(String name) {
        return factory.selectFrom(member)
                .where(member.userName.eq(name))
                .fetch();
    }

}
