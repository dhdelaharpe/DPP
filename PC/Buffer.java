import org.jcsp.lang.*;
/*Buffer class
 *to terminate I took the approach of adding a signal message to be transfered after run is complete for the producer and consumer
 *I then continued the loop over alt until I have confirmed 4 processes to be complete (by detecting -1 being sent over the channel
 *Full output can be found in out2.txt
*/ 
public class Buffer implements CSProcess
{
	private AltingChannelInputInt input; //producer 
	private ChannelOutputInt output;//consumer
	private AltingChannelInputInt msg;//from consumer to prod
	
	//new channels for pair2
	private AltingChannelInputInt input2; //producer 
	private ChannelOutputInt output2;//consumer
	private AltingChannelInputInt msg2;//from consumer to prod

	final int PRODUCER=0;
	final int CONSUMER=1;
	final int PRODUCER2=2;
	final int CONSUMER2=3;

	private int[] buf = new int[10];//store messages
	private int buffCount=0;//track amount in buffer
	private int readOutCount=0;//track taken from buffer



	public Buffer(AltingChannelInputInt input, ChannelOutputInt output, AltingChannelInputInt msg, AltingChannelInputInt input2,ChannelOutputInt output2,AltingChannelInputInt msg2)
	{
		this.input=input;
		this.output=output;
		this.msg=msg;
		this.input2=input2;
		this.output2=output2;
		this.msg2=msg2;
	}//end dc

	public void run()
	{
		final Alternative alt = new Alternative(new Guard[] { input,msg,input2,msg2}); //alting object
		int done=0; //track progress
		while(done<4){//2 pairs of processes
		switch(alt.select())
		{
			case PRODUCER://read in msg
			if(buffCount<(readOutCount+10)){//buffer is full
				int ms = input.read();
				if(ms==-1){
					done++;//one process is done
				}else{
					buf[buffCount%10]=ms;
					buffCount++;
				}//else
			}//end if
			break;
			case PRODUCER2://read in msg
			if(buffCount<(readOutCount+10)){//buffer is full
				int ms = input2.read();
				if(ms==-1){
					done++;//one process is done
				}else{
					buf[buffCount%10]=ms;
					buffCount++;
				}//else
			}//end if
			break;
			case CONSUMER:
			if(readOutCount<buffCount){//stuff left to read
				int ms = msg.read();
				if(ms==-1){
					done++;//process is done
					break;
				}
				output.write(buf[readOutCount%10]); //using mod as anything over 100 should go back to position 1
				readOutCount++;
			}else if(done>=2){//producers are done so what about consumer?
				int ms = msg.read();
				if(ms==-1){
					done++;
				}
			}
			break;	
			
			case CONSUMER2:
			if(readOutCount<buffCount){//stuff left to read
				int ms = msg2.read();
				if(ms==-1){
					done++;//process is done
					break;
				}
				output2.write(buf[readOutCount%10]); //using mod as anything over 100 should go back to position 1
				readOutCount++;
			}else if(done>=2){//producers are done so what about consumer?
				int ms = msg2.read();
				if(ms==-1){
					done++;
				}
			}
			break;
		}//end switch
		//System.out.println("Buff:"+buffCount+"\t out:"+readOutCount+"\t Done:"+done);
		}//end while
	}//end run
}//end class buffer
