
//Ведение учета потребленной электроэнергии: регистрация счетчика (владелец, тариф), ввод показаний, оплата 
//, корректировка показаний, получение информации о текущих показаниях

import java.io.Serializable;
import java.util.Date;

public class SchOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    //  Поля класса
    public Schyotchik sch;       // Счётчик
    public double value;   // Значение операции
    public Date operDate;   // Дата операции
    public Integer operTip; // Тип операции

    //  Конструктор класса
    public SchOperation(Schyotchik sch, Integer operTip,
                        double value, Date operDate) {
        this.sch = sch;
        this.value = value;
        this.operDate = operDate;
        this.operTip = operTip;
    }
}
