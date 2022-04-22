import javax.swing.JFrame;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
public class L21ClientM2 {
//	private BufferedReader in;
	//   private PrintWriter out;
	   private JFrame frame = new JFrame("Клиент подсчёта информации");
	   Socket socket = null;
	   static String serverAddress;
	   // Поле для ввода сообщений
	   private JTextField dataField = new JTextField(40);
	     //  Область для вывода сообщений от сервера
		private JTextArea messageArea = new JTextArea(8, 60);
	     //  Конструктор класса клиента
		public L21ClientM2() {
			// Создание GUI
			messageArea.setEditable(false);
			frame.getContentPane().add(dataField, "North");
			frame.getContentPane().add(
	             new JScrollPane(messageArea), "Center");
			// Обработчик события нажатия Enter:
	          // Отправка строки на сервер и отображение
	          //   полученного результата в поле вывода
	          //  При вводе пустой строки – завершение приложения
			dataField.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  DatagramSocket aSocket = null;
				try {
					aSocket = new DatagramSocket();
				} catch (SocketException e2) {
					e2.printStackTrace();
				}
				  try {

				// Запись строки в выходной поток сокета
				messageArea.append("m="+dataField.getText()+" ");
				if (dataField.getText().length()==0 || Integer.parseInt(dataField.getText()) == 0)
	    			 System.exit(0);

				byte[] path = dataField.getText().getBytes();
				InetAddress aHost = null;
				try {
					aHost = InetAddress.getByName(serverAddress);
				} catch (UnknownHostException e1) {
					
					e1.printStackTrace();
				}
				int serverPort = 5555;
				DatagramPacket request = new DatagramPacket(path, path.length, aHost, serverPort);
			//  Отправка сообщения
				aSocket.send(request);
				//String response;
			
			//  Создание буфера для приема ответа сервера
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);		
					aSocket.receive(reply);
					
					String per=new String(reply.getData());
					per=per.substring(0, reply.getLength());
				//	System.out.print(per);
					if (!(per.equals("-")))
						
				messageArea.append(". : "+per+"\n");
					else messageArea.append("Неверная входная строка"+"\n");

			  }
				  catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				  finally {
						// Закрытие сокета
						if (aSocket != null)
							aSocket.close();

			  }
			  }
			});
		}
			
		//  Метод установки соединения с сервером**
		public void connectToServer() throws IOException {
			// Запрос адреса сервера
			 serverAddress = JOptionPane.showInputDialog(
	              frame, "Введите IP адрес сервера:",
				"Вход в программу ", 
	               JOptionPane.QUESTION_MESSAGE);
			messageArea.append("Соединение с сервером установлено \n");
			 messageArea.append("Мы найдем нужное число. Введите его номер (от 0), для завершения пустая строка \n");
		}
		//  Запуск клиентского приложения
		public static void main(String[] args) throws Exception {
		  L21ClientM2 client = new L21ClientM2();
		  client.frame.setDefaultCloseOperation(
	            JFrame.EXIT_ON_CLOSE);
		  client.frame.pack();
		  client.frame.setLocationRelativeTo(null);
		  client.frame.setVisible(true);
		  client.connectToServer();
		}
	}
