package com.doo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Member;

public interface MemberRepository extends CrudRepository<Member, String>{

}
