package com.rohan90.majdoor.utils;

import com.rohan90.majdoor.api.tasks.domain.dtos.CreateTaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.ScheduleMetaDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskOperatorDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.UserDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.executor.operators.OperatorType;

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
}
