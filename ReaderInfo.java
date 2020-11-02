import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ReaderInfo extends InfoMain {
    protected BufferedReader inputStream;
    private ArrayList<String[]> dataInputStream = new ArrayList<>();
    private double sum;
    protected ArrayList<String[]> allDataCSV = new ArrayList<>();
    protected ArrayList<String[]> nameDataCSV = new ArrayList<>();
    protected ArrayList<String> readNameAll = new ArrayList<>();
    protected ArrayList<String[]> eachDayWeekMonth = new ArrayList<>();
    protected ArrayList<String[]> typeProduct = new ArrayList<>();
    protected ArrayList<String[]> getType = new ArrayList<>();
    protected ArrayList<String[]> getTypeValue = new ArrayList<>();
    protected ArrayList<String[]> allTypeCSV = new ArrayList<>();


    protected String[] checkID;

    public ReaderInfo() {
    }

    public ReaderInfo(String date, String user) throws IOException {
        setInputStream();
        setDataInputStream(date, user);
    }

    public void setAllDataCSV() throws IOException {
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] readline = str.split(",");
            allDataCSV.add(readline);
            primaryKey = Integer.parseInt(readline[4]);
        }
    }

    public ArrayList getAllDataCSV() {
        return allDataCSV;
    }

    public ArrayList<String[]> getGetTypeValue() {
        return getTypeValue;
    }

    public void setCheckID(int id) {
        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] array = allDataCSV.get(i);
            String aa = String.valueOf(id);
            if (array[4].equals(aa)) {
                checkID = array;
                return;
            }
        }
    }

    public String[] getCheckID() {
        return checkID;
    }

    public void setReadName(String name) {
        String str;
        nameDataCSV.clear();
        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] getName = allDataCSV.get(i);
            if (name.equals(getName[3])) {
                nameDataCSV.add(getName);
            }
        }
    }

    public ArrayList getReadName() {
        return nameDataCSV;
    }

    public void setReadNameAll() throws IOException {
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] getName = str.split(",");
            if (readNameAll.size() == 0) {
                readNameAll.add(getName[3]);
                continue;
            }
            int check = 0;
            for (int i = 0; i < readNameAll.size(); i++) {
                if (getName[3].equals(readNameAll.get(i))) {
                    check++;
                }
            }
            if (check == 0) {
                readNameAll.add(getName[3]);
            }
        }
    }

    public ArrayList<String> getReadNameAll() {
        return readNameAll;
    }

    public void setInputStream() {
        try {
            inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }

    public void setGetType(ArrayList<String[]> getType) {
        this.getType = getType;
    }

    public ArrayList<String[]> getGetType() {
        return getType;
    }

    public ArrayList<String[]> calEachDay(String date, String user) throws IOException {
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            if (array[0].endsWith(date) && array[3].equals(user)) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = array[0];
                    arr[1] = array[2];
                    arr[3] = "1";
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    eachDayWeekMonth.add(arr);
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;// array
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals(array[0])) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        each[1] = String.valueOf(c);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }
                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = array[0];
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }
            }
        }
        return eachDayWeekMonth;
    }

    public ArrayList<String[]> calEachMonth(String date, String user) throws IOException {
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            // 01/6/2020 11/11/2020
            String p1 = String.valueOf(array[0].charAt(3));
            String p2 = String.valueOf(array[0].charAt(4));
            String monthYear = null;
            if (p2.equals("/")) {
                monthYear = p1 + p2 + array[0].charAt(5)+array[0].charAt(6)+array[0].charAt(7)+array[0].charAt(8);
            } else {
                monthYear = p1 + p2 + "/" + array[0].charAt(6)+array[0].charAt(7)+array[0].charAt(8)+array[0].charAt(9);
            }
            if (monthYear.endsWith(date)&& array[3].equals(user)) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = monthYear;
                    arr[1] = array[2];
                    arr[3] = "1";
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    eachDayWeekMonth.add(arr);
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals(monthYear)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }

                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = monthYear;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }

            }
        }
        return eachDayWeekMonth;
    }

    public ArrayList<String[]> calEachWeek(String date, String user) throws IOException {
        String str;// 6/2020 11/2020
        String month, year;
        if (date.charAt(1) != '/') {
            month = String.valueOf(date.charAt(0)) + String.valueOf(date.charAt(1));
            year = String.valueOf(date.charAt(3)) + String.valueOf(date.charAt(4)) + String.valueOf(date.charAt(5))
                    + String.valueOf(date.charAt(6));
        } else {
            month = String.valueOf(date.charAt(0));
            year = String.valueOf(date.charAt(2)) + String.valueOf(date.charAt(3)) + String.valueOf(date.charAt(4))
                    + String.valueOf(date.charAt(5));
        }
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(","); // 11/11/2222 02/5/2020
            array = str.split(",");
            char one = array[0].charAt(0);
            char two = array[0].charAt(1);
            char arrayMonth = array[0].charAt(5);
            String monthArray, yearArry;
            String day = String.valueOf(one) + String.valueOf(two);
            if (arrayMonth != '/') {
                monthArray = String.valueOf(array[0].charAt(3));
                yearArry = String.valueOf(array[0].charAt(5)) + String.valueOf(array[0].charAt(6))
                        + String.valueOf(array[0].charAt(7)) + String.valueOf(array[0].charAt(8));
            } else {
                monthArray = String.valueOf(array[0].charAt(3)) + String.valueOf(array[0].charAt(4));
                yearArry = String.valueOf(array[0].charAt(6)) + String.valueOf(array[0].charAt(7))
                        + String.valueOf(array[0].charAt(8)) + String.valueOf(array[0].charAt(9));
            }
            // add Attribute
            if ((array[3].equals(user)) && ((day.equals("01") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("02") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("03") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("04") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("05") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("06") && month.equals(monthArray) && year.equals(yearArry))
                    || (day.equals("07") && month.equals(monthArray) && year.equals(yearArry)))) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = "1" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = "1";
                    eachDayWeekMonth.add(arr);
                    String[] data = {array[5]};
                    String[] data1 = { "1" };
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals("1" + "/" + monthArray + "/" + yearArry)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }

                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = "1" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }

            } else if ((array[3].equals(user))
                    && ((day.equals("08") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("09") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("10") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("11") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("12") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("13") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("14") && month.equals(monthArray) && year.equals(yearArry)))) {

                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = "2" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = "1";
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }

                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals("2" + "/" + monthArray + "/" + yearArry)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }

                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = "2" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }
            } else if ((array[3].equals(user))
                    && ((day.equals("15") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("16") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("17") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("18") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("19") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("20") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("21") && month.equals(monthArray) && year.equals(yearArry)))) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = "3" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = "1";
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals("3" + "/" + monthArray + "/" + yearArry)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }
                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = "3" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }
            } else if ((array[3].equals(user))
                    && ((day.equals("22") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("23") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("24") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("25") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("26") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("27") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("28") && month.equals(monthArray) && year.equals(yearArry)))) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = "4" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = "1";
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    eachDayWeekMonth.add(arr);
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals("4" + "/" + monthArray + "/" + yearArry)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }

                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = "4" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }
            } else if ((array[3].equals(user))
                    && ((day.equals("29") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("30") && month.equals(monthArray) && year.equals(yearArry))
                            || (day.equals("31") && month.equals(monthArray) && year.equals(yearArry)))) {
                if (eachDayWeekMonth.size() == 0) {
                    String[] arr = new String[4];
                    arr[0] = "5" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = "1";
                    eachDayWeekMonth.add(arr);
                    String[] data = {array[5]};
                    String[] data1 = {"1"};
                    getType.add(data);
                    getTypeValue.add(data1);
                    continue;
                }
                boolean check = false;
                boolean checkType = false;
                int num = 0;
                for (int i = 0; i < eachDayWeekMonth.size(); i++) {
                    String[] each = eachDayWeekMonth.get(i);
                    String[] numType = getType.get(i);
                    String[] numValue = getTypeValue.get(i);
                    if (each[0].equals("5" + "/" + monthArray + "/" + yearArry)) {
                        double a = Double.parseDouble(each[1]);
                        double b = Double.parseDouble(array[2]);
                        double c = a + b;
                        each[1] = String.valueOf(c);
                        each[3] = String.valueOf(Integer.parseInt(each[3]) + 1);
                        check = true;
                        num = i;
                        for (int n = 0; n < numType.length; n++) {
                            if (array[5].equals(numType[n])) {
                                // add value
                                int data = Integer.parseInt(numValue[n]) + 1;
                                numValue[n] = String.valueOf(data);
                                getTypeValue.set(i, numValue);
                                checkType = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (check == true && checkType == false) {
                    String[] data = getType.get(num);
                    String[] data1 = getGetTypeValue().get(num);
                    int len = data.length;
                    String[] oldData = new String[len + 1];
                    String[] oldData1 = new String[len + 1];

                    for (int i = 0; i < data.length; i++) {
                        oldData[i] = data[i];
                        oldData1[i] = data1[i];
                    }
                    oldData[len] = array[5];
                    oldData1[(data1.length)] = "1";
                    getType.set(num, oldData);
                    getTypeValue.set(num, oldData1);
                }
                if (check == false) {
                    String[] arr = new String[4];
                    arr[0] = "5" + "/" + monthArray + "/" + yearArry;
                    arr[1] = array[2];
                    arr[3] = String.valueOf(1);
                    eachDayWeekMonth.add(arr);
                    String[] data = { array[5] };
                    String[] data1 = { "1" };
                    getTypeValue.add(data1);
                    getType.add(data);
                    continue;
                }
            }
        }
        return eachDayWeekMonth;

    }

    public void setDataInputStream(String date, String user) throws IOException {
        String[] arrayData;
        char a = date.charAt(1); // 20/6/2020 // 01/12/2020 20/6/2020 1/12/2020 1/11/2022
        if (date.length() == 8 || (date.length() == 9) && (a == '/')) {
            char x = date.charAt(0); // 1/6/2020
            int n = Character.getNumericValue(x);
            char month = date.charAt(2);
            String p1 = String.valueOf(date.charAt(4));
            String p2 = String.valueOf(date.charAt(5));
            String p3 = String.valueOf(date.charAt(6));
            String p4 = String.valueOf(date.charAt(7));
            String year = p1 + p2 + p3 + p4;

            if (n == 1) {
                String str;
                while ((str = inputStream.readLine()) != null) {
                    arrayData = str.split(",");
                    char one = arrayData[0].charAt(0);
                    char two = arrayData[0].charAt(1);
                    char arrayMonth = arrayData[0].charAt(3);
                    String ap1 = String.valueOf(arrayData[0].charAt(5));
                    String ap2 = String.valueOf(arrayData[0].charAt(6));
                    String ap3 = String.valueOf(arrayData[0].charAt(7));
                    String ap4 = String.valueOf(arrayData[0].charAt(8));
                    String yearArray = ap1 + ap2 + ap3 + ap4;
                    String one1 = String.valueOf(one);
                    String two1 = String.valueOf(two);
                    String dd = one1 + two1;
                    if ((dd.equals("01") && arrayMonth == month && year.equals(yearArray)) && arrayData[3].equals(user)
                            || (dd.equals("02") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("03") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("04") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("05") && arrayMonth == month && year.equals(yearArray))
                            || (dd.equals("06") && arrayMonth == month && year.equals(yearArray))
                            || (dd.equals("07") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)) {
                        dataInputStream.add(arrayData);
                        double preSum = Double.parseDouble(arrayData[2]);
                        sum += preSum;
                    }
                }
            } else if (n == 2) {
                String str;
                while ((str = inputStream.readLine()) != null) {
                    arrayData = str.split(",");
                    char one = arrayData[0].charAt(0);
                    char two = arrayData[0].charAt(1);
                    char arrayMonth = arrayData[0].charAt(3);
                    String ap1 = String.valueOf(arrayData[0].charAt(5));
                    String ap2 = String.valueOf(arrayData[0].charAt(6));
                    String ap3 = String.valueOf(arrayData[0].charAt(7));
                    String ap4 = String.valueOf(arrayData[0].charAt(8));
                    String yearArray = ap1 + ap2 + ap3 + ap4;
                    String one1 = String.valueOf(one);
                    String two1 = String.valueOf(two);
                    String dd = one1 + two1;
                    if ((dd.equals("08") && arrayMonth == month && year.equals(yearArray)) && arrayData[3].equals(user)
                            || (dd.equals("09") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("10") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("11") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("12") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("13") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("14") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)) {
                        dataInputStream.add(arrayData);
                        double preSum = Double.parseDouble(arrayData[2]);
                        sum += preSum;
                    }
                }
            } else if (n == 3) {
                String str;
                while ((str = inputStream.readLine()) != null) {
                    arrayData = str.split(",");
                    char one = arrayData[0].charAt(0);
                    char two = arrayData[0].charAt(1);
                    char arrayMonth = arrayData[0].charAt(3);
                    String ap1 = String.valueOf(arrayData[0].charAt(5));
                    String ap2 = String.valueOf(arrayData[0].charAt(6));
                    String ap3 = String.valueOf(arrayData[0].charAt(7));
                    String ap4 = String.valueOf(arrayData[0].charAt(8));
                    String yearArray = ap1 + ap2 + ap3 + ap4;
                    String one1 = String.valueOf(one);
                    String two1 = String.valueOf(two);
                    String dd = one1 + two1;
                    if ((dd.equals("15") && arrayMonth == month && year.equals(yearArray)) && arrayData[3].equals(user)
                            || (dd.equals("16") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("17") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("18") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("19") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("20") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("21") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)) {
                        dataInputStream.add(arrayData);
                        double preSum = Double.parseDouble(arrayData[2]);
                        sum += preSum;
                    }
                }
            } else if (n == 4) {
                String str;
                while ((str = inputStream.readLine()) != null) {
                    arrayData = str.split(",");
                    char one = arrayData[0].charAt(0);
                    char two = arrayData[0].charAt(1);
                    char arrayMonth = arrayData[0].charAt(3);
                    String ap1 = String.valueOf(arrayData[0].charAt(5));
                    String ap2 = String.valueOf(arrayData[0].charAt(6));
                    String ap3 = String.valueOf(arrayData[0].charAt(7));
                    String ap4 = String.valueOf(arrayData[0].charAt(8));
                    String yearArray = ap1 + ap2 + ap3 + ap4;
                    String one1 = String.valueOf(one);
                    String two1 = String.valueOf(two);
                    String dd = one1 + two1;
                    if ((dd.equals("22") && arrayMonth == month && year.equals(yearArray)) && arrayData[3].equals(user)
                            || (dd.equals("23") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("24") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("25") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("26") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("27") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("28") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)) {
                        dataInputStream.add(arrayData);
                        double preSum = Double.parseDouble(arrayData[2]);
                        sum += preSum;
                    }
                }
            } else if (n == 5) {
                String str;
                while ((str = inputStream.readLine()) != null) {
                    arrayData = str.split(",");
                    char one = arrayData[0].charAt(0);
                    char two = arrayData[0].charAt(1);
                    char arrayMonth = arrayData[0].charAt(3);
                    String ap1 = String.valueOf(arrayData[0].charAt(5));
                    String ap2 = String.valueOf(arrayData[0].charAt(6));
                    String ap3 = String.valueOf(arrayData[0].charAt(7));
                    String ap4 = String.valueOf(arrayData[0].charAt(8));
                    String yearArray = ap1 + ap2 + ap3 + ap4;
                    String one1 = String.valueOf(one);
                    String two1 = String.valueOf(two);
                    String dd = one1 + two1;
                    if ((dd.equals("29") && arrayMonth == month && year.equals(yearArray)) && arrayData[3].equals(user)
                            || (dd.equals("30") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)
                            || (dd.equals("31") && arrayMonth == month && year.equals(yearArray))
                                    && arrayData[3].equals(user)) {
                        dataInputStream.add(arrayData);
                        double preSum = Double.parseDouble(arrayData[2]);
                        sum += preSum;
                    }
                }
            }
        } else {
            String str;
            while ((str = inputStream.readLine()) != null) {
                arrayData = str.split(",");
                if (arrayData[0].endsWith(date) && arrayData[3].equals(user)) {
                    dataInputStream.add(arrayData);
                    double preSum = Double.parseDouble(arrayData[2]);
                    sum += preSum;
                }
            }
        }

    }

    public ArrayList getDataInputStream() {
        return dataInputStream;
    }

    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public ArrayList<String[]> calListName(String name) throws NumberFormatException, IOException {
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] arrayData = str.split(",");
            if (arrayData[3].equals(name)) {
                dataInputStream.add(arrayData);
                double preSum = Double.parseDouble(arrayData[2]);
                sum += preSum;
            }
        }
        return dataInputStream;
    }

    public boolean calTypeProduct() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileType), "UTF8"));
        String str;
        boolean check = false;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            typeProduct.add(array);
            check = true;
        }
        return check;
    }

    public void setTypeProduct(ArrayList<String[]> typeProduct) {
        this.typeProduct = typeProduct;
    }

    public ArrayList<String[]> getTypeProduct() {
        return typeProduct;
    }
    public ArrayList<String[]> calAllTypeCSV() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileType), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            allTypeCSV.add(array);
        }
        return allTypeCSV;
    }
}