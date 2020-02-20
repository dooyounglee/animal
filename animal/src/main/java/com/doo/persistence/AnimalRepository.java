package com.doo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Animal;

public interface AnimalRepository extends CrudRepository<Animal, Long>{

}
