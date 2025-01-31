package com.example.postgresqllearn.service;

import com.example.postgresqllearn.Mapper.MeetingMapper;
import com.example.postgresqllearn.dto.MeetingDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.entity.Meeting;
import com.example.postgresqllearn.exception.DatabaseIntegrityException;
import com.example.postgresqllearn.exception.NoSuchElementException;
import com.example.postgresqllearn.exception.ResourceNotFoundException;
import com.example.postgresqllearn.repository.MeetingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingMapper meetingMapper;



    public MeetingDto createMeeting(MeetingDto meetingDto) {
        Meeting meeting = meetingMapper.mapToMeeting(meetingDto);
        return saveMeeting(meeting);
    }

    public MeetingDto getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting with id " + id + " not found"));
        return meetingMapper.mapToMeetingDto(meeting);
    }

    public List<MeetingDto> getAllMeetings() {
        List<Meeting> meetings = meetingRepository.findAll();
        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : meetings) {
            meetingDtos.add(meetingMapper.mapToMeetingDto(meeting));
        }
        return meetingDtos;
    }

    public MeetingDto updateMeeting(Long id, MeetingDto meetingDto) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting with id " + id + " not found"));
        meeting = meetingMapper.mapToMeeting(meetingDto);
        return saveMeeting(meeting);
    }

    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting with id " + id + " not found"));
        meetingRepository.delete(meeting);
    }

    public MeetingDto saveMeeting(Meeting meeting) {
        try {
            meetingRepository.save(meeting);
        } catch (DatabaseIntegrityException e) {
            throw new DatabaseIntegrityException("A meeting with the same details already exists.");
        }

        return meetingMapper.mapToMeetingDto(meeting);
    }


    // Fetch all meetings for a particular date
    public List<Meeting> getMeetingsByDate(LocalDate date) {
        try {
            List<Meeting> meetings = meetingRepository.findByDate(date);
            if (meetings.isEmpty()) {
                throw new NoSuchElementException("No meetings found for the specified date: " + date);
            }
            return meetings;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching meetings for date: " + date, e);
        }
    }

    // Fetch all attendees for a particular meeting
    public List<Employee> getAttendeesByMeetingId(Long meetingId) {
        try {
            Meeting meeting = meetingRepository.findById(meetingId)
                    .orElseThrow(() -> new NoSuchElementException("Meeting not found with ID: " + meetingId));
            return meeting.getAttendees();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching attendees for meeting ID: " + meetingId, e);
        }
    }


}
