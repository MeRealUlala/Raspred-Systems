
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SchService extends Thread {
    ObjectInputStream ins;
    ObjectOutputStream outs;
    SchServer cs;
    Socket sok;

    // Конструктор класса
    public SchService(SchServer cs, Socket sok) {

        this.cs = cs;
        // Извлечение сокета, присланного клиентом
        this.sok = sok;
        try {
            // Извлечение входного и выходного потоков
            this.outs = new
                    ObjectOutputStream(sok.getOutputStream());
            this.ins = new
                    ObjectInputStream(sok.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Работают потоки на сокете "
                + sok + "\n");
    }

    //  Операции потока
    public void run() {
        System.out.println(
                "Сервис обслуживания счётчиков стартовал\n");
        boolean work = true;
        String msg;
        Object o;
        //  Цикл обработки запросов клиента
        while (work) {
            try {
                // Чтение присланного объекта из потока сокета
                o = ins.readObject();
                // Проверка типов объекта
                if (o == null) {
                    // Пустой объект – выход из цикла
                    work = false;
                } else if (o instanceof SchOperation) {
                    //  Объект является операцией
                    SchOperation co = (SchOperation) o;
                    msg = "";
                    //  Определение типа операции и ее выполнение
                    if (co.operTip == 0) {//Регистрация нового счётчика
                        // Выполнение операции
                        msg = cs.addNew(co.sch);
                        // Запись операции в выходной поток
                        outs.writeObject(msg);
                    } else if (co.operTip == 1) {//Ввод показаний
                        msg = cs.addPok(co.sch.sNumber, co.value, co.operDate);
                        outs.writeObject(msg);
                    } else if (co.operTip == 2) { // Оплата
                        msg = cs.opl(co.sch.sNumber, co.value);
                        outs.writeObject(msg);
                    } else if (co.operTip == 3) { // Корректировка тарифа
                        msg = cs.korr(co.sch.sNumber, co.value);
                        outs.writeObject(msg);
                    } else if (co.operTip == 4) // Получение инф
                        outs.writeObject(cs.getSch
                                (co.sch.sNumber));
                } else
                    System.out.println("Ошибочная операция");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
