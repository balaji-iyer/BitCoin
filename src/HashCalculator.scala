//import akka.actor.Actor
//import akka.actor.ActorSystem
//import akka.actor.Props
//
//
//
//case class Start(z:Int)
//
//class HashCalculator extends Actor{
//
//	def receive =
//	{
//	  case Start(z) =>
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
//	    	
//	}
//}
//
//object Master
//{
//	def main(args: Array[String]) {
//		val zeroesExpected = args(0).toInt
//		val remoteHostPort = if (args.length >= 1) args(1) else "127.0.0.1:2552"
//		//println("remoteHostPort:"+remoteHostPort);
//		  
//		val system = ActorSystem("hashCalculatorSystem")
//		if(remoteHostPort == "127.0.0.1:2552")
//		{
//			val processors = Runtime.getRuntime().availableProcessors()
//			val hashCalculator1 = system.actorOf(Props[HashCalculator], name = "hashCalculator1")
//			val hashCalculator2 = system.actorOf(Props[HashCalculator], name = "hashCalculator2")
//			val hashCalculator3 = system.actorOf(Props[HashCalculator], name = "hashCalculator3")
//			val hashCalculator4 = system.actorOf(Props[HashCalculator], name = "hashCalculator4")
//			hashCalculator1 ! Start(zeroesExpected)
//			hashCalculator2 ! Start(zeroesExpected)
//			hashCalculator3 ! Start(zeroesExpected)
//			hashCalculator4 ! Start(zeroesExpected)
//		}  
//		
//		
//	
//	}
//  
//	
//}