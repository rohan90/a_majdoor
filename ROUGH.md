##Just a dump for thoughts so as to not loose focus/track

api
		1. create task with payload () (possibly with schedulerId)
		2. get tasks
			1. all
			2. by status
		3. payload 
					id,name,description
					type (immediate/cron)
					operation?
					
scheduler
	base
		register itself along with possible nodes.
		the nodes run the executors.
	presence
		consumer/producers
			1. getting all tasks from repo
			2. creating queue
			3. listening to task complete events?
	operator

tasks
	master
		all meta related to task
	child
		all meta related to master and execution logic.	
		