import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	ServerSocket svrSocket;
	Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	int byteRead;
	public Server(){
		
		try {
			svrSocket = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        while(true){  
           
			try {
				socket = svrSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			try {
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String obj = null;
			while(true){
			
				try {
					obj= (String)in.readObject();
				} catch (IOException e) {
					try {
						socket.close();
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					break;
					//e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(obj!=null){
					System.out.println(obj);
					try {
						out.writeObject("ok");
						out.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(socket.isClosed())
					break;
			}
        }
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		new Server();
	}

}
