/**
 * Code to generate SHA256 hash
 * Input : String
 */

import BearerTokenGenerator._
import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.actors.Actor.self
import scala.actors.TIMEOUT


case object execute
case object interrupt
case object done
case class bitcoin(input :String,output : String)
case object terminate


case object rmsg
case object msg
case object Test
case object sendzeroes
case object getNoOfZeroes

object BitCoinMaster{
   def main(args: Array[String]): Unit = { 
  //Accept number of zeroes and ip address arguments from command line
  
  var param =  args(0)
  var remoteHost=""
  var zeroesExpected=0
  if(param.toString().contains('.')){
 remoteHost = param.toString()  
  }
  else{
 zeroesExpected = args(0).toInt
 remoteHost = "127.0.0.1" 
  }
 
  
 
  //2nd method
  if(remoteHost == "127.0.0.1"){
    val masterSystem = ActorSystem("MasterSystem", ConfigFactory.load("Master.conf"))
    val master=masterSystem.actorOf(Props(new Master(zeroesExpected,remoteHost)),name="Master") 
    val processors = Runtime.getRuntime().availableProcessors()+Runtime.getRuntime().availableProcessors()
   
    //val actor1 = masterSystem.actorOf(Props(new MiningActor(remoteHost,master)),name="Test")
    //actor1! execute(zeroesExpected)
  
    for(i<- 0 to processors){
    	val actorName="MiningActor"+i.toString()
    	var actor = masterSystem.actorOf(Props(new MiningActor(remoteHost,master,zeroesExpected)), name = actorName)
    	actor! execute     
}
    val timer = masterSystem.actorOf(Props(new Timer(master)), "timer")
   }
    else{
    val localSystem = ActorSystem("LocalSystem",ConfigFactory.load("Local.conf"))
    val processors = Runtime.getRuntime().availableProcessors()+Runtime.getRuntime().availableProcessors()
    for(i<- 0 to processors){
        val actorName="MiningActor"+i.toString()
        var actor = localSystem.actorOf(Props(new RemoteMiningActor(remoteHost)), name = actorName)
        actor ! getNoOfZeroes
        //actor! execute
 
} 
   
  } 
  
  } 
   
   
}

class Master(zeroesExpected: Int, remoteHost:String) extends Actor {

var hashMap = Map.empty[String, String]
var totalMessages=0
var flag=false
var actorCount=8
def receive={
case `interrupt` =>
      {
    	  if(flag==false){  
    		  totalMessages+=1;
    		  sender ! execute
    	  }
    	  else
    	  {
    		  totalMessages+=1;
    		  sender ! terminate
    	  }
     
      }
    case bitcoin(input,output) =>
      {
      //println("Hello from MiningActor")
      hashMap+= (input->output)
      }
     
    case `done` => 
      				{ actorCount+=1
      				  //if(actorCount==10)
      				 println(totalMessages)
      				 //Thread.sleep(100)
      				 context.system.shutdown()
      				}
        
    case `terminate` => flag=true
    
    case sendzeroes => sender ! zeroesExpected
         
  }
}

  
  
 class MiningActor(remoteHost : String, masterRef : ActorRef,z : Int) extends Actor{
   
  var flag = true 
  var messageCount=0
  
  var counter=0
  def receive ={
      case `execute` =>
        {
      //for(i<- 0 to 10000000){
        			val input = BearerTokenGenerator.getToken()
        			val hashOutput = BearerTokenGenerator.generateSHAToken(input)
        			if (Common.isBitCoin(hashOutput,z)){
        				masterRef! bitcoin(input,hashOutput)
        				println("bitcoin:"+input+"\thash:"+ hashOutput)
        			}
     
        			masterRef ! interrupt
        }
     
     
    case `msg` =>
      println(s"Actor received message")
      sender ! rmsg
     
    case terminate =>
      {masterRef ! done
       context.stop(self)
      //context.system.shutdown()
      }
                  
   
  }
  
}

 class RemoteMiningActor(remoteHost : String) extends Actor{
   
  var flag = true 
  var messageCount=0
  val masterRef=context.actorSelection(s"akka.tcp://MasterSystem@$remoteHost:2552/user/Master")
  //println(remoteHost)
  var counter=0
  var zeroes=0
  def receive ={
      case `execute` =>
        {
      //for(i<- 0 to 10000000){
        			val input = BearerTokenGenerator.getToken()
        			val hashOutput = BearerTokenGenerator.generateSHAToken(input)
        			//println("The real zeroes",zeroes)
        			if (Common.isBitCoin(hashOutput,zeroes)){
        				masterRef! bitcoin(input,hashOutput)
        				println("bitcoin:"+input+"\thash:"+ hashOutput)
        			}
        			masterRef ! interrupt
        }
     
     
    case `msg` =>
      println(s"Actor received message")
      sender ! rmsg
     
    case `terminate` =>
      {masterRef ! done
       context.stop(self)
      //context.system.shutdown()
      }
   
    case `getNoOfZeroes` => masterRef ! sendzeroes        
    
    case z:Int => 
                  { zeroes=z
                    self!execute 
                  }
   
  }
  
}
 
 class Timer(masterRef : ActorRef) extends Actor{
	var startTime = System.currentTimeMillis()
	var flag=true
	while(flag==true)
	{
	  var currentTime = System.currentTimeMillis()
	  
	  if(currentTime >= startTime+60000)
	  {
	    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
	    flag=false
	    masterRef ! terminate
	  }
	}
	def receive={
	  case _=> println("Default")
	}
	
}