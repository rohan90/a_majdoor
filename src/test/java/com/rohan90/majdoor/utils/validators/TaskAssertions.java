package com.rohan90.majdoor.utils.validators;

import com.rohan90.majdoor.api.tasks.domain.dtos.*;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;

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
        assertEquals(o1.getType(), o2.getType());
        assertEquals(o1.getValue(), o2.getValue());
    }
}
