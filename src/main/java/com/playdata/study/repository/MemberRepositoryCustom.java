package com.playdata.study.repository;

import com.playdata.study.entity.Member;

import java.util.List;

// JPA 아님!! extends 안함
// QueryDSL 사용 용도
public interface MemberRepositoryCustom {

    List<Member> findByName(String name);

}
