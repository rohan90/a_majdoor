# Majdoor
An attempt at a distributed task scheduler.
#### postman link
[Postman](https://www.getpostman.com/collections/928aa000f2cbd1e70a14)

#### build
1. build project with `mvn clean install`
2. run locally with `mvn spring-boot:run -Pdev`

#### SLAs
1. Api 
    - rest-apis for creating and getting tasks
2. Scheduler
    - module for scheduling and polling tasks to shchedule
3. db 
    - in_memory and persistence implementations/stubs
        which help in storing and coordinating an execution cache.
4. executors
    - various task type operators and their executors(runners)
5. *.properties file
    - changing no of nodes, pollDelay, poolSize for schedulers.
6. Tests
    1. Unit tests
    2. integration tests
        - api
        - scheduler
    2. sanity test
        - an overall sanity of the system validated by apis.

#### todos
1. possibly attach a real sql instance instead of h2-console (in memory, very lazy, just need to implement spring jdbc for staging/prod profile)
2. master <-> child task. (for now theirs just one task template, no master/child)
3. Unit tests for other classes. (right now only for SchedulerImpl exists)

#### known bugs/shortcomings
1. Datacache ie task pool is inmemory and held by the service instance.
    (it should be global and shared, coordinated by all service instances)
