package com.rohan90.majdoor.ut;

import com.rohan90.majdoor.api.tasks.domain.dtos.TaskDTO;
import com.rohan90.majdoor.api.tasks.domain.models.ScheduleType;
import com.rohan90.majdoor.api.tasks.domain.models.TaskStatus;
import com.rohan90.majdoor.db.in_memory.ICacheClient;
import com.rohan90.majdoor.db.persistence.IDbClient;
import com.rohan90.majdoor.executor.runners.FutureTaskRunner;
import com.rohan90.majdoor.executor.runners.ImmediateTaskRunner;
import com.rohan90.majdoor.scheduler.DataPoller;
import com.rohan90.majdoor.scheduler.SchedulerImpl;
import com.rohan90.majdoor.scheduler.events.TaskUpdateStatusEvent;
import com.rohan90.majdoor.utils.MockData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Executors.class, SchedulerImpl.class})
public class SchedulerTest {

    @Mock
    ICacheClient mockCacheClient;

    @Mock
    IDbClient mockDbClient;

    @Mock
    DataPoller mockDataPoller;

    @Mock
    ScheduledExecutorService mockSchedulerExecutorService;

    private SchedulerImpl scheduler;
    private int PARALLELISM_COUNT = 1;
    private int POLL_DELAY = 10;
    private String SCHEDULER_NAME = "mockSchedulerName";

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(Executors.class);

        PowerMockito.when(Executors.newScheduledThreadPool(Mockito.anyInt())).thenReturn(mockSchedulerExecutorService);
        PowerMockito.whenNew(DataPoller.class).withAnyArguments().thenReturn(mockDataPoller);

        scheduler = new SchedulerImpl();
        scheduler.configure(PARALLELISM_COUNT, POLL_DELAY, mockDbClient, mockCacheClient);
        scheduler.identity(SCHEDULER_NAME);
    }

    @Test
    public void shouldStartCorrectly() {
        scheduler.start();

        assertEquals(SCHEDULER_NAME, scheduler.identify());

        Mockito.verify(mockDataPoller).start();

    }

    @Test
    public void shouldStopCorrectly() {
        scheduler.start();
        scheduler.stop();

        Mockito.verify(mockDataPoller).stopPolling();
        Mockito.verify(mockSchedulerExecutorService).shutdown();
        Mockito.verify(mockCacheClient).clear();

    }

    @Test
    public void shoudlNotScheduleAnyTasksWhenNoDataReturnedFromDataPolling(){
        Mockito.when(mockDataPoller.getTasks()).thenReturn(new ArrayList());

        scheduler.start();
        scheduler.runTasks(); //this is called internally from the datapoller hence invoking here

        Mockito.verify(mockDataPoller).start();
        Mockito.verify(mockDataPoller).getTasks();
        Mockito.verifyNoMoreInteractions(mockDataPoller,mockCacheClient,mockSchedulerExecutorService);
    }

    @Test
    public void shouldScheduleImmediateTask(){
        List<TaskDTO> taskDTOs = MockData.getTaskDTOs(ScheduleType.IMMEDIATE,1);
        Mockito.when(mockDataPoller.getTasks()).thenReturn(taskDTOs);
        Mockito.when(mockCacheClient.contains(Mockito.anyString())).thenReturn(false);


        scheduler.start();
        scheduler.runTasks(); //this is called internally from the datapoller hence invoking here

        Mockito.verify(mockDataPoller).start();
        Mockito.verify(mockDataPoller).getTasks();
        Mockito.verify(mockSchedulerExecutorService).schedule(Mockito.any(ImmediateTaskRunner.class),Mockito.eq(1l),Mockito.eq(TimeUnit.SECONDS));
        Mockito.verify(mockCacheClient).put(String.valueOf(taskDTOs.get(0).getId()),taskDTOs.get(0));
    }

    @Test
    public void shouldScheduleFutureTask(){
        List<TaskDTO> taskDTOs = MockData.getTaskDTOs(ScheduleType.FUTURE,1);
        Mockito.when(mockDataPoller.getTasks()).thenReturn(taskDTOs);
        Mockito.when(mockCacheClient.contains(Mockito.anyString())).thenReturn(false);


        scheduler.start();
        scheduler.runTasks(); //this is called internally from the datapoller hence invoking here

        Mockito.verify(mockDataPoller).start();
        Mockito.verify(mockDataPoller).getTasks();
        //i should proabably extract that long a** inline variable to a local variable for the Mock.eq
        Mockito.verify(mockSchedulerExecutorService).schedule(Mockito.any(FutureTaskRunner.class),Mockito.eq(TaskDTO.getDelayInMillis(taskDTOs.get(0).getScheduleMeta().getValue())),Mockito.eq(TimeUnit.SECONDS));
        Mockito.verify(mockCacheClient).put(String.valueOf(taskDTOs.get(0).getId()),taskDTOs.get(0));
    }

    @Test
    public void shouldNotScheduleATaskIfAlreadyScheduled(){
        List<TaskDTO> taskDTOs = MockData.getTaskDTOs(ScheduleType.IMMEDIATE,1);
        Mockito.when(mockDataPoller.getTasks()).thenReturn(taskDTOs);
        Mockito.when(mockCacheClient.contains(Mockito.anyString())).thenReturn(true);


        scheduler.start();
        scheduler.runTasks(); //this is called internally from the datapoller hence invoking here

        Mockito.verify(mockDataPoller).start();
        Mockito.verify(mockDataPoller).getTasks();

        Mockito.verifyNoMoreInteractions(mockDataPoller,mockSchedulerExecutorService);

    }

    @Test
    public void shouldUpdateTaskCorrectly(){
        TaskStatus mockStatus = TaskStatus.COMPLETED;
        TaskDTO mockTaskDTO= MockData.getTaskDTOs(ScheduleType.IMMEDIATE,1).get(0);
        TaskUpdateStatusEvent mockTaskUpdateEvent = new TaskUpdateStatusEvent(this, mockStatus, mockTaskDTO);

        scheduler.start();
        scheduler.updateTask(mockTaskUpdateEvent);

        Mockito.verify(mockDbClient).updateTaskStatus(mockStatus,mockTaskDTO.getId());
        Mockito.verify(mockCacheClient).remove(String.valueOf(mockTaskDTO.getId()));
    }
}
