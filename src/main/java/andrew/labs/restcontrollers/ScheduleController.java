package andrew.labs.restcontrollers;

import andrew.labs.models.Schedule;
import andrew.labs.service.ScheduleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api(value = "/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping(value = "/schedule")
    @ApiOperation(
            value = "Добавить расписание",
            httpMethod = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 201, message = "Расписание добавлено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> create(@RequestBody Schedule schedule){
        scheduleService.create(schedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/show")
    @ApiOperation(
            value = "Просмотр расписание",
            httpMethod = "GET",
            produces = "application/json",
            response = Schedule.class,
            responseContainer = "List"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Расписание не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<List<Schedule>> read(){
        final List<Schedule> weeks = scheduleService.readAll();

        return weeks != null && !weeks.isEmpty()
                ? new ResponseEntity<>(weeks, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/show/{id}")
    @ApiOperation(
            value = "Просмотр расписания группы",
            httpMethod = "GET",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Расписание группы не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<Schedule> read(
            @ApiParam(
                    value = "номер группы",
                    name = "group",
                    required = true,
                    example = "4032"
            )
            @PathVariable(name = "id") Integer id){
        final Schedule schedule = scheduleService.read(id);

        return schedule != null
                ? new ResponseEntity<>(schedule, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/show/{id}")
    @ApiOperation(
            value = "Обновление расписания для группы",
            httpMethod = "PUT",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Группа не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> update(
            @ApiParam(
                    value = "номер группы",
                    name = "group",
                    required = true,
                    example = "4032"
            )
            @PathVariable(name = "id") Integer id,
            @ApiParam(
                    value = "расписание",
                    name = "schedule",
                    required = true
            )
            @RequestBody Schedule schedule){
        final boolean updated = scheduleService.update(schedule, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/show/{id}")
    @ApiOperation(
            value = "Удаление расписания для группы",
            httpMethod = "DELETE",
            produces = "application/json",
            response = String.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Группа не найдено"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка")
    })
    public ResponseEntity<?> delete(
            @ApiParam(
                    value = "номер группы",
                    name = "group",
                    required = true,
                    example = "4032"
            )
            @PathVariable(name = "id") Integer id){
        final boolean deleted = scheduleService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
