package com.example.postgresqllearn.repository;

import com.example.postgresqllearn.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByDate(LocalDate date);
}
