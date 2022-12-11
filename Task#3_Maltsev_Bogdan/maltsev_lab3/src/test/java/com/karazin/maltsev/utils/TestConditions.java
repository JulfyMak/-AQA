package com.karazin.maltsev.utils;

import com.karazin.maltsev.entity.DoctorSchedule;
import com.karazin.maltsev.entity.Doctor;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.assertj.core.api.Condition;

public class TestConditions {

  public static final Condition<Doctor> DOCTOR_NAME_IS_BOGDAN = new Condition<>(
      d -> d.getFirstName().equals("Bogdan"), "The name of Doctor is Bogdan"
  );

  public static final Condition<? super List<? extends Doctor>> DOCTORS_NAMES_ARE_BOGDAN
      = new Condition<>(doctors -> doctors.stream()
      .allMatch(d -> d.getFirstName().equals("Bogdan")),
      "The names of Doctors are Bogdan");

  public static final Condition<? super List<? extends DoctorSchedule>> DOCTOR_SCHEDULES_BY_DOCTOR_ID
      = new Condition<>(schedules -> schedules.stream()
      .allMatch(s -> s.getDoctorId() == 1),
      "Doctor schedules with doctor id = 1");

  public static final Condition<DoctorSchedule> SESSION_IS_SCHEDULED = new Condition<>(
      s -> !s.isFree()
          && s.getDoctorId() == 1L
          && s.getPatientId() == 1L
          && s.getTime().equals(LocalDateTime.of(2022, Month.MAY, 28, 10, 30)),
      "Session is scheduled for patient id = 1");

  public static final Condition<? super List<? extends DoctorSchedule>> DOCTOR_SCHEDULES_BY_DOCTOR_ID_AND_TIME_RANGE
      = new Condition<>(schedules -> schedules.stream()
      .allMatch(s -> s.getDoctorId() == 1
          && (s.getTime().isEqual(LocalDateTime.of(2022, Month.MAY, 22, 10, 30))
          || s.getTime().isAfter(LocalDateTime.of(2022, Month.MAY, 22, 10, 30)))
          && s.getTime().isBefore(LocalDateTime.of(2022, Month.MAY, 30, 10, 30))),
      "Doctor schedules with doctor id = 1 are in time range");

  public static final Condition<DoctorSchedule> SESSION_IS_DECLINED = new Condition<>(
      s -> s.isFree()
          && s.getScheduleId() == 7L
          && s.getPatientId() == 0L,
      "Session is scheduled for patient id = 1");

  public static final Condition<? super List<? extends DoctorSchedule>> SCHEDULES_FOR_PATIENT =
      new Condition<>(schedules -> schedules.stream().allMatch(s -> s.getPatientId() == 1L),
          "Schedules for patient id = 1");
}
