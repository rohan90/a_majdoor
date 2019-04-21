package com.rohan90.majdoor.utils.validators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohan90.majdoor.api.tasks.domain.dtos.*;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import com.rohan90.majdoor.executor.operators.OperatorType;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskAssertions {
    public static void assertTaskCreated(CreateTaskDTO request, TaskDTO response) {
        assertTrue(response.getId() > 0);
        assertTrue(response.getLastExecuted() == 0);
        assertEquals(TaskStatus.CREATED, response.getStatus());

        assertEquals(request.getName(), response.getName());
        assertEquals(request.getDescription(), response.getDescription());
        assertTaskOperatorIsSame(request.getOperator(), response.getOperator());
        assertCreatedByIsSame(request.getCreatedBy(), response.getCreatedBy());
        assertScheduleMetaIsSame(request.getScheduleMeta(), response.getScheduleMeta());
    }

    private static void assertScheduleMetaIsSame(ScheduleMetaDTO sm1, ScheduleMetaDTO sm2) {
        assertEquals(sm1.getType(), sm2.getType());
        assertEquals(sm1.getValue(), sm2.getValue());
    }

    private static void assertCreatedByIsSame(UserDTO u1, UserDTO u2) {
        assertEquals(u1.getId(), u2.getId());
        assertEquals(u1.getName(), u2.getName());
    }

    private static void assertTaskOperatorIsSame(TaskOperatorDTO o1, TaskOperatorDTO o2) {
        //todo extract this into more , maybe a switch
        assertEquals(o1.getType(), o2.getType());

        //assertingOperatorPayloads
        //todo find a better way to do this T_T
        if (o1.getValue().equals(OperatorType.PRINT)) {
            assertEquals(o1.getValue(), o2.getValue());
        } else if (o1.getType().equals(OperatorType.SMS)) {
            SmsOperatorPayload v1 = (SmsOperatorPayload) o1.getValue();
            try {
                String o2Json = new ObjectMapper().writeValueAsString(o2.getValue());
                o2.setValue(new ObjectMapper().readValue(o2Json, SmsOperatorPayload.class));
                SmsOperatorPayload v2 = (SmsOperatorPayload) o2.getValue();
                assertEquals(v1.getMessage(), v2.getMessage());
                assertEquals(v1.getPhoneNumber(), v2.getPhoneNumber());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("parsing operator failure");
            }
        }
    }
}
