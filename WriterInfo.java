import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class WriterInfo extends InfoMain {
    private FileWriter streamOutput;
    private FileWriter streamDelete;
    private FileWriter streamEdit;
    private FileWriter streamRegis;
    private ArrayList<String[]> allDataCSV;
    private String[] checkID;
    private String register;

    public WriterInfo() {
    }
    public WriterInfo(ArrayList allDataCSV, String[] checkID) throws IOException {
        this.allDataCSV = allDataCSV;
        this.checkID = checkID;
        streamEdit = new FileWriter(fileDir);

    }

    public void setAllDataCSV(ArrayList<String[]> allDataCSV) {
        this.allDataCSV = allDataCSV;
    }

    public ArrayList<String[]> getAllDataCSV() {
        return allDataCSV;
    }

    public void setStreamEdit(FileWriter streamEdit) throws IOException {
        this.streamEdit = new FileWriter(fileDir);
    }

    public FileWriter getStreamEdit() {
        return streamEdit;
    }

    public void setStreamOutput() throws IOException {
        streamOutput = new FileWriter(fileDir, true);
    }

    public FileWriter getStreamOutput() {
        return streamOutput;
    }

    public void setStreamDelete() throws IOException {
        streamDelete = new FileWriter(fileDir);
    }

    public FileWriter getStreamDelete() {
        return streamDelete;
    }

    public boolean calWriter() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        String str = inputStream.readLine();
        if (str == null) {
            streamOutput.append(formatDate);
            streamOutput.append(",");
            streamOutput.append(productName);
            streamOutput.append(",");
            streamOutput.append(price);
            streamOutput.append(",");
            streamOutput.append(name);
            streamOutput.append(",");
            streamOutput.append(String.valueOf(primaryKey + 1));
            streamOutput.flush();
            streamOutput.close();
        } else {
            streamOutput.append("\n");
            streamOutput.append(formatDate);
            streamOutput.append(",");
            streamOutput.append(productName);
            streamOutput.append(",");
            streamOutput.append(price);
            streamOutput.append(",");
            streamOutput.append(name);
            streamOutput.append(",");
            streamOutput.append(String.valueOf(primaryKey + 1));
            streamOutput.flush();
            streamOutput.close();
        }
        return true;
    }
    public boolean calWriter(String[] type) throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
        String str = inputStream.readLine();
        if (str == null) {
            streamOutput.append(formatDate);
            streamOutput.append(",");
            streamOutput.append(productName);
            streamOutput.append(",");
            streamOutput.append(price);
            streamOutput.append(",");
            streamOutput.append(name);
            streamOutput.append(",");
            streamOutput.append(String.valueOf(primaryKey + 1));
            streamOutput.append(",");
            streamOutput.append(type[0]);
            streamOutput.flush();
            streamOutput.close();
        } else {
            streamOutput.append("\n");
            streamOutput.append(formatDate);
            streamOutput.append(",");
            streamOutput.append(productName);
            streamOutput.append(",");
            streamOutput.append(price);
            streamOutput.append(",");
            streamOutput.append(name);
            streamOutput.append(",");
            streamOutput.append(String.valueOf(primaryKey + 1));
            streamOutput.append(",");
            streamOutput.append(type[0]);
            streamOutput.flush();
            streamOutput.close();
        }
        return true;
    }

    public boolean calWriterEdit(String user,String idType) throws IOException {
        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] array = allDataCSV.get(i);
            if (i == 0) {
                if (checkID[4].equals(array[4])) {
                    streamEdit.append(checkID[0]);
                    streamEdit.append(",");
                    streamEdit.append(checkID[1]);
                    streamEdit.append(",");
                    streamEdit.append(checkID[2]);
                    streamEdit.append(",");
                    streamEdit.append(user);
                    streamEdit.append(",");
                    streamEdit.append(checkID[4]);
                    streamEdit.append(",");
                    streamEdit.append(idType);
                } else {
                    streamEdit.append(array[0]);
                    streamEdit.append(",");
                    streamEdit.append(array[1]);
                    streamEdit.append(",");
                    streamEdit.append(array[2]);
                    streamEdit.append(",");
                    streamEdit.append(array[3]);
                    streamEdit.append(",");
                    streamEdit.append(array[4]);
                    streamEdit.append(",");
                    streamEdit.append(array[5]);
                }
                streamEdit.append("\n");
                continue;
            }
            if (i == allDataCSV.size() - 1) {
                if (checkID[4].equals(array[4])) {
                    streamEdit.append(checkID[0]);
                    streamEdit.append(",");
                    streamEdit.append(checkID[1]);
                    streamEdit.append(",");
                    streamEdit.append(checkID[2]);
                    streamEdit.append(",");
                    streamEdit.append(user);
                    streamEdit.append(",");
                    streamEdit.append(checkID[4]);
                    streamEdit.append(",");
                    streamEdit.append(idType);
                } else {
                    streamEdit.append(array[0]);
                    streamEdit.append(",");
                    streamEdit.append(array[1]);
                    streamEdit.append(",");
                    streamEdit.append(array[2]);
                    streamEdit.append(",");
                    streamEdit.append(array[3]);
                    streamEdit.append(",");
                    streamEdit.append(array[4]);
                    streamEdit.append(",");
                    streamEdit.append(array[5]);
                }
                break;
            }
            if (checkID[4].equals(array[4])) {
                streamEdit.append(checkID[0]);
                streamEdit.append(",");
                streamEdit.append(checkID[1]);
                streamEdit.append(",");
                streamEdit.append(checkID[2]);
                streamEdit.append(",");
                streamEdit.append(user);
                streamEdit.append(",");
                streamEdit.append(checkID[4]);
                streamEdit.append(",");
                streamEdit.append(idType);
                streamEdit.append("\n");
            } else {
                streamEdit.append(array[0]);
                streamEdit.append(",");
                streamEdit.append(array[1]);
                streamEdit.append(",");
                streamEdit.append(array[2]);
                streamEdit.append(",");
                streamEdit.append(array[3]);
                streamEdit.append(",");
                streamEdit.append(array[4]);
                streamEdit.append(",");
                streamEdit.append(array[5]);
                streamEdit.append("\n");
            }
        }
        streamEdit.flush();
        streamEdit.close();
        return true;
    }

    public void calDeleteProduct(int primary) throws IOException {
        boolean check = false;
        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] array = allDataCSV.get(i);
            int checkDelete = Integer.parseInt(array[4]);
            if (primary == checkDelete) {
                allDataCSV.remove(i);
            }
        }

        for (int i = 0; i < allDataCSV.size(); i++) {
            String[] array = allDataCSV.get(i);
            int checkDelete = Integer.parseInt(array[4]);
            if (i == 0) {
                streamDelete.append(array[0]);
                streamDelete.append(",");
                streamDelete.append(array[1]);
                streamDelete.append(",");
                streamDelete.append(array[2]);
                streamDelete.append(",");
                streamDelete.append(array[3]);
                streamDelete.append(",");
                streamDelete.append(array[4]);
                streamDelete.append(",");
                streamDelete.append(array[5]);
            } else {
                streamDelete.append("\n");
                streamDelete.append(array[0]);
                streamDelete.append(",");
                streamDelete.append(array[1]);
                streamDelete.append(",");
                streamDelete.append(array[2]);
                streamDelete.append(",");
                streamDelete.append(array[3]);
                streamDelete.append(",");
                streamDelete.append(array[4]);
                streamDelete.append(",");
                streamDelete.append(array[5]);
            }
        }
        streamDelete.flush();
        streamDelete.close();
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getRegister() {
        return register;
    }

    public Boolean calRegister(String user) throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegis), "UTF8"));
        if ((inputStream.readLine()) == null) {
            streamRegis.append(user);
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.flush();
            streamRegis.close();
            return true;
        } else {
            streamRegis.append("\n");
            streamRegis.append(user);
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.append(",");
            streamRegis.append("0");
            streamRegis.flush();
            streamRegis.close();
            return true;
        }
    }

    public void setStreamRegis() throws IOException {
        streamRegis = new FileWriter(fileRegis, true);
    }
    public void calAddType() throws IOException {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileType), "UTF8"));
        BufferedReader inputStream1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileType), "UTF8"));
        String str = inputStream.readLine();
        FileWriter streamOutput = new FileWriter(fileType,true);
        int num = 1;
        if (str == null) {
            streamOutput.append("1");
            streamOutput.append(",");
            streamOutput.append(type);
            inputStream.close();
            streamOutput.flush();
            streamOutput.close();
        } else {
            while((str = inputStream1.readLine()) != null){
                num += 1;
            }
            streamOutput.append("\n");
            streamOutput.append(String.valueOf(num));
            streamOutput.append(",");
            streamOutput.append(type);
            inputStream1.close();
            streamOutput.flush();
            streamOutput.close();
        }
    }
    public void calWriterEditType(ArrayList<String[]> typeProduct) throws IOException {
        FileWriter streamOutput = new FileWriter(fileType);
        for(int i =0;i<typeProduct.size();i++){
            String[] array = typeProduct.get(i);
            if(i == 0){
                streamOutput.append(array[0]);
                streamOutput.append(",");
                streamOutput.append(array[1]);
            }
            else{                
                streamOutput.append("\n");
                streamOutput.append(array[0]);
                streamOutput.append(",");
                streamOutput.append(array[1]);
            }
        }
    streamOutput.flush();
    streamOutput.close();
    }
}