/**
 * Code to generate SHA256 hash
 * Input : String
 */

import BearerTokenGenerator._
import akka.actor._
import com.typesafe.config.ConfigFactory

object BitCoinMaster {

  def main(args: Array[String]): Unit = {}
  	/**
  	 * Test code for a particular string
  	 */
    val userName = "COP5615 is a boring clas"
    val sha1 = generateSHA256Token(userName);
    println( "SHA1: " + sha1 )
     
    //Create a actor system
    val system = ActorSystem("MasterSystem")
    val remoteActor = system.actorOf(Props[MiningActor], name = "RemoteActor1")
    remoteActor ! "The RemoteActor is alive"
    
    
    
    
    
}

class MiningActor extends Actor{
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@10.136.79.71:2552/user/RemoteActor")
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
  }
  
}

