/**
 * Code to generate SHA256 hash
 * Input : String
 */

import BearerTokenGenerator._
import akka.actor._
import com.typesafe.config.ConfigFactory

class BitCoinMaster(path: String, zeroesExpected: Int, remotePath: String) extends Actor {

  def receive={
    case "START" =>
      		println("Hello from LocalActor")
    
  }
     
    //Create a actor system
    //val system = ActorSystem("MasterSystem")
    //val remoteActor = system.actorOf(Props[MiningActor], name = "RemoteActor1")
    //remoteActor ! "The RemoteActor is alive"    
}

object Master{
   def main(args: Array[String]): Unit = { 
  //Accept number of zeroes and ip address arguments from command line
  val zeroesExpected = args(0).toInt
  val remoteHostPort = if (args.length >= 1) args(1) else "127.0.0.1:2552"
    
  val system = ActorSystem("MasterSystem", ConfigFactory.load("Master.conf"))    	
  val remotePath = s"akka.tcp://MasterSystem@$remoteHostPort/user/MiningActor"

//  if(remoteHostPort == "127.0.0.1:2552"){
//  	 val local = context.actorSelection(remotePath)
//  	 local ! Start(zeroesExpected)
//  }
//  else{
//    val remote=context.actorSelection(remotePath)
//  }
    
      system.actorOf(BitCoinMaster(remotePath, zeroesExpected, remotePath), "snd")

      
  
  /*if(remoteHostPort == "127.0.0.1:2552")
		{
			val processors = Runtime.getRuntime().availableProcessors()
			val MiningActor1 = system.actorOf(Props[LocalMiningActor], name = "MiningActor1")
			val MiningActor2 = system.actorOf(Props[LocalMiningActor], name = "MiningActor2")
			val MiningActor3 = system.actorOf(Props[LocalMiningActor], name = "MiningActor3")
			val MiningActor4 = system.actorOf(Props[LocalMiningActor], name = "MiningActor4")
			MiningActor1 ! Start(zeroesExpected)
			MiningActor2 ! Start(zeroesExpected)
			MiningActor3 ! Start(zeroesExpected)
			MiningActor4 ! Start(zeroesExpected)
		}
		*/

  }
  
}

class MiningActor(ip: String) extends Actor{
  val remote = context.actorSelection("akka.tcp://MasterSystem@"+ip+":2552/user/RemoteMiningActor")
  var counter=0
  def receive ={
    case "START" =>
      		remote ! "Hello from LocalActor"
    case msg : String=>
      		println(s"LocalActor received message:'$msg'");
      		if(counter<5){
      			sender ! "Hello back to you"
      			counter += 1
      		}
    case Start(z) =>
	    {
	      for(i<- 0 to 10000000)
	      {
	    	  var flag = true
	    	  val input = BearerTokenGenerator.getToken()
	    	  val hashOutput = BearerTokenGenerator.generateSHAToken(input)
	    	  if (Common.isBitCoin(hashOutput,z))
	    		  println("bitcoin:"+input+"\thash:"+ hashOutput)
	      }
	      
	    }		
  }
  
  
  
}

class RemoteMiningActor(ip: String) extends Actor{
  
  
  val remote = context.actorSelection("akka.tcp://MasterSystem@"+ip+":2552/user/RemoteMiningActor")
  var counter=0
  def receive ={
    case "START" =>
      		remote ! "Hello from LocalActor"
    case msg : String=>
      		println(s"LocalActor received message:'$msg'");
      		if(counter<5){
      			sender ! "Hello back to you"
      			counter += 1
      		}
    case Start(z) =>
	    {
	      for(i<- 0 to 10000000)
	      {
	    	  var flag = true
	    	  val input = BearerTokenGenerator.getToken()
	    	  val hashOutput = BearerTokenGenerator.generateSHAToken(input)
	    	  if (Common.isBitCoin(hashOutput,z))
	    		  println("bitcoin:"+input+"\thash:"+ hashOutput)
	      }
	      
	    }		
  }

}