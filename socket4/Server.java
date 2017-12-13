package socket4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Server {
	public static void main(String args[]) {
		ServerSocket server = null;
		//List<EchoThread> list2 = new Vector<>(); 
		List<Socket> list = new Vector<>(); 
		try {
			server = new ServerSocket(9001);
			System.out.println("Ŭ���̾�Ʈ�� ������ �����");
			while (true) {
				Socket socket = server.accept();
				list.add(socket);
				new EchoThread(socket,list).start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(server != null)server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

class EchoThread extends Thread {
	List<Socket> list;
	Socket socket;
	public EchoThread() {	}
	public EchoThread(Socket socket,List<Socket> list) {
		this.socket = socket;
		this.list = list;
	}
	@Override
	public void run() {
		InetAddress address = socket.getInetAddress();
		System.out.println(address.getHostAddress() + " �κ��� �����߽��ϴ�.");
		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			String message = null;
			while ((message = br.readLine()) != null) {
				broadcast(message);
			}
			br.close();

		} catch (Exception e) {

		} finally {
			try {
				if(socket !=null ) socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void broadcast(String msg) throws IOException{
		for(Socket socket:list){
			OutputStream out = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(msg);
			pw.flush();
		}
	}
}
