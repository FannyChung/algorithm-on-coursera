/**
 * 
 */

/**
 * @author zhongfang
 *
 */
public class QuickUnionApprove {
	private int[] id;

	private int[] sz;
	/**
	 * 
	 */
	public QuickUnionApprove(int N) {
		// TODO Auto-generated constructor stub
		id=new int[N];
		for(int i=0;i<N;i++){
			id[i]=i;
			sz[i]=1;
		}
	}

	private int root(int p){
		while(id[p]!=p){
			p=id[p];
		}
		return p;
	}
	
	public boolean connected(int p,int q){//检查是否相连
		return root(p)==root(q);
	}
	
	public void union(int p,int q){//建立连接
		int i=root(p);
		int j=root(q);
		if(i==j)
			return;
		else{
			if(sz[i]<sz[j]){//i放在j下方
				id[i]=j;
				sz[j]+=sz[i];
			}else{
				id[j]=i;
				sz[i]+=sz[j];
			}
		}
	}
}
