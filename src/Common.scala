import scala.util._

object Common {
	def isBitCoin(hashValue: String , zeroesExpected : Int):Boolean = {
		var flag = true
		for (index <- 0 to (zeroesExpected-1))
	    {
	    		  if(hashValue.charAt(index)!='0')
	    		  {
	    		    flag= false
	    		    return flag
	    		  }
	    		  
	    }
	    	  return flag
		}
}
