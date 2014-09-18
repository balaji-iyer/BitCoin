BitCoin
=======
The goal is to mine bitcoins using akka actor model so that it runs well on multiple core machines.

Team members:
=======
Garima Singh (UFID : 5197-5877)
Balaji Iyer  (UFID : 0443-5000)

Prerequisites:
=======
Scala 2.11.2
Akka 2.3.6 distribution for Scala 2.11

Instructions to execute:
=======
Compile:
	scalac -cp  "../lib/scala-library-2.11.2.jar:../lib/akka/*:." BitCoinMaster.scala

Run:
	Server Mode: scala -cp  "../lib/scala-library-2.11.2.jar:../lib/akka/*:." BitCoinMaster <number of zeros>
	Client mode: scala -cp  "../lib/scala-library-2.11.2.jar:../lib/akka/*:." BitCoinMaster <server ip>

Observations:
=======

1. Size of the work unit:



2. For : scala project1.scala 4
	Result file: 4_zeroes.txt
	actorCount is 8
	Total Number of bitcoins mined is 586
	Total number of messages processed is 37700000
	Throughtput is 26.71716296453235 messages/sec
	2095.64user 24.28system 5:04.34elapsed 696%CPU (0avgtext+0avgdata 228476maxresident)k
	0inputs+976outputs (0major+74386minor)pagefaults 0swaps

   For : scala project1.scala 5
    Result file: 5_zeroes.txt
    actorCount is 8
	Total Number of bitcoins mined is 27
	Total number of messages processed is 37000000
	Throughtput is 26.221078732160073 messages/sec
	2000.01user 26.43system 5:04.64elapsed 665%CPU (0avgtext+0avgdata 243052maxresident)k
	0inputs+608outputs (0major+93682minor)pagefaults 0swaps



3. The coin with the most 0s :

4. The largest number of working machines you were able to run your code with : 12






