package andrew.labs.service;


import andrew.labs.models.Schedule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private static final HashMap<Integer, Schedule>  SCHEDULE_REPOS_MAP = new HashMap<>();

    @Override
    public void create(Schedule schedule) {
        SCHEDULE_REPOS_MAP.put(schedule.getGroup(), schedule);
    }

    @Override
    public List<Schedule> readAll() {
        return new ArrayList<>(SCHEDULE_REPOS_MAP.values());
    }

    @Override
    public Schedule read(Integer idGroup) {
        return SCHEDULE_REPOS_MAP.get(idGroup);
    }

    @Override
    public boolean update(Schedule schedule, Integer idGroup) {
        if (SCHEDULE_REPOS_MAP.containsKey(idGroup)){
            SCHEDULE_REPOS_MAP.put(idGroup, schedule);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer idGroup) {
        return SCHEDULE_REPOS_MAP.remove(idGroup) != null;
    }
}
