import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

public class Main {

	public static void main(String[] args) throws IOException
	{
//		控制参量
		int meanDegree = 4;
		int nodeCount = 16000;
		int edgeCount = 2*nodeCount;
		int outEdge = nodeCount;//外部连边数
		double attackProb = 0.10;//被攻击顶点比例
		double alpha = 0.0;
		double beta = 0.98;
		//设置输出目录结构
		String root="../data/CommunityAttack/meanDegree="+meanDegree+"/cascadeProcess/alpha="+alpha+"/"
				+"beta="+beta+"/";/***/	
		String dir = root+nodeCount+"/";
		String result_dir = dir+"result.txt";
		File file=new File(dir);
		if(!file.exists())
			file.mkdirs();
		else
		{
			System.out.println("director exists !");
			System.exit(0);
		}
		BufferedWriter bw_result = new BufferedWriter(new FileWriter(result_dir));
		NumberFormat  nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		AttackModel attack = new AttackModel();
		while(attackProb<=1.0)
		{
			model cascadeModel = new model(nodeCount,outEdge,edgeCount,alpha,beta);
//			模拟随机攻击，并取得最大连通分支尺度
			attack.attackProcess(cascadeModel.netWork1, cascadeModel.netWork2, attackProb);
			int giantIndex = cascadeModel.netWork1.giantComponentIndex;
			int giantSize = cascadeModel.netWork1.cluster_nodeList.get(giantIndex).size();
			System.out.println("giantSize:"+giantSize);
			double fract = 1.0*giantSize/nodeCount;
			System.out.println("attackProb: "+attackProb+"\t"+"fract: "+fract);
			bw_result.write(attackProb+"\t"+nf.format(fract)+'\n');
			bw_result.flush();
			attackProb+=0.02;
		}
		bw_result.close();
	}
}
