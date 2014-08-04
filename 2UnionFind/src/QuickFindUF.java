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
	
	public boolean connected(int p,int q){//����Ƿ�����
		return id[p]==id[q];
	}
	
	public void union(int p,int q){//��������
		int pid=id[p];
		int qid=id[q];
		for(int i=0;i<id.length;i++){
			if(id[i]==pid){//�����id[p]���ᵼ�´�����Ϊp��ֵ�����Ѿ��ı�
				id[i]=qid;
			}
		}
	}

}
