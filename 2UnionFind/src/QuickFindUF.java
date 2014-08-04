/**
 * 
 */

/**
 * @author zhongfang
 *
 */
public class QuickFindUF {

	private int[] id;
	/**
	 * 
	 */
	public QuickFindUF(int N) {
		// TODO Auto-generated constructor stub
		id=new int[N];
		for(int i=0;i<N;i++){
			id[i]=i;
		}
	}
	
	public boolean connected(int p,int q){//检查是否相连
		return id[p]==id[q];
	}
	
	public void union(int p,int q){//建立连接
		int pid=id[p];
		int qid=id[q];
		for(int i=0;i<id.length;i++){
			if(id[i]==pid){//如果是id[p]，会导致错误，因为p的值可能已经改变
				id[i]=qid;
			}
		}
	}

}
