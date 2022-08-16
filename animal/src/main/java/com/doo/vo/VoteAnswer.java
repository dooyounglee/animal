package com.doo.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.doo.vo.dto.OpinionAnswer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="voteanswer")
@IdClass(OpinionAnswer.class)
public class VoteAnswer {

	private Long ono;
	@Id
	private Long vno;
	@Id
	private String email;
}
