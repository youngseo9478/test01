package socket4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client_UI {
	JFrame f = new JFrame("ä��Ŭ���̾�Ʈ");
	Button b1,b2;
	TextField ip,port,id,msg;
	TextArea ta;
	Socket socket =null;
    BufferedReader br;
    PrintWriter pw;
	
	Client_UI(){
		Panel p1 = new Panel();
		p1.add(ip = new TextField("70.12.109.50"));
		p1.add(port=new TextField("9001"));
		p1.add(b1 = new Button("����"));
		p1.add(b2 = new Button("����"));
		f.add(p1,BorderLayout.NORTH);
		f.add(ta = new TextArea(25,30));
		Panel p2 = new Panel();
		p2.add(id=new TextField(10));
		p2.add(msg=new TextField(30));
		f.add(p2,BorderLayout.SOUTH);
		f.setSize(400, 350);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		//�����ư
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String name =id.getText(); 
				try {
					socket = new Socket(ip.getText(),9001);
					System.out.println("���ӵ�..");
					InputStream in = socket.getInputStream();
					br = new BufferedReader(new InputStreamReader(in));
					OutputStream out = socket.getOutputStream();
					pw = new PrintWriter(new OutputStreamWriter(out));
						
					new ClientThread2(socket,br,ta).start();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		msg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pw.println(id.getText()+":"+msg.getText());
				pw.flush();	
				msg.setText("");
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�ڿ��ݳ�
			}
		});
		
	}
	public static void main(String args[]){
		new Client_UI();
	}	
}
class ClientThread2 extends Thread{
	Socket socket;
	BufferedReader br;
	TextArea ta;
	ClientThread2(){}
	ClientThread2(Socket socket,BufferedReader br,TextArea ta){
		this.socket = socket;
		this.br = br;
		this.ta = ta;
	}
	public void run() {
        try{
           String msg = null;
           while((msg = br.readLine()) != null){
        	   ta.append(msg+"\n");
           }
        }catch (Exception e) {
  
        }finally {
			
		}
	}
}













