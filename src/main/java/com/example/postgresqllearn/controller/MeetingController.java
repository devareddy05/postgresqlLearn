package com.example.postgresqllearn.controller;

import com.example.postgresqllearn.dto.MeetingDto;
import com.example.postgresqllearn.entity.Employee;
import com.example.postgresqllearn.entity.Meeting;
import com.example.postgresqllearn.service.EmployeeService;
import com.example.postgresqllearn.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<MeetingDto> createMeeting(@RequestBody MeetingDto meetingDto) {
        MeetingDto createdMeeting = meetingService.createMeeting(meetingDto);
        return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDto> updateMeeting(@PathVariable Long id, @RequestBody MeetingDto meetingDto) {
        MeetingDto updatedMeeting = meetingService.updateMeeting(id, meetingDto);
        return ResponseEntity.ok(updatedMeeting);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable("id") Long id) {
        MeetingDto meetingDto = meetingService.getMeetingById(id);
        return new ResponseEntity<>(meetingDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MeetingDto>> getAllMeetings() {
        List<MeetingDto> meetingDtos = meetingService.getAllMeetings();
        return new ResponseEntity<>(meetingDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.ok("Meeting deleted successfully");
    }


    // Get all meetings for a particular date
    @GetMapping("/by-date")
    public List<Meeting> getMeetingsByDate(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return meetingService.getMeetingsByDate(localDate);
    }

    // Get all attendees for a particular meeting
    @GetMapping("/{meetingId}/attendees")
    public List<Employee> getAttendeesByMeetingId(@PathVariable Long meetingId) {
        return meetingService.getAttendeesByMeetingId(meetingId);
    }





}
