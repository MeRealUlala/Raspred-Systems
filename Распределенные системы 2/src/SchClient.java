
/*
 * 2.7 ������� ����� ����������� ����: ����������� �������� (��������, ���,
�����), ���� ���������, ������ , ������������� ������, ���������
���������� � ������� ���������� � ���������� ����������� ����
 */

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchClient {
    int tipOper;
    int srvPort = 7896;
    static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String srvName;
    Socket s;
    ObjectInputStream ins;
    ObjectOutputStream outs;

    // �����������
    public SchClient(String serverName) {
        this.srvName = serverName;
    }

    // ����� ������������ ��������
    public void startTest() throws
            IOException, ClassNotFoundException, ParseException {
        connectToServer();
        Schyotchik sch2 = new Schyotchik("������ �. �.", 900.0, "2222", "��������", 0.0);
        SchOperation co = new SchOperation(new Schyotchik("�������� �. �.", 900.0, "111", "����������������", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(sch2, 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("������ �. �.", 899.0, "3", "���������������", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("��������� �. �.", 789.0, "444", "���������������", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("��������� �. �.", 789.0, "444", "���������������", 0.0), 0, 0, new Date());
        processOperation(co);

        //����.1 ����
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 1, 1200, formatter.parse("01-09-2020"));
        processOperation(co);
        co = new SchOperation(sch2, 1, 300, formatter.parse("01-09-2020"));
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 1, 500, new Date());
        processOperation(co);

        //����.2 ������
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 2, 1000, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 2, 200, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 2, 1240, new Date());
        processOperation(co);

        //����.3 ������������� ������
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 3, 800, new Date());
        processOperation(co);
        co = new SchOperation(sch2, 3, 304, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 3, 506, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "344", null, 0), 3, 506, new Date());
        processOperation(co);

        //����.4. �����
        co = new SchOperation(sch2, 4, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 4, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 4, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 789.0, "444", null, 0), 4, 0, new Date());
        processOperation(co);
        outs.writeObject(null);
        s.close();
        log("������ ���������");
    }

    // ���������� � ��������
    void connectToServer()
            throws UnknownHostException, IOException {
        s = new Socket(srvName, srvPort);
        log("���������� � �������� �����������\n");
        ins = new ObjectInputStream(s.getInputStream());
        outs = new ObjectOutputStream(s.getOutputStream());
        log("������� ������ ��� ������ �������\n");
    }

    // ����� ���������� ��������
    void processOperation(SchOperation co)
            throws IOException, ClassNotFoundException {
        log("�������� " + co.operTip + " �� ��������� "
                + co.sch.sNumber);
        //  ������� �������� �������
        outs.writeObject(co);
        //  ����� ������ �������
        log("���������: " + (String) ins.readObject());
    }

    // ������ �������
    public static void main(String[] args) throws Exception {
        String serverAddress = JOptionPane.showInputDialog(
                null, "������� IP ����� �������:",
                "���� � ��������� �������� �� ����������",
                JOptionPane.PLAIN_MESSAGE);
        SchClient bc = new SchClient(serverAddress);
        try {
            bc.startTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String msg) {
        System.out.println(msg);
    }

}
