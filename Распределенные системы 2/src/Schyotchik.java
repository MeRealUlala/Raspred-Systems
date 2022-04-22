

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Schyotchik implements Serializable {
    private static final long serialVersionUID = 1L;
    // Поля класса
    public String person;    // Владелец
    public double tarif; // Тариф
    public String vid;// Вид
    public double summ;    // Оплаченная сумма
    public String sNumber;// Вид
    public Double pokazN; //Последние показания
    public Date dateP; //Дата последних показаний

    //  Конструктор класса
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

    // Метод вывода информации по счётчику
    public String toString() {
        if (dateP == null)
            return "Счётчик: Номер=" + sNumber +
                    "\tВладелец=" +
                    person + "\nПоказаний не зафиксировано." + "\n";
        else return
                "Счётчик: Номер=" + sNumber +
                        "\tВладелец=" +
                        person + "\nПоследние показания=" + pokazN + "" + "\tДата показаний=" + dateFormat.format(dateP) + "\n" +
                        "Оплачено=" + summ;
    }
}

