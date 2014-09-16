/**
 * Code to generate SHA256 hash
 * Input : String
 */

import BearerTokenGenerator._
import akka.actor._
import com.typesafe.config.ConfigFactory


case class start(zeroes :Int)

case class Greeting(message: String)

object BitCoinMaster{
   def main(args: Array[String]): Unit = { 
  //Accept number of zeroes and ip address arguments from command line
  val zeroesExpected = args(0).toInt
  val remoteHostPort = if (args.length >= 2) args(1) else "127.0.0.1:2552"
    val remotePath = s"akka.tcp://MasterSystem@$remoteHostPort/user/MiningActor"
   if(remoteHostPort == "127.0.0.1:2552"){
  val system = ActorSystem("MasterSystem", ConfigFactory.load("Master.conf"))
  system.actorOf(BitCoinMaster.mprops(remotePath, zeroesExpected, remoteHostPort), "snd")
   }
   else{
     val system = ActorSystem("LocalSystem", ConfigFactory.load("Local.conf"))
     system.actorOf(BitCoinMaster.sprops(remotePath, zeroesExpected, remoteHostPort), "snd")
   }
   
   }

  def mprops(path: String, zeroesExpected: Int, remoteHostPort: String): Props =
    Props(classOf[MBitCoin],path, zeroesExpected, remoteHostPort)
  def sprops(path: String, zeroesExpected: Int, remoteHostPort: String): Props =
    Props(classOf[SBitCoin],path, zeroesExpected, remoteHostPort)
   
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


class MBitCoin(path: String, zeroesExpected: Int, remoteHostPort:String) extends Actor {

   
  def receive={
    case "RECEIVE" =>
     		println("Hello from MiningActor")
  }
    //Create a actor system
    //val systemTest = ActorSystem("System", ConfigFactory.load("Local.conf"))    
    
  	if(remoteHostPort == "127.0.0.1:2552")
		{
  	  /*
			val processors = Runtime.getRuntime().availableProcessors()+4
			
			for(i<- 0 to processors){
			  val actorName="MiningActor"+i.toString()
			  var actor = system.actorOf(Props[MiningActor], name = actorName)
			  actor! start(zeroesExpected)
			  
			}
			*/
  	  //println("Trying just remote")
  	  //val remote = context.actorSelection(path)
		//	remote ! "START"
  	  
  		println("Local")
//  		println("Trying just remote")
//  		println(path)
		//val remote = context.actorSelection(s"akka.tcp://MasterSystem@10.136.14.46:2552/user/Mining")
		//remote ! "START"
  	  
		}
//  		else{
//  		  println("Trying just remote")
//  		  println(path)
//		  val remote = context.actorSelection(path)
//		  remote ! "START"
//  		}
			    
    		//self! "RECEIVE"
}

class SBitCoin(path: String, zeroesExpected: Int, remoteHostPort:String) extends Actor {

   
  def receive={
    case "RECEIVE" =>
     		println("Hello from MiningActor")
  }
    //Create a actor system
    //val systemTest = ActorSystem("System", ConfigFactory.load("Local.conf"))    
    
  	if(remoteHostPort != "127.0.0.1:2552")
		{
  	  /*
			val processors = Runtime.getRuntime().availableProcessors()+4
			
			for(i<- 0 to processors){
			  val actorName="MiningActor"+i.toString()
			  var actor = system.actorOf(Props[MiningActor], name = actorName)
			  actor! start(zeroesExpected)
			  
			}
			*/
  	  //println("Trying just remote")
  	  //val remote = context.actorSelection(path)
		//	remote ! "START"
  	  
  		
  		println("Trying just remote")
  		println(path)
		val remote = context.actorSelection(s"akka.tcp://MasterSystem@10.136.14.46:2552/user/RemoteMiningActor")
		remote ! "START"
  	  
		}
  	else{
  		 println("Trying just remote")
  		 println(path)
		 val remote = context.actorSelection(path)
		 remote ! "START"
  		}
			    
    		//self! "RECEIVE"
}


class MiningActor extends Actor{
  //val remote = context.actorSelection("akka.tcp://MasterSystem@"+ip+":2552/user/RemoteMiningActor")
  var counter=0
  def receive ={
    case "START" =>
      		println("Abe hoja")
    case msg : String=>
      		println(s"LocalActor received message:'$msg'");
      		if(counter<5){
      			sender ! "Hello back to you"
      			counter += 1
      		}
    case start(z) =>
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
  
 class RemoteMiningActor extends Actor{
  //val remote = context.actorSelection("akka.tcp://MasterSystem@"+ip+":2552/user/RemoteMiningActor")
  var counter=0
  def receive ={
    case "START" =>
      		println("Abe hoja")
    case msg : String=>
      		println(s"LocalActor received message:'$msg'");
      		if(counter<5){
      			sender ! "Hello back to you"
      			counter += 1
      		}
    case start(z) =>
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

