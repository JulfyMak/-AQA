package com.karazin.maltsev.api;

import com.karazin.maltsev.entity.DoctorSchedule;
import com.karazin.maltsev.entity.Patient;
import com.karazin.maltsev.repository.DoctorScheduleRepository;
import com.karazin.maltsev.repository.PatientRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

  private final DoctorScheduleRepository doctorScheduleRepository;
  private final PatientRepository patientRepository;

  @GetMapping("/{id}/schedules")
  public List<DoctorSchedule> fetchSchedulesForPatient(@PathVariable Long id) {
    return doctorScheduleRepository.findAllByPatientId(id);
  }

  @GetMapping("/")
  public List<Patient> fetchAllPatients() {
    return patientRepository.findAll();
  }
}
