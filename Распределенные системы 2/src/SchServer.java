

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Hashtable;

public class SchServer extends Thread {
    private int srvPort = 7896;
    private ServerSocket ss;
    private Hashtable<String, Schyotchik> ht;
    //private Hashtable<String,Pokaz> htp;

    // ����������� �������
    public SchServer() {
        // �������� ��������� ��� ���������
        ht = new Hashtable<String, Schyotchik>();
        //  htp = new Hashtable<String,Pokaz> ();
    }

    //  ������ �������
    public static void main(String[] args) {
        SchServer cs = new SchServer();
        cs.start();
    }

    //  ������ ������ �������
    public void run() {
        try {
            // �������� ���������� ������
            ss = new ServerSocket(srvPort);
            log("������ ���������");
            // ���� ������������ ��������
            while (true) {
                log("�������� ������ �������...");
                Socket soc = ss.accept();
                log("������������� ������");
                // �������� ������� � ������ ������������ �������
                SchService service = new SchService(this, soc);
                log("������ ������ ������������ ���������");
                service.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ����� ��������
    public String addNew(Schyotchik sch) {
        Schyotchik c = (Schyotchik) ht.get(sch.vid);
        String msg;
        // �������� ������� �������� � �������� �������
        if (c != null) {
            msg = "������. ������� � ����� ������� ����������\n";
            log(msg);
            return msg;
        } else {
            // ������ �������� � ���������
            ht.put(sch.sNumber, sch);
            msg = "������ ������� � ������� " + sch.sNumber;
            log(msg);
            return msg;
        }
    }

    //  ����� ����� ���������
    public String addPok(String sch, Double value, Date datas) {
        // ���������� �������� �� ���������
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // �������� ������� ��������� ��������
        if (c == null) {
            msg = "������. ������������ ����� ��������\n";
            log(msg);
            return msg;
        }
        ;


        c.pokazN = value;
        c.dateP = datas;

        // ������ �������� � ���������
        ht.put(sch, c);
        msg = "������ ��������� " + value + " �� ������� # "
                + sch;
        log(msg);
        return msg;
    }

    //  ����� ������������� ������
    public String korr(String sch, double value) {
        // ���������� ����� �� ���������
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // �������� ������� ��������� ��������
        if (c == null) {
            msg = "������. ������������ ����� ��������\n";
            log(msg);
            return msg;
        }
        ;
        c.tarif = value;

        ht.put(sch, c);
        msg = "����� �� �������� # "
                + sch + " ���������������. ����� ��������=" + value + "\n";
        log(msg);
        return msg;
    }

    // ������
    public String opl(String sch, double value) {
        // ���������� ����� �� ���������
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // �������� ������� ��������� ��������
        if (c == null) {
            // �������� ������� ��������� ��������
            msg = "������. ������������ ����� ��������\n";
            log(msg);
            return msg;
        }
        ;
        // �������� �������
        //  if (c.balance < money){
        //   msg="������. ������������ �������. �� ����� ������ "
        // + c.balance+"\n";
        //   log(msg);
        //   return msg;
        // }
        // else {
        // ������ �����
        c.summ += value;
        // ������ ����� � ���������
        ht.put(sch, c);
        msg = "������ " + value + " �� ��������# " + sch +
                ". ����� ��������=" + c.summ + "\n";
        log(msg);
        return msg;
    }

    //}
    // ������ ���������� �� ��������
    public String getSch(String sch) {
        // ���������� ����� �� ���������
        Schyotchik c = (Schyotchik) ht.get(sch);
        if (c == null) {
            // �������� ������� ��������� ��������
            String msg = "������. ������������ ����� ��������\n";
            log(msg);
            return msg;
        }
        ;
        log("������ ���������� �� ��������#" + sch);
        return c.toString();
    }

    // ����� ����������� (����� ��������� ������� � �������)
    private void log(String msg) {
        System.out.println(msg);
    }
}
