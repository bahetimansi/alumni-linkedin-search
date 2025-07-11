package com.example.alumni.repository;

import com.example.alumni.entity.AlumniProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniRepository extends JpaRepository<AlumniProfile, Long> {
}

