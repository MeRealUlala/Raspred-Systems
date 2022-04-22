

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schyotchik implements Serializable {
    private static final long serialVersionUID = 1L;
    // ���� ������
    public String person;    // ��������
    public double tarif; // �����
    public String vid;// ���
    public double summ;    // ���������� �����
    public String sNumber;// ���
    public Double pokazN; //��������� ���������
    public Date dateP; //���� ��������� ���������

    //  ����������� ������
    public Schyotchik(String person, double tarif, String sNumber,
                      String vid, double summ) {
        this.person = person;
        this.tarif = tarif;
        this.vid = vid;
        this.summ = summ;
        this.sNumber = sNumber;

        this.pokazN = null;
        this.dateP = null;

    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    // ����� ������ ���������� �� ��������
    public String toString() {
        if (dateP == null)
            return "�������: �����=" + sNumber +
                    "\t��������=" +
                    person + "\n��������� �� �������������." + "\n";
        else return
                "�������: �����=" + sNumber +
                        "\t��������=" +
                        person + "\n��������� ���������=" + pokazN + "" + "\t���� ���������=" + dateFormat.format(dateP) + "\n" +
                        "��������=" + summ;
    }
}

