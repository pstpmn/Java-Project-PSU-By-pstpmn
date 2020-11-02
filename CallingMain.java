import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class CallingMain {
    public static void main(String[] args) throws IOException {
        // Login to System
        Scanner scanLogin = new Scanner(System.in);
        System.out.println("-------- System --------");
        while (true) {
            System.out.println("[1] Login\n[2] Register\n[other] Exit Programe");
            System.out.print("Enter menu : ");
            String preLogin = scanLogin.next();
            if (preLogin.equals("2")) {
                WriterInfo printLogin = new WriterInfo();
                ManageInfo loginManage = new ManageInfo();

                System.out.println("-------- Register --------");
                System.out.print("Register Username : ");
                String user = scanLogin.next();
                boolean checkUser = loginManage.calCheckUser(user);
                if (checkUser == false) {
                    System.out.println("--------------------------------");
                    System.out.println("* already using this username. *");
                    System.out.println("--------------------------------");
                    continue;
                }

                printLogin.setStreamRegis();
                boolean check = printLogin.calRegister(user);
                if (check == true) {
                    System.out.println("---------------------");
                    System.out.println("* Register Success *");
                    System.out.println("---------------------");
                    break;
                } else {
                    System.out.println("------------------");
                    System.out.println("Register Failed");
                    System.out.println("------------------");
                }

            } else if (preLogin.equals("1")) {
                break;
            } else {
                System.out.println("Exit Programe");
                return;
            }
        }

        System.out.println("-------- Login --------");
        String user = null;
        while (true) {
            ManageInfo loginManage = new ManageInfo();
            loginManage.setInputStream();
            System.out.print("Username :");
            user = scanLogin.next();
            boolean resultLogin = loginManage.calCheckUser(user);
            if (resultLogin == false) {
                System.out.println("success login : " + user);
                System.out.println("------------------------");
                loginManage.getInputStream().close();
                break;
            } else {
                System.out.println("-------- Unknown --------");
            }
        }

        // Login to System
        while (true) {
            ManageInfo read1 = new ManageInfo();
            String[] infoUser1 = read1.calInfoDayMonthYear(user);

            Scanner scan = new Scanner(System.in);
            int menu = 0;
            System.out.println("--- Menu Programe ---");
            System.out.println("--- Acount : " + user + " ---");
            System.out.println("------------------------");
            System.out.println("Goal => Days : "+infoUser1[1]);
            System.out.println("Goal => Weeks : "+infoUser1[2]);
            System.out.println("Goal => Years : "+infoUser1[3]);
            System.out.println("------------------------");
            System.out.println("[1] : Show summary of the month");
            System.out.println("[2] : Show Username of all Product");
            System.out.println("[3] : Setting Goal");
            System.out.println("[4] : Write to list");
            System.out.println("[5] : Add Type");
            System.out.println("[6] : Edit Product");
            System.out.println("[7] : Edit Type");
            System.out.println("[8] : delete Product");
            System.out.println("[9] : *Change account");
            System.out.println("[other] : Exit Program");
            System.out.print("Enter menu :");

            try {
                menu = scan.nextInt();
            } catch (Exception e) {
                System.out.println("--------------------------------");
                System.out.println("Please specify the number type.");
                System.out.println("--------------------------------");
            }

            if (menu == 1) {
                String checkGoal = null;
                ManageInfo read = new ManageInfo();
                System.out.println("----- Show Product of day/week/month -----");
                System.out.println("[1] Check each days\n[2] Check each weeks\n[3] Check each months");
                System.out.print("Enter : ");
                String[] infoUser = read.calInfoDayMonthYear(user);
                int menuCheck = 0;
                try {
                    menuCheck = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("--------------------------------");
                    System.out.println("Please specify the number type.");
                    System.out.println("--------------------------------");
                    continue;
                }
                if(menuCheck >3 || menuCheck<1){
                    System.out.println("--------------------------------");
                    System.out.println("Please specify the number type.");
                    System.out.println("--------------------------------");
                    continue;
                }
                ArrayList<String[]> list = new ArrayList<>();
                ArrayList allType = read.calAllTypeCSV();
                if (menuCheck == 1) {
                    checkGoal = infoUser[1];
                    read.setInputStream();
                    System.out.println("---------------------------------------");
                    System.out.println("Please specify [mm/yy] such as 6/2020");
                    System.out.println("---------------------------------------");
                    System.out.print("Enter mm/yy :");
                    String date = scan.next();
                    ArrayList cal = read.calEachDay(date, user);
                    if(cal.size() == 0){
                        System.out.println("---------------------------------------");
                        System.out.println("           Not found data");
                        System.out.println("---------------------------------------");
                        continue;
                    }
                    System.out.println("-----------------------------------------------");
                    System.out.println("\n--- Your goal days: " + infoUser[1] + " ---");
                    System.out.println("-----------------------------------------------");
                    int sumType = 0;
                    for (int i = 0; i < cal.size(); i++) {
                        String[] show = (String[]) cal.get(i);
                        String[] aas = read.getGetType().get(i);
                        String[] sss = read.getGetTypeValue().get(i);
                        double sum = Double.parseDouble(show[1]);
                        double over = Double.parseDouble(infoUser[1]);
                        read.setOverMoney(sum, over);
                        System.out.println("[" + i + "]  " + show[0] + "  over : " + read.getOverMoney() + "  sum : "
                                + sum + " item :" + show[3]);
                        for (int n = 0; n < aas.length; n++) {
                            for (int a = 0; a < allType.size(); a++) {
                                String[] dataType = (String[]) allType.get(a);
                                if (dataType[0].equals(aas[n])) {
                                    aas[n] = dataType[1];
                                    break;
                                }
                            }
                            sumType += Integer.parseInt(sss[n]);
                            System.out.print("     " + aas[n] + " : " + sss[n]);
                        }
                        System.out.println(" ");
                        System.out.print("Percentage :");
                        for (int n = 0; n < aas.length; n++) {
                            double tran = Double.parseDouble(sss[n]);
                            System.out
                                    .print(" " + aas[n] + " : " + String.format("%.2f", (tran / sumType) * 100) + " %");
                        }
                        System.out.println(" ");
                        over = (over / sum) * 100;
                        if(over > 100){
                            over = 100;
                        }
                        System.out.print("Percentage of Goal : " + String.format("%.2f", over)
                                + "%   Over : " + String.format("%.2f", (read.getOverMoney() / sum) * 100) + "%");
                        System.out.println(" ");
                        System.out.println(" ");
                        String[] arr = { String.valueOf(i), show[0] };
                        list.add(arr);
                        sumType = 0;
                    }

                } else if (menuCheck == 2) {
                    checkGoal = infoUser[2];
                    read.setInputStream(); 
                    System.out.println("---------------------------------------");
                    System.out.println("Please specify [mm/yy] such as 6/2020");
                    System.out.println("---------------------------------------");
                    System.out.print("Enter mm/yy :");
                    String date = scan.next();
                    if(date.length() < 6){
                        System.out.println("---------------------------------------");
                        System.out.println("           Not found data");
                        System.out.println("---------------------------------------");
                        continue;
                    }
                    ArrayList cal = read.calEachWeek(date, user);
                    if(cal.size() == 0){
                        System.out.println("---------------------------------------");
                        System.out.println("           Not found data");
                        System.out.println("---------------------------------------");
                        continue;
                    }
                    int sumType = 0;
                    System.out.println("\n--- Your goal weeks: " + infoUser[2] + " ---");
                    for (int i = 0; i < cal.size(); i++) {
                        String[] show = (String[]) cal.get(i);
                        // System.out.println("-----"+cal.size());
                        // System.out.println("-----"+read.getGetType().size());
                        // String[] b = read.getGetType().get(i);
                        // System.out.println("-----"+b.length);

                        String[] aas = read.getGetType().get(i);
                        String[] sss = read.getGetTypeValue().get(i);
                        double sum = Double.parseDouble(show[1]);
                        double over = Double.parseDouble(infoUser[2]);
                        read.setOverMoney(sum, over);
                        System.out.println("[" + i + "]  " + show[0] + "  over : " + read.getOverMoney() + "  sum : "
                                + sum + " item :" + show[3]);
                        for (int n = 0; n < aas.length; n++) {
                            for (int a = 0; a < allType.size(); a++) {
                                String[] dataType = (String[]) allType.get(a);
                                if (dataType[0].equals(aas[n])) {
                                    aas[n] = dataType[1];
                                    break;
                                }
                            }
                            sumType += Integer.parseInt(sss[n]);
                            System.out.print("     " + aas[n] + " : " + sss[n]);
                        }
                        System.out.println(" ");
                        System.out.print("Percentage :");
                        for (int n = 0; n < aas.length; n++) {
                            double tran = Double.parseDouble(sss[n]);
                            System.out
                                    .print(" " + aas[n] + " : " + String.format("%.2f", (tran / sumType) * 100) + " %");
                        }
                        System.out.println(" ");
                        over = (over / sum) * 100;
                        if(over > 100){
                            over = 100;
                        }
                        System.out.print("Percentage of Goal : " + String.format("%.2f", over)
                                + "%   Over : " + String.format("%.2f", (read.getOverMoney() / sum) * 100) + "%");
                        System.out.println(" ");
                        System.out.println(" ");
                        String[] arr = { String.valueOf(i), show[0] };
                        list.add(arr);
                        sumType = 0;
                    }
                } else if (menuCheck == 3) {
                    checkGoal = infoUser[3];
                    read.setInputStream();
                    System.out.println("---------------------------------------");
                    System.out.println("Please specify [mm/yy] such as 6/2020");
                    System.out.println("---------------------------------------");
                    System.out.print("Enter mm/yy :");
                    String date = scan.next();
                    
                    ArrayList cal = read.calEachMonth(date, user);
                    if(cal.size() == 0){
                        System.out.println("---------------------------------------");
                        System.out.println("           Not found data");
                        System.out.println("---------------------------------------");
                        continue;
                    }
                    int sumType = 0;
                    System.out.println("\n--- Your goal months: " + infoUser[3] + " ---");
                    for (int i = 0; i < cal.size(); i++) {
                        String[] show = (String[]) cal.get(i);
                        String[] aas = read.getGetType().get(i);
                        String[] sss = read.getGetTypeValue().get(i);
                        double sum = Double.parseDouble(show[1]);
                        double over = Double.parseDouble(infoUser[3]);
                        read.setOverMoney(sum, over);
                        System.out.println("[" + i + "]  " + show[0] + "  over : " + read.getOverMoney() + "  sum : "
                                + sum + " item :" + show[3]);
                        for (int n = 0; n < aas.length; n++) {
                            for (int a = 0; a < allType.size(); a++) {
                                String[] dataType = (String[]) allType.get(a);
                                if (dataType[0].equals(aas[n])) {
                                    aas[n] = dataType[1];
                                    break;
                                }
                            }
                            sumType += Integer.parseInt(sss[n]);
                            System.out.print("     " + aas[n] + " : " + sss[n]);
                        }
                        System.out.println(" ");
                        System.out.print("Percentage :");
                        for (int n = 0; n < aas.length; n++) {
                            double tran = Double.parseDouble(sss[n]);
                            System.out
                                    .print(" " + aas[n] + " : " + String.format("%.2f", (tran / sumType) * 100) + " %");
                        }
                        System.out.println(" ");
                        over = (over / sum) * 100;
                        if(over > 100){
                            over = 100;
                        }
                        System.out.print("Percentage of Goal : " + String.format("%.2f", over)
                                + "%   Over : " + String.format("%.2f", (read.getOverMoney() / sum) * 100) + "%");
                        System.out.println(" ");
                        System.out.println(" ");
                        String[] arr = { String.valueOf(i), show[0] };
                        list.add(arr);
                        sumType = 0;
                    }
                }
                System.out.print("Do you want See more details [Y/N] :");
                char details = scan.next().charAt(0);

                if (details != 'y' && details != 'Y') {
                    continue;
                }
                // if (checkMenu == 1) {
                read.setInputStream();
                System.out.print("Enter List number :");
                String checkDate = scan.next();
                boolean check = false;
                for (int i = 0; i < list.size(); i++) {
                    String[] infoList = list.get(i);
                    if (checkDate.equals(infoList[0])) {
                        read.setDataInputStream(infoList[1], user);
                        check = true;
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("--------------------------");
                    System.out.println(" --- List not Match ---");
                    System.out.println("--------------------------");
                    continue;
                }

                System.out.println("[1] check all product");
                System.out.println("[2] check over product");
                System.out.print("Enter menu : ");
                int checkMenu = 0;
                try {
                    checkMenu = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("--------------------------");
                    System.out.println(" ---  not Match ---");
                    System.out.println("--------------------------");
                    continue;
                }
                if(checkMenu <1 || checkMenu >2){
                    System.out.println("--------------------------");
                    System.out.println(" ---  not Match ---");
                    System.out.println("--------------------------");
                    continue;
                }
                if (checkMenu == 1) {
                    ArrayList data = read.getDataInputStream();
                    System.out.println("************************************************");
                    System.out.println("date\t\t name product\t\tprice\t\tname\t\tprimarykey\t\ttype");
                    System.out.println("************************************************");
                    double checkMoney = 0;
                    for (int i = 0; i < data.size(); i++) {
                        String[] array = (String[]) data.get(i);
                        double transformMoney = Double.parseDouble(array[2]);
                        checkMoney += transformMoney;
                        if (checkMoney > Double.parseDouble(checkGoal)) {
                            for (int n = 0; n < array.length; n++) {
                                char a = '1';
                                char b = '2';
                                try {
                                    a = array[n].charAt(2);
                                    b = array[n].charAt(1);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                if(n == 5){
                                    for (int e = 0; e < allType.size(); e++) {
                                        String[] dataType = (String[]) allType.get(e);
                                        if (dataType[0].equals(array[n])) {
                                            array[n] = dataType[1];
                                            break;
                                        }
                                    }
                                }
                                if (a == '/' || b == '/') {
                                    System.out.print("*" + array[n]);
                                    System.out.print("\t\t");
                                } else {
                                    System.out.print(array[n]);
                                    System.out.print("\t\t");
                                }
                            }
                            System.out.println("");
                        } else {
                            for (int n = 0; n < array.length; n++) {
                                if(n == 5){
                                    for (int a = 0; a < allType.size(); a++) {
                                        String[] dataType = (String[]) allType.get(a);
                                        if (dataType[0].equals(array[n])) {
                                            array[n] = dataType[1];
                                            break;
                                        }
                                    }
                                }
                                System.out.print(array[n]);
                                System.out.print("\t\t");
                            }
                            System.out.println("");
                        }
                    }
                    read.setOverMoney(checkMoney, Double.parseDouble(checkGoal));
                    String over = String.format("%.2f", read.getOverMoney());
                    System.out.println("\n\t\t\t\tOver : " + over + "\t\tSum : " + read.getSum() + "\n");
                } else if (checkMenu == 2) {
                    ArrayList data = read.getDataInputStream();
                    System.out.println("************************************************");
                    System.out.println("date\t\t name product\t\tprice\t\tname\t\tprimarykey\t\ttype");
                    System.out.println("************************************************");
                    double checkMoney = 0;
                    for (int i = 0; i < data.size(); i++) {
                        String[] array = (String[]) data.get(i);
                        double transformMoney = Double.parseDouble(array[2]);
                        checkMoney += transformMoney;
                        if (checkMoney > Double.parseDouble(checkGoal)) {
                            for (int n = 0; n < array.length; n++) {
                                char a = '1';
                                char b = '2';
                                try {
                                    a = array[n].charAt(2);
                                    b = array[n].charAt(1);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                                if(n == 5){
                                    for (int e = 0; e < allType.size(); e++) {
                                        String[] dataType = (String[]) allType.get(e);
                                        if (dataType[0].equals(array[n])) {
                                            array[n] = dataType[1];
                                            break;
                                        }
                                    }
                                }
                                if (a == '/' || b == '/') {
                                    System.out.print("*" + array[n]);
                                    System.out.print("\t\t");
                                } else {
                                    System.out.print(array[n]);
                                    System.out.print("\t\t");
                                }
                            }
                            System.out.println("");
                        }
                    }
                    read.setOverMoney(checkMoney, Double.parseDouble(checkGoal));
                    String over = String.format("%.2f", read.getOverMoney());
                    System.out.println("\n\t\t\t\tOver : " + over + "\t\tSum : " + read.getSum() + "\n");
                }
            } else if (menu == 2) {
                ManageInfo detect = new ManageInfo();
                detect.setInputStream();
                detect.setReadNameAll();
                ArrayList a = detect.getReadNameAll();
                System.out.println("\n********** List Name **********");
                for (int i = 0; i < a.size(); i++) {
                    System.out.println("[" + i + "] " + a.get(i));
                }
                System.out.print("Enter number Name : ");
                int numberName;
                try {
                    numberName = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("--------------------------------");
                    System.out.println("Please specify the number type.");
                    System.out.println("--------------------------------");
                    continue;
                }
                String listName;
                try {
                    listName = (String) a.get(numberName);
                } catch (Exception e) {
                    System.out.println("--------------------------------");
                    System.out.println("Not Found.");
                    System.out.println("--------------------------------");
                    continue;
                }
                detect.setInputStream();
                ArrayList list = detect.calListName(listName);
                for (int i = 0; i < list.size(); i++) {
                    String[] array = (String[]) list.get(i);
                    for (int n = 0; n < array.length; n++) {
                        System.out.print(array[n]);
                        System.out.print("\t\t");
                    }
                    System.out.println("");
                }
                System.out.println("\n\t\t\t\tSUM : " + detect.getSum());

            } else if (menu == 3) {
                System.out.println("------ Setting Goal. ------");
                System.out.println("[1] set all goal days/weeks/months");
                System.out.println("[2] set goal days");
                System.out.println("[3] set goal weeks");
                System.out.println("[4] set goal months");
                System.out.print("Enter menu : ");
                int menuGoal = scan.nextInt();
                if (menuGoal == 1) {
                    ManageInfo manage = new ManageInfo();
                    System.out.print("Enter GoatDays : ");
                    double days = scan.nextDouble();
                    System.out.print("Enter GoatWeeks : ");
                    double weeks = scan.nextDouble();
                    System.out.print("Enter GoatMonths : ");
                    double months = scan.nextDouble();

                    manage.setGoalDays(days);
                    manage.setGoalWeek(weeks);
                    manage.setGoalMonths(months);
                    manage.setName(user);
                    manage.calSettingGoal();
                } else if (menuGoal == 2) {
                    ManageInfo manage = new ManageInfo();
                    System.out.print("Enter GoatDays : ");
                    double days = scan.nextDouble();
                    manage.setGoalDays(days);
                    manage.setName(user);
                    manage.calSettingGoalDays();
                } else if (menuGoal == 3) {
                    ManageInfo manage = new ManageInfo();
                    System.out.print("Enter GoatWeeks : ");
                    double weeks = scan.nextDouble();
                    manage.setGoalWeek(weeks);
                    manage.setName(user);
                    manage.calSettingGoalWeeks();
                } else if (menuGoal == 4) {
                    ManageInfo manage = new ManageInfo();
                    System.out.print("Enter GoatMonths : ");
                    double months = scan.nextDouble();
                    manage.setGoalMonths(months);
                    manage.setName(user);
                    manage.calSettingGoalMonths();
                }

            } else if (menu == 4) {
                WriterInfo writer = new WriterInfo();
                ReaderInfo read = new ReaderInfo();
                if (read.calTypeProduct() == false) {
                    System.out.println("---------------------------------");
                    System.out.println("       is not Type data");
                    System.out.println("---------------------------------");
                    continue;
                }
                ArrayList typeProduct = read.getTypeProduct();
                System.out.println("---- Please specify your Type Product. ----");
                for (int i = 0; i < typeProduct.size(); i++) {
                    String[] array = (String[]) typeProduct.get(i);
                    System.out.println("[" + i + "]  " + array[1]);
                }
                System.out.print(" -Enter number Type :");
                int inputType;
                String[] dataType;
                try {
                    inputType = scan.nextInt();
                    dataType = (String[]) typeProduct.get(inputType);
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("---------------------------------");
                    System.out.println("      is not match Type");
                    System.out.println(e);
                    System.out.println("---------------------------------");
                    continue;
                }

                System.out.println("Please specify your Product name.");
                System.out.print(" -Enter product name :");
                String proName = scan.next();
                System.out.println("Please specify your price.");
                System.out.print(" -Enter price :");
                double price;
                try {
                    price = scan.nextDouble();
                    writer.setPrice(price);
                } catch (Exception e) {
                    System.out.println("---------------------------------");
                    System.out.println("is not match must be number only");
                    System.out.println(e);
                    System.out.println("---------------------------------");
                    continue;
                }
                if (price <= 0) {
                    System.out.println("---------------------------------");
                    System.out.println("do not be less than zero");
                    System.out.println("---------------------------------");
                    continue;
                }
                read.setInputStream();
                read.setAllDataCSV();
                writer.setPrimaryKey(read.getPrimaryKey());
                writer.setDate();
                writer.setName(user);
                writer.setProductName(proName);
                writer.setStreamOutput();
                boolean result = writer.calWriter(dataType);
                if (result == true) {
                    System.out.println("----------------");
                    System.out.println("insert sucess");
                    System.out.println("----------------");
                } else {
                    System.out.println("----------------");
                    System.out.println("insert Failed");
                    System.out.println("----------------");
                }

            } else if (menu == 5) {
                WriterInfo addWriter = new WriterInfo();
                ManageInfo manage = new ManageInfo();
                System.out.println("--- add type ---");
                System.out.print("Enter Type you want to add. : ");
                String addType = scan.next();
                if (manage.calCheckType(addType) == false) {
                    System.out.println("--------------------------------");
                    System.out.println("This type is already in the data.");
                    System.out.println("--------------------------------");
                    continue;
                }
                addWriter.setType(addType);
                addWriter.calAddType();

            } else if (menu == 6) {
                ManageInfo manage = new ManageInfo();
                manage.setInputStream();
                manage.setAllDataCSV();
                ArrayList array = manage.getAllDataCSV();
                ArrayList getArrayName;

                for (int i = 0; i < array.size(); i++) {
                    String[] arrayLine = (String[]) array.get(i);
                    manage.setReadName(user);

                }
                getArrayName = manage.getReadName();
                if (getArrayName.size() == 0) {
                    System.out.println("----------------");
                    System.out.println("Not found Name");
                    System.out.println("----------------");
                    continue;
                }
                for (int i = 0; i < getArrayName.size(); i++) {
                    String[] list = (String[]) getArrayName.get(i);
                    System.out.println(
                            "[" + list[4] + "]\t" + list[3] + "\t" + list[1] + "\t" + list[2] + "\t" + list[0]);
                }
                System.out.println("Please specify ID Product");
                System.out.print("Enter ID Product : ");
                int idProduct = 0;
                try {
                    idProduct = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("");
                    System.out.println("---------------------------------");
                    System.out.println("is not match must be number only");
                    System.out.println(e);
                    System.out.println("---------------------------------");
                    continue;
                }
                boolean check = false;
                for (int i = 0; i < getArrayName.size(); i++) {
                    String[] list = (String[]) getArrayName.get(i);
                    int id = Integer.parseInt(list[4]);
                    if (idProduct == id) {
                        if (manage.calTypeProduct() == false) {
                            System.out.println("---------------------------------");
                            System.out.println("       is not Type data");
                            System.out.println("---------------------------------");
                            continue;
                        }
                        ArrayList typeProduct = manage.getTypeProduct();
                        System.out.println("---- Please specify your Type Product. ----");
                        for (int n = 0; n < typeProduct.size(); n++) {
                            String[] array1 = (String[]) typeProduct.get(n);
                            System.out.println("[" + n + "]  " + array1[1]);
                        }
                        System.out.print(" -Enter number Type :");
                        int inputType;
                        String[] dataType;
                        try {
                            inputType = scan.nextInt();
                            dataType = (String[]) typeProduct.get(inputType);
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println("---------------------------------");
                            System.out.println("      is not match Type");
                            System.out.println(e);
                            System.out.println("---------------------------------");
                            continue;
                        }

                        check = true;
                        manage.setCheckID(idProduct);

                        System.out.println("Please specify edit name product");
                        System.out.print("Enter : ");
                        String editNameProduct = scan.next();

                        System.out.println("Please specify edit price");
                        System.out.print("Enter : ");
                        int editPrice = scan.nextInt();

                        manage.setEditLine(editPrice, editNameProduct);
                        String[] getID = manage.getCheckID();

                        WriterInfo writer = new WriterInfo(array, getID);
                        writer.calWriterEdit(user, dataType[0]);
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("");
                    System.out.println("---------------------------------");
                    System.out.println("is not match Primary Key");
                    System.out.println("---------------------------------");
                }

            } else if (menu == 7) {
                ManageInfo manage = new ManageInfo();
                manage.setInputStream();
                manage.calTypeProduct();
                ArrayList typeProduct = manage.getTypeProduct();
                System.out.println("---- Please specify your Type Product. ----");
                for (int n = 0; n < typeProduct.size(); n++) {
                    String[] array1 = (String[]) typeProduct.get(n);
                    System.out.println("[" + n + "]  " + array1[1]);
                }
                System.out.print(" -Enter number Type :");
                int inputType;
                String[] dataType;
                try {
                    inputType = scan.nextInt();
                    dataType = (String[]) typeProduct.get(inputType);
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("---------------------------------");
                    System.out.println("      is not match Type");
                    System.out.println(e);
                    System.out.println("---------------------------------");
                    continue;
                }
                System.out.println("Type to want Edit");
                System.out.print("Enter Type : ");
                String editType = scan.next();

                if(manage.calEditType(editType, dataType[0]) == true){
                    ArrayList editSuccess = manage.getTypeProduct();
                    WriterInfo w = new WriterInfo();
                    w.calWriterEditType(editSuccess);
                    System.out.println("---------------------------------");
                    System.out.println("      Edit Type Success");
                    System.out.println("---------------------------------");
                }
                else{
                    System.out.println("---------------------------------");
                    System.out.println("           Fali Edit");
                    System.out.println("---------------------------------");
                    continue;
                }
            } else if (menu == 8) {
                ManageInfo manage = new ManageInfo();
                manage.setInputStream();
                manage.setAllDataCSV();
                ArrayList array = manage.getAllDataCSV();
                ArrayList getArrayName;

                for (int i = 0; i < array.size(); i++) {
                    String[] arrayLine = (String[]) array.get(i);
                    manage.setReadName(user);
                }
                getArrayName = manage.getReadName();
                if (getArrayName.size() == 0) {
                    System.out.println("----------------");
                    System.out.println("Not found Name");
                    System.out.println("----------------");
                    continue;
                }
                for (int i = 0; i < getArrayName.size(); i++) {
                    String[] list = (String[]) getArrayName.get(i);
                    System.out.println(
                            "[" + list[4] + "]\t" + list[3] + "\t" + list[1] + "\t" + list[2] + "\t" + list[0]);
                }

                System.out.println("Please specify ID Product");
                System.out.print("Enter ID Product : ");
                int idProduct = 0;
                try {
                    idProduct = scan.nextInt();
                } catch (Exception e) {
                    System.out.println("");
                    System.out.println("---------------------------------");
                    System.out.println("is not match must be number only");
                    System.out.println(e);
                    System.out.println("---------------------------------");
                    break;
                }
                boolean check = false;
                for (int i = 0; i < getArrayName.size(); i++) {
                    String[] list = (String[]) getArrayName.get(i);
                    int id = Integer.parseInt(list[4]);
                    if (idProduct == id) {
                        check = true;
                        WriterInfo writer = new WriterInfo();
                        writer.setStreamDelete();
                        writer.setAllDataCSV(array);
                        writer.calDeleteProduct(idProduct);
                        break;
                    }
                }
                if (check == false) {
                    System.out.println("");
                    System.out.println("---------------------------------");
                    System.out.println("is not match Primary Key");
                    System.out.println("---------------------------------");
                }
                // change Account
            } else if (menu == 9) {
                ManageInfo loginManage = new ManageInfo();
                System.out.println("-------- Change account --------");
                while (true) {
                    System.out.print("Username :");
                    String newLogin = scan.next();
                    boolean resultLogin = loginManage.calCheckUser(newLogin);
                    if (resultLogin == false) {
                        user = newLogin;
                        System.out.println("success login : " + newLogin);
                        System.out.println("---------------------");
                        break;
                    } else {
                        System.out.println("-------- Unknown --------");
                    }
                }

            } else {
                System.out.println("----------------");
                System.out.println("Exit Programe");
                System.out.println("----------------");
                return;
            }
        }
    }
}
