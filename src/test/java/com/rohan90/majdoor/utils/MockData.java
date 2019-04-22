package com.rohan90.majdoor.utils;

import com.rohan90.majdoor.api.tasks.domain.dtos.*;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.executor.operators.OperatorType;

import java.util.ArrayList;
import java.util.List;

public class MockData {
    public static CreateTaskDTO getCreateTaskDTO() {
        long timestamp = System.currentTimeMillis();
        CreateTaskDTO data = new CreateTaskDTO();
        data.setCreatedBy(new UserDTO("Mock_User_"+timestamp,100l));
        data.setName("Mock task "+timestamp);
        data.setDescription("Mock description "+timestamp);
        data.setScheduleMeta(new ScheduleMetaDTO(ScheduleType.IMMEDIATE,String.valueOf(0)));
        data.setOperator(new TaskOperatorDTO(OperatorType.PRINT,"hello world"));
        return data;
    }

    public static TaskOperatorDTO getSmsOperatorDTO() {
        TaskOperatorDTO data = new TaskOperatorDTO();
        data.setType(OperatorType.SMS);
        data.setValue(new SmsOperatorPayload("Some message","99999999"));
        return data;
    }

    public static List<TaskDTO> getTaskDTOs(ScheduleType type, int count) {
        List<TaskDTO> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(getTaskDTO(type));
        }
        return data;
    }

    private static TaskDTO getTaskDTO(ScheduleType type) {
        TaskDTO data = new TaskDTO();
        data.setId(100);
        data.setName("Mock name");
        data.setDescription("Mock description");
        data.setScheduleMeta(new ScheduleMetaDTO(type,"1"));
        data.setOperator(getSmsOperatorDTO());
        //for now dont need to set the other variables
        return data;
    }
}
