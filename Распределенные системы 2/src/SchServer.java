

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

    // Конструктор сервера
    public SchServer() {
        // Создание хранилища для счётчиков
        ht = new Hashtable<String, Schyotchik>();
        //  htp = new Hashtable<String,Pokaz> ();
    }

    //  Запуск сервера
    public static void main(String[] args) {
        SchServer cs = new SchServer();
        cs.start();
    }

    //  Запуск потока сервера
    public void run() {
        try {
            // Создание серверного сокета
            ss = new ServerSocket(srvPort);
            log("Сервер стартовал");
            // Цикл обслуживания клиентов
            while (true) {
                log("Ожидание нового клиента...");
                Socket soc = ss.accept();
                log("Подсоединился клиент");
                // Создание сервиса – потока обслуживания клиента
                SchService service = new SchService(this, soc);
                log("Создан сервис обслуживания счётчиков");
                service.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод создания
    public String addNew(Schyotchik sch) {
        Schyotchik c = (Schyotchik) ht.get(sch.vid);
        String msg;
        // Проверка наличия счётчика с заданным номером
        if (c != null) {
            msg = "Ошибка. Счётчик с таким номером существует\n";
            log(msg);
            return msg;
        } else {
            // Запись счётчика в хранилище
            ht.put(sch.sNumber, sch);
            msg = "Создан счётчик с номером " + sch.sNumber;
            log(msg);
            return msg;
        }
    }

    //  Метод ввода показаний
    public String addPok(String sch, Double value, Date datas) {
        // Извлечение счётчика из хранилища
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // Проверка наличия заданного счётчика
        if (c == null) {
            msg = "Ошибка. Неправильный номер счётчика\n";
            log(msg);
            return msg;
        }
        ;


        c.pokazN = value;
        c.dateP = datas;

        // Запись счётчика в хранилище
        ht.put(sch, c);
        msg = "Запись показаний " + value + " на счётчик # "
                + sch;
        log(msg);
        return msg;
    }

    //  Метод корректировки тарифа
    public String korr(String sch, double value) {
        // Извлечение карты из хранилища
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // Проверка наличия заданного счётчика
        if (c == null) {
            msg = "Ошибка. Неправильный номер счётчика\n";
            log(msg);
            return msg;
        }
        ;
        c.tarif = value;

        ht.put(sch, c);
        msg = "Тариф на счётчике # "
                + sch + " скоррректирован. Новое значение=" + value + "\n";
        log(msg);
        return msg;
    }

    // Оплата
    public String opl(String sch, double value) {
        // Извлечение карты из хранилища
        Schyotchik c = (Schyotchik) ht.get(sch);
        String msg;
        // Проверка наличия заданного счётчика
        if (c == null) {
            // Проверка наличия заданного счётчика
            msg = "Ошибка. Неправильный номер счётчика\n";
            log(msg);
            return msg;
        }
        ;
        // Проверка баланса
        //  if (c.balance < money){
        //   msg="Ошибка. Недостаточно средств. На карте только "
        // + c.balance+"\n";
        //   log(msg);
        //   return msg;
        // }
        // else {
        // Снятие суммы
        c.summ += value;
        // Запись карты в хранилище
        ht.put(sch, c);
        msg = "Оплата " + value + " по счётчику# " + sch +
                ". Всего оплачено=" + c.summ + "\n";
        log(msg);
        return msg;
    }

    //}
    // Выдача информации по счётчику
    public String getSch(String sch) {
        // Извлечение карты из хранилища
        Schyotchik c = (Schyotchik) ht.get(sch);
        if (c == null) {
            // Проверка наличия заданного счётчика
            String msg = "Ошибка. Неправильный номер счётчика\n";
            log(msg);
            return msg;
        }
        ;
        log("Запрос информации по счётчику#" + sch);
        return c.toString();
    }

    // Метод логирования (вывод сообщений сервера в консоль)
    private void log(String msg) {
        System.out.println(msg);
    }
}
