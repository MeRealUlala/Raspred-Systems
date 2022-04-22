
/*
 * 2.7 Ведение учета потребления воды: регистрация счетчика (владелец, вид,
тариф), ввод показаний, оплата , корректировка тарифа, получение
информации о текущих показаниях и оплаченном потреблении воды
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

    // Конструктор
    public SchClient(String serverName) {
        this.srvName = serverName;
    }

    // Метод тестирования операций
    public void startTest() throws
            IOException, ClassNotFoundException, ParseException {
        connectToServer();
        Schyotchik sch2 = new Schyotchik("Петров С. К.", 900.0, "2222", "вихревой", 0.0);
        SchOperation co = new SchOperation(new Schyotchik("Васильев О. А.", 900.0, "111", "электромагнитный", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(sch2, 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("Власов К. Е.", 899.0, "3", "тахометрический", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("Софийская А. К.", 789.0, "444", "тахометрический", 0.0), 0, 0, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik("Софийская А. К.", 789.0, "444", "тахометрический", 0.0), 0, 0, new Date());
        processOperation(co);

        //опер.1 ввод
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 1, 1200, formatter.parse("01-09-2020"));
        processOperation(co);
        co = new SchOperation(sch2, 1, 300, formatter.parse("01-09-2020"));
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 1, 500, new Date());
        processOperation(co);

        //опер.2 оплата
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 2, 1000, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 2, 200, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 2, 1240, new Date());
        processOperation(co);

        //опер.3 корректировка тарифа
        co = new SchOperation(new Schyotchik(null, 900.0, "111", null, 0), 3, 800, new Date());
        processOperation(co);
        co = new SchOperation(sch2, 3, 304, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "3", null, 0), 3, 506, new Date());
        processOperation(co);
        co = new SchOperation(new Schyotchik(null, 899.0, "344", null, 0), 3, 506, new Date());
        processOperation(co);

        //опер.4. вывод
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
        log("Работа завершена");
    }

    // Соединение с сервером
    void connectToServer()
            throws UnknownHostException, IOException {
        s = new Socket(srvName, srvPort);
        log("Соединение с сервером установлено\n");
        ins = new ObjectInputStream(s.getInputStream());
        outs = new ObjectOutputStream(s.getOutputStream());
        log("Созданы потоки для обмена данными\n");
    }

    // Метод выполнения операции
    void processOperation(SchOperation co)
            throws IOException, ClassNotFoundException {
        log("Операция " + co.operTip + " со счётчиком "
                + co.sch.sNumber);
        //  Отсылка операции серверу
        outs.writeObject(co);
        //  Вывод ответа сервера
        log("Результат: " + (String) ins.readObject());
    }

    // Запуск клиента
    public static void main(String[] args) throws Exception {
        String serverAddress = JOptionPane.showInputDialog(
                null, "Введите IP адрес сервера:",
                "Вход в программу операций со счётчиками",
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
