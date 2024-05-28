package com.springboot.jpa.hibernate.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.jpa.hibernate.model.MMnrc;

@Repository
public interface INrcRepository extends JpaRepository<MMnrc, Long> {
}