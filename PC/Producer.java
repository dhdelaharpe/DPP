import org.jcsp.lang.*;

/** Producer class: produces one random integer and sends on
  * output channel, then terminates.
  */
public class Producer implements CSProcess
  { private ChannelOutputInt channel;
	private int min;
	private int max;
    public Producer (final ChannelOutputInt out,int min,int max)
      { channel = out;
	this.min=min;
	this.max=max;
      } // constructor

    public void run ()
      { 
	for(int i=0;i<100;i++){      
	int item = (int)(Math.random()*(max-min+1)+min);
        channel.write(item);

	}
	channel.write(-1); //signal end of process
      } // run

  } // class Producer
