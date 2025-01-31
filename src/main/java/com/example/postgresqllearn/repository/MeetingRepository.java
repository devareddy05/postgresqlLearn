package com.example.postgresqllearn.repository;

import com.example.postgresqllearn.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
