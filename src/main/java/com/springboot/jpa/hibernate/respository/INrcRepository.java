package com.springboot.jpa.hibernate.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.hibernate.model.Image;
import com.springboot.jpa.hibernate.model.MMnrc;

@Repository
public interface INrcRepository extends JpaRepository<MMnrc, String> {
//	@Query(value ="SELECT EXISTS (SELECT 1 FROM myanmarnrc m WHERE m.mm_id = :mmid)",
//            nativeQuery = true)
//	Optional<Boolean>  mmIdExist(@Param("mmid") String mmid);

	@Query("SELECT m FROM MMnrc m WHERE m.mmId = :mmid")
	Optional<MMnrc> getMMnrcByMMid(@Param("mmid") String mmid);
	
	@Query("SELECT m FROM Image m WHERE m.mmId = :mmid")
	Optional<Image> getMMnrcImageByMMid(@Param("mmid") String mmid);

	@Query("SELECT EXISTS (SELECT 1 FROM MMnrc m WHERE m.mmId = :mmid)")
	Boolean mmIdExist(@Param("mmid") String mmid);

	void save(Image image);

}