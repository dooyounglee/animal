package com.doo.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="voteopinion")
@EqualsAndHashCode(of="ono")
public class VoteOpinion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ono;
	private String content;
	
	@Transient
	private int cnt;
	
}
