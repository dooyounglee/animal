package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Animal;
import com.doo.vo.AnimalNickname;
import com.doo.vo.dto.AnimalEmail;

public interface AnimalNicknameRepository extends CrudRepository<AnimalNickname, AnimalEmail>{

	@Query("SELECT a FROM Animal a WHERE a.animal_no not in (select b.animal_no from AnimalNickname b where b.b_no=?1)")
	public List<Animal> getRemainAnimal(Long b_no);
}
