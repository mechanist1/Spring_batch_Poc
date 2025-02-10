# Spring_batch_Poc
this is my batch processing POC , i am treating abt 2000 lines of Employees and i want to excute it in a reasonable time , we set up the item reader, item writer,and item processor, we set the Step to run those tasks and call the job that should take care of the Step , a job can have multiple steps,i also used taskExcutor to parallelize the excution of these tasks.
