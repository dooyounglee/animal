package com.doo.vo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "reply")
@EqualsAndHashCode(of = "r_no")
@ToString(exclude="board")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long r_no;
	
	private String replyText;
	private String replyer;
	private String animal;
	
	private String ipAddress;
	
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Board board;
}
