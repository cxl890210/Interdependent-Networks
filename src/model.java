import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class model {
	public int nodeCount;//系统尺度
//	双层网络
	public NetWork netWork1;
	public NetWork netWork2;
//	生成两个社区
	public model(int nodeCount,int outEdge,int edgeCount,double alpha,double beta) throws IOException
	{
		this.nodeCount = nodeCount;
		netWork1 = new NetWork(nodeCount,alpha);//初始化两个社区
		netWork2 = new NetWork(nodeCount,beta);
		produceNetWork(netWork1,edgeCount);
		produceNetWork(netWork2,edgeCount);
		interConnctFunction(netWork1,netWork2);//社区之间连边
		System.out.println("Construct Network Finish!");
	}
//	网络之间的一一连接(全耦合)
	public void interConnctFunction(NetWork netWork1,NetWork netWork2) throws IOException
	{
		for(int i=0;i<nodeCount;i++)
		{
			Point net1Point = netWork1.points[i];
			Point net2Point = netWork2.points[i];
			netWork1.interConnect.put(i,i);
			netWork2.interConnect.put(i, i);
			netWork1.outLinkNodes.add(net1Point);
			netWork2.outLinkNodes.add(net2Point);
		}
	}
	public void produceNetWork(NetWork netWork,int edgeCount) throws IOException
	{
		add(netWork,edgeCount);
	}
	//添加连边
	public  void add(NetWork netWork,int edgeCount) throws IOException
	{
		int step=0;
		double alpha = netWork.alpha;
		while(step<=edgeCount)
		{
			Edge addedge=null;
			addedge = produceEdge(netWork,alpha);
			if(addedge.inter==true)//如果是集团间连边则两个集团融合
			{   
				Point point1 = addedge.getPoint1();
				Point point2 = addedge.getPoint2();
				merge(point1,point2,netWork);
			}
			step++;
		}
	}

	public Edge produceEdge(NetWork netWork,double alpha) throws IOException
	{
		Edge edge=null;
		Point[] points = netWork.points;
		Random random = new Random();
		int domainNode;//主节点
		int secondNode;//从节点
		domainNode=random.nextInt(nodeCount);
		Point domainPoint = points[domainNode];
		Point secondPoint;
		int communityNum = domainPoint.communityNumber;
//		第二个节点以alpha连向自己社区,以概率1-alpha连向外社区
		double r = random.nextDouble();
		if(r<alpha)
		{//连向自己社区
			int beginCount = netWork.community[communityNum].beginCount;
			int communitySize = netWork.community[communityNum].communitySize;
			while(true)
			{
				secondNode = random.nextInt(communitySize)+beginCount;
				secondPoint = points[secondNode];
				if(secondNode!=domainNode&&!domainPoint.adjVector.contains(secondPoint))
					break;
			}
		}
		else
		{//连向外部社区
			int beginCount = netWork.community[1-communityNum].beginCount;
			int communitySize = netWork.community[1-communityNum].communitySize;
			while(true)
			{
				secondNode = random.nextInt(communitySize)+beginCount;
				secondPoint = points[secondNode];
				if(!domainPoint.adjVector.contains(secondPoint))
					break;
			}
		}
		secondPoint = points[secondNode];
		Boolean inter=true;//是否集团间连边
//		相互建立连边
		domainPoint.adjVector.add(secondPoint);
		secondPoint.adjVector.add(domainPoint);
		int branch_number1=domainPoint.branchNumber;
		int branch_number2=secondPoint.branchNumber;
		if(branch_number1==branch_number2)inter=false;
		edge=new Edge(domainPoint,secondPoint,inter);	
		return edge;
	}	
	public void merge(Point point1,Point point2,NetWork netWork) throws IOException
	{
		int lager_branch=0;//记录较大连通分支号
		int smaller_branch=0;
		int lager_size;
		int smaller_size;
		ArrayList<Integer> lager_cluster;//大集团
		ArrayList<Integer> smaller_cluster;//小集团
		int clusterSize1=netWork.cluster_nodeList.get(point1.branchNumber).size();
		int clusterSize2=netWork.cluster_nodeList.get(point2.branchNumber).size();
		if(clusterSize1<=clusterSize2)
		{lager_branch=point2.branchNumber;
		 smaller_branch=point1.branchNumber;
		 lager_size=clusterSize2;
		 smaller_size=clusterSize1;
		}
		else{ 
			lager_branch=point1.branchNumber;
			smaller_branch=point2.branchNumber;
			lager_size=clusterSize1;
			smaller_size=clusterSize2;
		}
		lager_cluster=netWork.cluster_nodeList.get(lager_branch);
		smaller_cluster=netWork.cluster_nodeList.get(smaller_branch);
		//更改小分支顶点所属分支编号
		for(Integer index:smaller_cluster)
		{
			netWork.points[index].branchNumber=lager_branch;
		}
		lager_cluster.addAll(smaller_cluster);
		netWork.cluster_nodeList.put(lager_branch, lager_cluster);
		netWork.cluster_nodeList.remove(smaller_branch);//删除集团标号为smaller_branch的集团	
	}
}

