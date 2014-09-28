import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class NetWork {
		Point[] points;
		Community[] community;
	    public int nodeCount;//网络尺度
	    public double alpha;
	    public HashMap<Integer,Integer> interConnect;//保存网络之间的连边
	    public ArrayList<Point> outLinkNodes;//保存与外部网络有连接的顶点
	    public int giantComponentIndex;
	    public HashMap<Integer,ArrayList<Integer>> cluster_nodeList;//记录每个集团对应的顶点编号
	    public ArrayList<Integer> currentAll;
	    
		public NetWork(int nodeCount,double alpha)
		{
			points = new Point[nodeCount];
			this.nodeCount = nodeCount;
			this.alpha = alpha;
			cluster_nodeList=new HashMap<Integer,ArrayList<Integer>>();
			interConnect = new HashMap<Integer,Integer>();
			outLinkNodes = new ArrayList<Point>();
			currentAll = new ArrayList<Integer>();
			community = new Community[2];
			community[0] = new Community(0,nodeCount/2);
			community[1] = new Community(nodeCount/2,nodeCount);
			int communityNum;
			for(int i=0;i<nodeCount;i++)
			{
				if(i<nodeCount/2)communityNum = 0;
				else communityNum = 1;
				points[i] = new Point(i,i,communityNum);//建立一个顶点，并对顶点进行标号以及其所属连通分支号
				cluster_nodeList.put(i, new ArrayList<Integer>());
				cluster_nodeList.get(i).add(i);
				currentAll.add(i);
			}
		}
		public HashMap<Integer,Double> Degree_distribution() throws IOException
		{
			HashMap<Integer,Double> degree_prob=new HashMap<Integer,Double>();
			HashMap<Integer,Integer> degree_map=new HashMap<Integer,Integer>();
			for(int i=0;i<nodeCount;i++)
			{
				int degreeNum=points[i].adjVector.size();
				if(degree_map.containsKey(degreeNum))
				{
					int currentValue=degree_map.get(degreeNum)+1;
					degree_map.put(degreeNum, currentValue);
				}
				else
				degree_map.put(degreeNum, 1);
			}
			Entry<Integer,Integer> entry;
			for(Iterator<Entry<Integer, Integer>> it=degree_map.entrySet().iterator();it.hasNext();)
			{
				entry=it.next();
				int degree=entry.getKey();
				double prob=1.0*entry.getValue()/(nodeCount);
				degree_prob.put(degree, prob);
			}
			return degree_prob;
		}
		public void displayStruct(BufferedWriter bw_log) throws IOException
		{
			for(Point point:points)
			{
				for(Point adjPoint:point.adjVector)
				{
					bw_log.write(point.number+"\t"+adjPoint.number+"\n");
					bw_log.flush();
				}
			}
		}
}
