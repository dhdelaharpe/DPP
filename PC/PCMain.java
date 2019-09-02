import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
  * Sets up channel, creates one of each process then
  * executes them in parallel, using JCSP.
  */
public final class PCMain
  {
    public static void main (String[] args)
      { new PCMain();
      } // main

    public PCMain ()
      { // Create channel object
        final One2OneChannelInt channelProd = Channel.one2oneInt();
	final One2OneChannelInt channelCons = Channel.one2oneInt();
	final One2OneChannelInt msg = Channel.one2oneInt();
	
	//second Prod/Cons pair
	final One2OneChannelInt channelProd2 = Channel.one2oneInt();
	final One2OneChannelInt channelCons2 = Channel.one2oneInt();
	final One2OneChannelInt msg2 = Channel.one2oneInt();
       
       	// Create and run parallel construct with a list of processes
        CSProcess[] procList = { new Producer(channelProd.out(),1,100),
				new Producer(channelProd2.out(),101,200),
		       		new Consumer(channelCons.in(),msg.out()),
				new Consumer(channelCons2.in(),msg2.out()),
				new Buffer(channelProd.in(),channelCons.out(),msg.in(),channelProd2.in(),channelCons2.out(),msg2.in())
	}; // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
      } // PCMain constructor

  } // class PCMain
