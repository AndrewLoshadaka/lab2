package andrew.labs.service;

import andrew.labs.models.Schedule;

import java.util.List;

public interface ScheduleService {
    void create(Schedule schedule);
    //возврат всего расписания

    List<Schedule> readAll();

    //расписание для группы
    Schedule read(Integer idGroup);

    boolean update(Schedule schedule, Integer idGroup);

    boolean delete(Integer idGroup);
}
