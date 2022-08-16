package com.doo.vo;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
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
@EqualsAndHashCode(of = "rno")
@ToString(exclude="board")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="r_no")
	private Long rno;
	
	private String replyText;
	private String replyer;
	private Long animal_no;
	
	private String pw;
	private Long rref;
	@Column(name="re_yn")
	private String reYN="N";
	private String ipAddress;
	@Column(name="del_yn")
	private String delYN="N";
	@Column(name="pc_yn")
	private String pcYN;
	
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private Board board;
	
	
	@Transient
	private Long b_no;
	@Transient
	private String nickname;
	@Transient
	private String signYN;
	@Transient
	private String animal;
}
