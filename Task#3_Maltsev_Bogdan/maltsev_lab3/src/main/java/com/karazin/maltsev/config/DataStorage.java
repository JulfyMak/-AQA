package com.karazin.maltsev.config;

import com.karazin.maltsev.entity.DoctorSchedule;
import com.karazin.maltsev.entity.Doctor;
import com.karazin.maltsev.entity.Patient;
import com.karazin.maltsev.repository.DoctorRepository;
import com.karazin.maltsev.repository.DoctorScheduleRepository;
import com.karazin.maltsev.repository.PatientRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataStorage implements ApplicationListener<ApplicationReadyEvent> {

  private static final String DOCTOR_SCHEDULE_TABLE_NAME = "DOCTOR_SCHEDULE";
  private static final String DOCTOR_TABLE_NAME = "DOCTOR";
  private static final String PATIENT_TABLE_NAME = "PATIENT";
  private static final String POPULATING_TABLE_MSG = "Populating {} table";
  private static final String TABLE_POPULATED_MSG = "{} table has been populated with data";

  private final DoctorRepository doctorRepository;
  private final DoctorScheduleRepository doctorScheduleRepository;
  private final PatientRepository patientRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info(POPULATING_TABLE_MSG, DOCTOR_TABLE_NAME);
    doctorRepository.saveAll(buildDoctors());
    log.info(TABLE_POPULATED_MSG, DOCTOR_TABLE_NAME);

    log.info(POPULATING_TABLE_MSG, DOCTOR_SCHEDULE_TABLE_NAME);
    doctorScheduleRepository.saveAll(buildDoctorSchedules());
    log.info(TABLE_POPULATED_MSG, DOCTOR_SCHEDULE_TABLE_NAME);

    log.info(POPULATING_TABLE_MSG, PATIENT_TABLE_NAME);
    patientRepository.saveAll(buildPatients());
    log.info(TABLE_POPULATED_MSG, PATIENT_TABLE_NAME);
  }

  private List<Patient> buildPatients() {
    return List.of(
        Patient.builder()
            .firstName("Dima")
            .lastName("Maltsev")
            .build(),
        Patient.builder()
            .firstName("Patient1")
            .lastName("Todorov")
            .build(),
        Patient.builder()
            .firstName("Patient2")
            .lastName("Drushko")
            .build()
    );
  }

  private static List<DoctorSchedule> buildDoctorSchedules() {
    return List.of(
        // For doctor with id = 1
        DoctorSchedule.builder()
            .doctorId(1)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 10, 30))
            .build(),
        DoctorSchedule.builder()
            .doctorId(1)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 11, 30))
            .build(),
        DoctorSchedule.builder()
            .doctorId(1)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 12, 30))
            .build(),
        DoctorSchedule.builder()
            .doctorId(1)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 13, 30))
            .build(),
        DoctorSchedule.builder()
            .doctorId(1)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 14, 30))
            .build(),

        // For doctor with id = 2
        DoctorSchedule.builder()
            .doctorId(2)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 10, 30))
            .free(false)
            .patientId(2)
            .build(),
        DoctorSchedule.builder()
            .doctorId(2)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 11, 30))
            .free(false)
            .patientId(2)
            .build(),
        DoctorSchedule.builder()
            .doctorId(2)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 12, 30))
            .build(),
        DoctorSchedule.builder()
            .doctorId(2)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 13, 30))
            .free(false)
            .patientId(1)
            .build(),
        DoctorSchedule.builder()
            .doctorId(2)
            .time(LocalDateTime.of(2022, Month.MAY, 28, 14, 30))
            .build()
    );
  }

  private static List<Doctor> buildDoctors() {
    return List.of(
        Doctor.builder()
            .firstName("Bogdan")
            .lastName("Maltsev")
            .build(),
        Doctor.builder()
            .firstName("Sofiia")
            .lastName("Khalamai")
            .build()
    );
  }
}
