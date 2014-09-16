/**
 * Code to generate SHA256 hash
 * Input : String
 */

import BearerTokenGenerator._
import akka.actor._
import com.typesafe.config.ConfigFactory



object Master{
   def main(args: Array[String]): Unit = { 
  //Accept number of zeroes and ip address arguments from command line
  val zeroesExpected = args(0).toInt
  val remoteHostPort = if (args.length >= 2) args(1) else "127.0.0.1:2552"
    
  val system = ActorSystem("MasterSystem", ConfigFactory.load("Master.conf"))    	
  val remotePath = s"akka.tcp://MasterSystem@$remoteHostPort/user/MiningActor"

  
   system.actorOf(Master.props(remotePath, zeroesExpected, remoteHostPort, remotePath), "snd")

//  if(remoteHostPort == "127.0.0.1:2552"){
//  	 val local = context.actorSelection(remotePath)
//  	 local ! Start(zeroesExpected)
//  }
//  else{
//    val remote=context.actorSelection(remotePath)
//  }
    
   }

  def props(path: String, zeroesExpected: Int, remoteHostPort: String, remotePath: String): Props =
    Props(new BitCoinMaster(path, zeroesExpected, remoteHostPort, remotePath))
   
}
  
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


class BitCoinMaster(path: String, zeroesExpected: Int, remoteHostPort:String, remotePath: String) extends Actor {

  def receive={
    case "START" =>
     		println("Hello from MiningActor")
  }
    //Create a actor system
    val system = ActorSystem("MasterSystem")
    //val testActor = system.actorOf(Props[MiningActor], name = "testActor1")
    
//  	if(remoteHostPort == "127.0.0.1:2552")
//		{
//			val processors = Runtime.getRuntime().availableProcessors()
//			val MiningActor1 = system.actorOf(Props[MiningActor], name = "MiningActor1")
//			
//			
//			
//			MiningActor1 ! "START"
//			
//			
//		}
  
    self !"START"
    
}

class MiningActor(ip:String) extends Actor{
  //val remote = context.actorSelection("akka.tcp://MasterSystem@"+ip+":2552/user/RemoteMiningActor")
  var counter=0
  def receive ={
    case "START" =>
      		sender ! "START"
    case msg : String=>
      		println(s"LocalActor received message:'$msg'");
      		if(counter<5){
      			sender ! "Hello back to you"
      			counter += 1
      		}
//    case start : Int =>
//	    {
//	      for(i<- 0 to 10000000)
//	      {
//	    	  var flag = true
//	    	  val input = BearerTokenGenerator.getToken()
//	    	  val hashOutput = BearerTokenGenerator.generateSHAToken(input)
//	    	  if (Common.isBitCoin(hashOutput,z))
//	    		  println("bitcoin:"+input+"\thash:"+ hashOutput)
//	      }
//	      
//	    }		
  }
  
  
  
}

