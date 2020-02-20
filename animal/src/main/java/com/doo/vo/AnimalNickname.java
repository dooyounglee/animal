package com.doo.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
