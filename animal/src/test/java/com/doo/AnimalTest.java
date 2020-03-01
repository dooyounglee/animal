package com.doo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.doo.persistence.AnimalRepository;
import com.doo.persistence.BoardRepository;
import com.doo.persistence.MemberRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.vo.Animal;
import com.doo.vo.Board;
import com.doo.vo.Member;
import com.doo.vo.Reply;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnimalTest {

	@Autowired
	private MemberRepository mr;
	
	@Autowired
	private AnimalRepository ar;
	
	@Autowired
	private BoardRepository br;
	
	@Autowired
	private ReplyRepository rr;
	
	//@Test
	public void test() {
		for(Long i=0L;i<100;i++) {
			Animal a=new Animal(i,"동물"+i);
			ar.save(a);
		}
	}
	
	//@Test
	public void test1() {
		List<Board> list=new ArrayList<Board>();
		br.findBoardByDelYNWithReplyCountNative().forEach( b->{
			Board bo=((Board)(b[0]));
			bo.setRcount(((Long)(b[1])));
			list.add(bo);
		});
		System.out.println(list);
	}
	
	//@Test
	public void test2(MultipartHttpServletRequest req) {
		System.out.println(req.getSession().getServletContext().getRealPath(""));
	}
	
	@Test
	public void test3() {
		Board b=new Board();
		b.setB_no(40L);
		rr.getReplyOfBoard(b).forEach( e->{
			Reply r=(Reply)e[0];
			Member m=(Member)e[1];
			
			r.setNickname(m!=null?m.getNickname():r.getReplyer());
			r.setSignYN(m!=null?"Y":"N");
			System.out.println(r);
		});
	}
}
