
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SchService extends Thread {
    ObjectInputStream ins;
    ObjectOutputStream outs;
    SchServer cs;
    Socket sok;

    // ����������� ������
    public SchService(SchServer cs, Socket sok) {

        this.cs = cs;
        // ���������� ������, ����������� ��������
        this.sok = sok;
        try {
            // ���������� �������� � ��������� �������
            this.outs = new
                    ObjectOutputStream(sok.getOutputStream());
            this.ins = new
                    ObjectInputStream(sok.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("�������� ������ �� ������ "
                + sok + "\n");
    }

    //  �������� ������
    public void run() {
        System.out.println(
                "������ ������������ ��������� ���������\n");
        boolean work = true;
        String msg;
        Object o;
        //  ���� ��������� �������� �������
        while (work) {
            try {
                // ������ ����������� ������� �� ������ ������
                o = ins.readObject();
                // �������� ����� �������
                if (o == null) {
                    // ������ ������ � ����� �� �����
                    work = false;
                } else if (o instanceof SchOperation) {
                    //  ������ �������� ���������
                    SchOperation co = (SchOperation) o;
                    msg = "";
                    //  ����������� ���� �������� � �� ����������
                    if (co.operTip == 0) {//����������� ������ ��������
                        // ���������� ��������
                        msg = cs.addNew(co.sch);
                        // ������ �������� � �������� �����
                        outs.writeObject(msg);
                    } else if (co.operTip == 1) {//���� ���������
                        msg = cs.addPok(co.sch.sNumber, co.value, co.operDate);
                        outs.writeObject(msg);
                    } else if (co.operTip == 2) { // ������
                        msg = cs.opl(co.sch.sNumber, co.value);
                        outs.writeObject(msg);
                    } else if (co.operTip == 3) { // ������������� ������
                        msg = cs.korr(co.sch.sNumber, co.value);
                        outs.writeObject(msg);
                    } else if (co.operTip == 4) // ��������� ���
                        outs.writeObject(cs.getSch
                                (co.sch.sNumber));
                } else
                    System.out.println("��������� ��������");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
