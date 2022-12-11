package com.karazin.maltsev.api;

import com.karazin.maltsev.entity.DoctorSchedule;
import com.karazin.maltsev.model.DoctorScheduleRequest;
import com.karazin.maltsev.exception.SessionWithDoctorScheduleException;
import com.karazin.maltsev.exception.UnknownScheduleException;
import com.karazin.maltsev.repository.DoctorScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor-schedules")
public class DoctorScheduleController {

  private final DoctorScheduleRepository doctorScheduleRepository;

  @GetMapping("/{id}")
  public List<DoctorSchedule> fetchSchedulesByDoctorId(@PathVariable Long id) {
    return doctorScheduleRepository.findAllByDoctorId(id);
  }

  @PostMapping("/")
  public List<DoctorSchedule> fetchSchedulesByRequest(
      @RequestBody DoctorScheduleRequest scheduleRequest) {
    if (scheduleRequest.getDoctorId() == null) {
      throw new SessionWithDoctorScheduleException();
    }
    return doctorScheduleRepository.findAllByDoctorIdAndTimeBetween(scheduleRequest.getDoctorId(),
        scheduleRequest.getFrom(), scheduleRequest.getTo());
  }

  @PostMapping("/schedule")
  public DoctorSchedule scheduleSessionWithDoctor(
      @RequestBody DoctorScheduleRequest scheduleRequest) {
    DoctorSchedule schedule = doctorScheduleRepository.findFirstByDoctorIdAndTime(
        scheduleRequest.getDoctorId(),
        scheduleRequest.getSelectedTime());

    if (schedule != null && schedule.isFree()) {
      schedule.setPatientId(scheduleRequest.getPatientId());
      schedule.setFree(false);
      return doctorScheduleRepository.save(schedule);
    }

    throw new SessionWithDoctorScheduleException();
  }

  @PostMapping("/decline")
  public DoctorSchedule declineSessionWithDoctor(
      @RequestBody DoctorScheduleRequest scheduleRequest) {

    DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleRequest.getScheduleId())
        .orElseThrow(UnknownScheduleException::new);

    schedule.setFree(true);
    schedule.setPatientId(0);

    return doctorScheduleRepository.save(schedule);
  }

}
