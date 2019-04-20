package com.rohan90.majdoor.api.tasks;

import com.rohan90.majdoor.api.tasks.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<Task,Long> {
}
