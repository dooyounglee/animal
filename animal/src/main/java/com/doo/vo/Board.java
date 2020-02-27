package com.doo.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "board")
@EqualsAndHashCode(of = "b_no")
@ToString(exclude="reply")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long b_no;
	private String title;
	private String writer;
	private String content;
	
	private String ipAddress;

	@Column(updatable=false)
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@JsonIgnore
	@OneToMany(mappedBy="board", fetch=FetchType.LAZY)
	private List<Reply> reply;
}
