package com.rohan90.majdoor.api.utils.rest;

import com.rohan90.majdoor.api.common.RestResponse;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDashboardDTO;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;

public class RestParser {

    public static TaskDTO getTaskDTO(Response response) {
        return response.as(new TypeRef<RestResponse<TaskDTO>>() {
        }).getData();
    }


    public static List<TaskDTO> getTaskDTOs(Response response) {
        return response.as(new TypeRef<RestResponse<List<TaskDTO>>>() {
        }).getData();
    }

    public static TaskDashboardDTO getTaskDashboardDTO(Response response) {
        return response.as(new TypeRef<RestResponse<TaskDashboardDTO>>() {
        }).getData();
    }
}
