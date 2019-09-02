import java.util.concurrent.*;

public class ParaSort
{
	//method to call from timing which starts sorting
	public static <T extends Comparable<? super T>> void Sort(T[] ls, ForkJoinPool p)
	{
		QuickSort firstTask= new QuickSort(ls,0,ls.length-1);
		p.invoke(firstTask);
		//return firstTask.getData();
	}//end Sort

	public static class QuickSort extends RecursiveAction
	{
		private Comparable[] data;
		private int start;
		private int end;
		
		public QuickSort(Comparable[] data, int start, int end){
			this.data=data;
			this.start=start;
			this.end=end;
		}// dc

		//dont need this getter but useful to check output is correct
		public Comparable[]  getData()
		{
			return this.data;
		}

		protected void compute()
		{
			if(start<end){//else no point
				int point = partition(data,start,end);
				//spawn threads recursively- invokeAll allows all tasks to complete so no need to wait
				invokeAll(new QuickSort(data,start,point-1),new QuickSort(data,point+1,end));//figuring out to start next at point+1 took a fair amount of debugging :(
			}//end if
		
		}//end compute

	//copied partition method	
	@SuppressWarnings("unchecked")
	private static int partition (Comparable[] list, int start, int end)
        { 
            int left = start,
            right = end;
            Comparable tmp;
            while (left < right)
            { // Work from right end first
                while (list[right].compareTo(list[start]) > 0)
                    right--;
                // Now work up from start
                while (left < right && list[left].compareTo(list[start]) <= 0)
                    left++;
                if (left < right)
                { 
                    tmp = list[left];
                    list[left] = list[right];
                    list[right] = tmp;
                }
            }
            // Exchange the partition element with list[right]
            tmp = list[start];
            list[start] = list[right];
            list[right] = tmp;
            return right;
        } // partition
	}//inner class
	
	
	
	


	/*
	//temp main 
	public static void main(String[] args)
	{
		Comparable[] someData = new Comparable[10];
		for(int i =0; i<10;i++){
			someData[i]=(int)( Math.random()*50)+1;
			System.out.println(someData[i]);
		}
		ForkJoinPool p = new ForkJoinPool();
		someData = Sort(someData,p);
		for(int i=0;i<10;i++){
			System.out.println(someData[i]);
		}
	}//main
*/
}//end ParaSort
