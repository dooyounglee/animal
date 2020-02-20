package com.doo.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "animal")
@EqualsAndHashCode(of = "animal_no")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

	@Id
	private Long animal_no;
	private String animal_name;
}
