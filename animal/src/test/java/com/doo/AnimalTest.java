package com.doo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.doo.persistence.AnimalRepository;
import com.doo.persistence.MemberRepository;
import com.doo.vo.Animal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalTest {

	@Autowired
	private MemberRepository mr;
	
	@Autowired
	private AnimalRepository ar;
	
	@Test
	public void test() {
		for(Long i=0L;i<100;i++) {
			Animal a=new Animal(i,"동물"+i);
			ar.save(a);
		}
	}
}
