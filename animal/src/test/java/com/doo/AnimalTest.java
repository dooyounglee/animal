package com.doo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.doo.persistence.AnimalRepository;
import com.doo.persistence.BoardRepository;
import com.doo.persistence.MemberRepository;
import com.doo.persistence.ReplyRepository;
import com.doo.persistence.VoteRepository;
import com.doo.vo.Animal;
import com.doo.vo.Board;
import com.doo.vo.Member;
import com.doo.vo.VoteOpinion;
import com.doo.vo.Reply;
import com.doo.vo.Vote;

//@RunWith(SpringRunner.class)
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
	
	@Autowired
	private VoteRepository vr;
	
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
	
	//@Test
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
	
	@Transactional
	//@Test
	public void test4() {
		br.findAll().forEach(e->{
			System.out.println(e);
			System.out.println(e.getReply().size());
		});
	}
	
	//@Test
	public void test5() {
		Vote v=new Vote();
		v.setTitle("title");
		
		VoteOpinion o1=new VoteOpinion();
		o1.setContent("c1");
		
		VoteOpinion o2=new VoteOpinion();
		o2.setContent("c2");
		
		//v.setOlist(Arrays.asList(o1,o2));
		
		vr.save(v);
	}
	
	//@Test
	public void test6() {
		Vote v=new Vote();
		System.out.println("v가져온다");
		//v=vr.findById(2L).isPresent();
		System.out.println(vr.findById(1L).isPresent()==false?null:vr.findById(1L).get());
		System.out.println(vr.findById(2L).isPresent()==false?null:vr.findById(2L).get());
		System.out.println("한번만?");
		vr.findById(1L).ifPresent(e->System.out.println(e));
		//vr.save(v);
	}
}
