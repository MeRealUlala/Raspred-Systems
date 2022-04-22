
//������� ����� ������������ ��������������: ����������� �������� (��������, �����), ���� ���������, ������ 
//, ������������� ���������, ��������� ���������� � ������� ����������

import java.io.Serializable;
import java.util.Date;

public class SchOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    //  ���� ������
    public Schyotchik sch;       // �������
    public double value;   // �������� ��������
    public Date operDate;   // ���� ��������
    public Integer operTip; // ��� ��������

    //  ����������� ������
    public SchOperation(Schyotchik sch, Integer operTip,
                        double value, Date operDate) {
        this.sch = sch;
        this.value = value;
        this.operDate = operDate;
        this.operTip = operTip;
    }
}
