package closest_pair;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.sqrt;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;

class Point
{
    double x;
    double y;
    
    static double eucledean(Point a,Point b)
    {
        return sqrt( ((a.x - b.x)*(a.x - b.x)) + ((a.y - b.y)*(a.y - b.y)));
    }
    
     static void SortX(Point point[],int N)
    {
        int flag = 1;
        Point temp;
        for(int i=0;i< N && flag==1;i++)
        {
            flag = 0;
            for (int j=0;j <N-1;j++)
            {
                if (point[j].x > point[j+1].x )
                {
                    temp = point[j];
                    point[j] = point[j+1];
                    point[j+1] = temp;
                    flag = 1;
                }
            }
        }
    }
     
    static void sweep(int N,Point point[])
    {
        Point.SortX(point,N);
        Vector<Point> list = new Vector<>();
        Vector<Point> pq = new Vector<Point>();
        Point output[]=new Point[N];
        int count[]=new int[1];
        count[0]=0;
        double min= eucledean(point[0],point[1]);
        int index=0;
        pq.add(point[index]);
        for(int i=0;i<N;i++)
        {
            while (index<i && point[i].x-point[index].x > min) pq.remove(point[index++]);
            search(list,point[i].y - min,point[i].y + min,count,pq,N,pq.size());
            for(int j=0;j<count[0];j++) 
            {
                double distance = eucledean(point[i],list.elementAt(j));
                if (distance <= min && distance!=0) 
                {
                    min = distance;
                    output[0]=point[i];
                    output[1]=list.elementAt(j);
                }
                pq.add(point[i]);
            }
        }
        System.out.println("Closest pair of Points : ");
        System.out.println("( "+output[0].x+", "+output[0].y+" )");
        System.out.println("( "+output[1].x+", "+output[1].y+" )");
        System.out.println("Distance : "+min);
    }  
  
    static void search(Vector<Point> list,double y1,double y2,int count[],Vector<Point> pq,int N,int size)
    {
        int index=count[0];
        int index1=binarySearch(pq,0,size,y1 );
        int index2=binarySearch(pq,0,size,y2 );
        if (index1 < 0) 
        {
            index1 = - index1 - 1;
        }
        if (index2 < 0) 
        {
            index2 = - index2 - 1;
        }
        count[0]=count[0]+index2-index1;
        for(int i=index1;i<index2;i++) list.add(pq.elementAt(i));
    }   
    
   static int binarySearch(Vector<Point> pq,int low,int nElems,double key) 
   {
        if (nElems == 0) return 0;
        int high = nElems - 1;
        int mid = 0;
        while (true)
        {
            mid = (high + low) / 2;
            if (pq.elementAt(mid).y == key) return mid;
            else if (pq.elementAt(mid).y < key)
            {
                low = mid + 1; // its in the upper
                if (low > high) return mid + 1;
            }
            else
            {
                high = mid - 1; // its in the lower
                if (low > high)return mid;
            }
        }
    }
}

public class Closest_Pair {

    public static Scanner in;
    public static void main(String[] args) throws FileNotFoundException 
    {
        in = new Scanner(new File("input.txt"));
        int T=in.nextInt();
        for(int l=0;l<T;l++)
        {
            int N=in.nextInt();
            Point point[]=new Point[N];
            for (int i=0;i<N;i++)
            {
                point[i]=new Point();
                point[i].x=in.nextInt();
                point[i].y=in.nextInt();
            }
            Point.sweep(N,point);
            System.out.println();
            System.out.println();
        }
    }
    
}