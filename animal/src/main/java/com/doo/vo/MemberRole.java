package com.doo.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "memberrole")
@EqualsAndHashCode(of = "fno")
@ToString
public class MemberRole {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fno;
	
	private String roleName;
}
