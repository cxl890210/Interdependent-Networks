import java.util.ArrayList;


public class Point {

	public int number ;//记录点标号
	public int branchNumber;//记录点所属cluster标号
	public ArrayList<Point> adjVector;//该点的邻接点向量
	public boolean isFailed;//节点失效与否
	public int communityNumber;
	public Point(int number,int branchNumber,int communityNum)
	{
		this.number = number;
		this.communityNumber = communityNum;
		this.branchNumber = branchNumber;
		isFailed=false;
		adjVector = new ArrayList<Point>();
	}
}
