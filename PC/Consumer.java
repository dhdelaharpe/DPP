import org.jcsp.lang.*;

/** Consumer class: reads one int from input channel, displays it, then
  * terminates.
  */
public class Consumer implements CSProcess
  { private AltingChannelInputInt channel;
	private ChannelOutputInt msg; //tell buffer to pass next message

    public Consumer (final AltingChannelInputInt in, final ChannelOutputInt msg)
      { channel = in;
	this.msg=msg;
      } // constructor

    public void run ()
      { 
	for(int i=0;i<100;i++){
	 msg.write(1); //let buffer know 
	 int item = channel.read();
         System.out.println(item);
	}
	msg.write(-1);
      } // run

  } // class Consumer
