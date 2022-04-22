
/*

 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
public class L21ServerM2 {

	public static void main(String[] args) throws Exception {
		 int s = 40;
	     System.out.println("Сервер вывода информации запущен");
	     DatagramSocket aSocket = null;
	   // int clientNumber=0;

	     try {
	     //  Создание сокета на заданном порту
		  aSocket = new DatagramSocket(5555); 
		  //  Массив байт для приема данных
		  byte[] buffer = new byte[1000];
	       while (true) {
	    	   
	    //	   clientNumber++;
	    	// Создание датаграммы
	    		  DatagramPacket request = 
	    		       new DatagramPacket(buffer, buffer.length);
	    		// Прием пакета данных
	    		  aSocket.receive(request);
	    		 int len=request.getLength();
	    		 
	    		  //  
	    		  
	    				  String str=new String (request.getData());
	    				  str=str.substring(0,len);

	    				  int m;
	    				  String inputm=null;
	    				  try {
	    				 //m берется из входной строки
	    				  m=Integer.parseInt(str);
	    				  if (m == 0){
	    				  	aSocket.close();

	    				  	break;

	    				  }
	    				//  System.out.println("m"+m);
	    				  int p[] = new int [1200];
		    				 System.out.println();
		    				 for (int i=0; i<(m+1);i++) {

		    					 p[i]=(int)(Math.random()*s);

		    				
		    					 System.out.print((p[i])+" ");

		    				 }
							  System.out.println();
							  System.out.println("Обслуживание клиента завершено");
		    				 //В выходную переменную записывается к-кол-во
		    				 inputm=String.valueOf(p[m]);
		    				// System.out.print("bff"+(p[m])+" ");
		   
		    				
	    				  }
	    				  catch (NumberFormatException |ArrayIndexOutOfBoundsException y) {
	    					  inputm="-";
	    				  }
	    
		           
		            
	    		  DatagramPacket reply = new DatagramPacket(inputm.getBytes(StandardCharsets.UTF_8),
	    				  inputm.getBytes(StandardCharsets.UTF_8).length,
	    		       request.getAddress(), request.getPort());
	    		  aSocket.send(reply);
					// break;
				}
			} catch (SocketException e) {
				// Ошибка
				System.out.println("Ошибка создания сокета: " + e.getMessage());
			} catch (IOException e) {
				// 
				System.out.println("Ошибка отправки!: " + e.getMessage());
			} finally {
				//
				if (aSocket != null)
					aSocket.close();

			}
		System.out.println("Обслуживание клиента завершено");

		}


}