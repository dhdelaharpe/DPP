import java.util.concurrent.*;
/*
 * Parallel 10 run avg(ms)  =88
 * Sequential 10 run avg(ms)  =120
 * this was with only 70000 elements, as this vm does not have the resources to run more but it is enough to show that there is a parallel speedup of around 1.4
 */

public class timing
{
	private static long startTime;
	private static long end;

	//main
	public static void main(String[] args)
	{
		int runAmount=10;//task requests 10 runs
		long para=0;
		long seq=0;

		Comparable[] data = new Comparable[70000]; //1m entries

		System.out.println("This may take a while");

		for(int i=0;i<runAmount;i++)
		{//for to iterate each run
			System.out.println("Filling list with data");
			data=fillList();
			System.out.println("timing parallel version");
			para+=pTime(data);
			System.out.println("timing sequential version");
			seq+=sTime(data);
		}

		//show results
		System.out.println("Parallel 10 run avg(ms)  ="+para/10);
		System.out.println("Sequential 10 run avg(ms)  ="+seq/10);
	}//end main
	
	private static Comparable[] fillList()
	{//start fillList
		Comparable[] ls = new Comparable[70000];
		for(int i =0;i<ls.length;i++)
		{//start for
			ls[i]=(int)(Math.random()*50)+1; //adding the multiplication and addition to spread results a bit
		}//end for
		return ls;
	}//end fillList

	/*
	private static long time(Comparable[] data,boolean para)
	{//start time

		startTime=System.currentTimeMillis();
		if(para)//adds some overhead but shouldn't be much
		{	
			ForkJoinPool p = new ForkJoinPool();
			ParaSort.Sort(data,p);
			long end=System.currentTimeMillis();
			return end-startTime;
		}
		else
		{
			Sort.quickSort(data);
			long end=System.currentTimeMillis();
			return end-startTime;
		}
	}//end time
	*/
	private static long pTime(Comparable[] data)
	{
		ForkJoinPool p = new ForkJoinPool();
		startTime=System.currentTimeMillis();
		ParaSort.Sort(data,p);
		end = System.currentTimeMillis();
		return end-startTime;
	}

	private static long sTime(Comparable[] data)
	{
		startTime=System.currentTimeMillis();
		Sort.quickSort(data);
		end = System.currentTimeMillis();
		return end-startTime;
	}
}//end main class
