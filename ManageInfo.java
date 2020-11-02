import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ManageInfo extends ReaderInfo {
    private ArrayList<String[]> detectName = new ArrayList<>();
    private double overMoney;

    public ManageInfo() {
    }

    public void setEditLine(int price, String nameProduct) {
        checkID[2] = String.valueOf(price);
        checkID[1] = nameProduct;
    }

    public void setOverMoney(double max, double min) {
        double sum = max - min;
        if (sum < 0) {
            sum = 0;
        }
        this.overMoney = sum;
    }

    public double getOverMoney() {
        return overMoney;
    }

    public boolean checkLogin(ArrayList allUser, String user) {
        String str;
        for (int i = 0; i < allUser.size(); i++) {
            if (user.equals(allUser.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean calCheckUser(String user) throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            if (array[0].equals(user)) {
                return false;
            }
        }
        return true;
    }

    public void setReadUsername(String name) {
        String str;
        nameDataCSV.clear();
        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] getName = allDataCSV.get(i);
            if (name.equals(getName[3])) {
                nameDataCSV.add(getName);
            }
        }
    }

    public String[] calInfoDayMonthYear(String user) throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            if (array[0].equals(user)) {
                String[] arr = {array[0],array[1],array[2],array[3]};
                return arr;
            }
        }
        return null;
    }

    public void calSettingGoal() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            detectName.add(array);
        }
        FileWriter streamOutput = new FileWriter(fileRegis);
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (array[0].equals(name)) {
                String[] arr = { name, String.valueOf(goalDays), String.valueOf(goalWeek), String.valueOf(goalMonths) };
                detectName.set(i, arr);
            }
        }
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (i == 0) {
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalDays));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalWeek));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalMonths));
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            } else {
                streamOutput.append("\n");
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalDays));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalWeek));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(goalMonths));
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            }
        }
        streamOutput.flush();
        streamOutput.close();
        inputStream.close();
    }
    public void calSettingGoalDays() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            detectName.add(array);
        }
        FileWriter streamOutput = new FileWriter(fileRegis);
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (array[0].equals(name)) {
                String[] arr = { name, String.valueOf(goalDays),array[2],array[3]};
                detectName.set(i, arr);
            }
        }
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (i == 0) {
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            } else {
                streamOutput.append("\n");
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            }
        }
        streamOutput.flush();
        streamOutput.close();
        inputStream.close();
    }
    public void calSettingGoalWeeks() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            detectName.add(array);
        }
        FileWriter streamOutput = new FileWriter(fileRegis);
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (array[0].equals(name)) {
                String[] arr = { name, array[1],String.valueOf(goalWeek),array[3]};
                detectName.set(i, arr);
            }
        }
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (i == 0) {
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            } else {
                streamOutput.append("\n");
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            }
        }
        streamOutput.flush();
        streamOutput.close();
        inputStream.close();
    }

    public void calSettingGoalMonths() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        String str;
        while ((str = inputStream.readLine()) != null) {
            String[] array = str.split(",");
            detectName.add(array);
        }
        FileWriter streamOutput = new FileWriter(fileRegis);
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (array[0].equals(name)) {
                String[] arr = { name, array[1],array[2],String.valueOf(goalMonths)};
                detectName.set(i, arr);
            }
        }
        for (int i = 0; i < detectName.size(); i++) {
            String[] array = detectName.get(i);
            if (i == 0) {
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            } else {
                streamOutput.append("\n");
                if (array[0].equals(name)) {
                    streamOutput.append(name);
                    streamOutput.append(",");
                    streamOutput.append(array[1]);
                    streamOutput.append(",");
                    streamOutput.append(array[2]);
                    streamOutput.append(",");
                    streamOutput.append(array[3]);
                } else {
                    streamOutput.append(array[0]);
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[1]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[2]));
                    streamOutput.append(",");
                    streamOutput.append(String.valueOf(array[3]));
                }
            }
        }
        streamOutput.flush();
        streamOutput.close();
        inputStream.close();
    }
    public boolean calCheckType(String type) throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileType), "UTF8"));
        String str;
        while((str = inputStream.readLine()) != null){
            String[] array = str.split(",");
            if(array[1].equals(type)){
                return false;
            }
        }
        inputStream.close();
        return true;
        
    }
    public boolean calEditType(String nameType,String idType){
        for(int i =0;i<typeProduct.size();i++){
            String[] dataType = typeProduct.get(i);
            if(dataType[0].equals(idType)){
                dataType[1] = nameType;
                typeProduct.set(i, dataType);
                return true;
            }
        }
        return false;
    }
    // public void setDetectName(String detectName) throws IOException {
    // String str;
    // while((str = inputStream.readLine()) != null) {
    // for(int i = 0;)
    // }
    // }
}