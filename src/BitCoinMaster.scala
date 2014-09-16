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
    

  if(remoteHostPort == "127.0.0.1:2552"){
    val system = ActorSystem("MasterSystem", ConfigFactory.load("Master.conf"))
    	val MiningActor1 = system.actorOf(Props[MiningActor], name = "MiningActor1")
    	MiningActor1 ! start(zeroesExpected)
    
  }
  
  else{
    val system = ActorSystem("LocalSystem", ConfigFactory.load("Local.conf"))
    val remotePath = s"akka.tcp://MasterSystem@$remoteHostPort/user/MiningActor"
    val remote = system.actorSelection(remotePath)
			remote ! "START"
  }
  
   //system.actorOf(BitCoinMaster.props(remotePath, zeroesExpected, remoteHostPort), "snd")
   }

  def props(path: String, zeroesExpected: Int, remoteHostPort: String): Props =
    Props(new BitCoinMaster(path, zeroesExpected, remoteHostPort))
   
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


class BitCoinMaster(path: String, zeroesExpected: Int, remoteHostPort:String) extends Actor {

   
  def receive={
    case "RECEIVE" =>
     		println("Hello from MiningActor")
  }
    //Create a actor system
    val system = ActorSystem("LocalSystem", ConfigFactory.load("Local.conf"))    
    
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
  	  val remote = system.actorSelection(path)
			remote ! "START"
  	  println("Trying just remote")
  	  
		}
  		else{
			val remote = system.actorSelection(path)
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

