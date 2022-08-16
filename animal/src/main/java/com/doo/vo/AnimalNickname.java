package com.doo.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.doo.vo.dto.AnimalEmail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 이두영
 * 게시판에 누가 어떤 동물을 가져갔는지 현황판
 *
 */
@Getter
@Setter
@Entity
@Table(name="animalnickname")
@ToString
@IdClass(AnimalEmail.class)
public class AnimalNickname {

	@Id
	private String email;
	@Id
	private Long b_no;
	private Long animal_no;

}
