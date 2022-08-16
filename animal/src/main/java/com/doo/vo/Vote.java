package com.doo.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="olist")
@Entity
@Table(name="vote")
@EqualsAndHashCode(of="vno")
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vno;
	private String title;
	private String email;
	private String status="01"; //01:투표전 02:투표중 03:투표마감
	
	//@OneToMany(cascade=CascadeType.ALL)
	//@JoinColumn(name="vno")
	//private List<VoteOpinion> olist;
}
