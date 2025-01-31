package com.example.postgresqllearn.Mapper;

import com.example.postgresqllearn.dto.MeetingDto;
import com.example.postgresqllearn.entity.Meeting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeetingMapper {

    MeetingDto mapToMeetingDto(Meeting meeting);
    Meeting mapToMeeting(MeetingDto meetingDto);

}
