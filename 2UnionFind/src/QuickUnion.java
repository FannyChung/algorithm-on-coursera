/**
 * 
 */

/**
 * @author zhongfang
 *
 */
public class QuickUnion {
	
	private int[] id;

	/**
	 * 
	 */
	public QuickUnion(int N) {
		// TODO Auto-generated constructor stub
		id=new int[N];
		for(int i=0;i<N;i++){
			id[i]=i;
		}
	}
	public boolean connected(int p,int q){//检查是否相连
		int proot=p,qroot=q;
		while(proot!=id[proot]){
			proot=id[proot];
		}
		while(qroot!=id[qroot]){
			qroot=id[qroot];
		}
		return qroot==proot;
	}
		
	public void union(int p,int q){//建立连接
		int proot=p,qroot=q;
		while(proot!=id[proot]){
			proot=id[proot];
		}
		while(qroot!=id[qroot]){
			qroot=id[qroot];
		}
		id[proot]=qroot;
	}
}
