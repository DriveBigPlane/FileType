import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import java.io.File;
/**
 * author :

CFMS ：Computer files management system
 * version ：1.0 2013-3-1 下午10:21:32
 */
public class Restore {
 // 缓存文件头信息-文件头信息
 public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();
 static {
  // images
  mFileTypes.put("FFD8FFE0", "jpg");
  mFileTypes.put("89504E47", "png");
  mFileTypes.put("47494638", "gif");
  mFileTypes.put("49492A00", "tif");
  mFileTypes.put("424D", "bmp");
  //
  mFileTypes.put("41433130", "dwg"); // CAD
  mFileTypes.put("38425053", "psd");
  mFileTypes.put("7B5C727466", "rtf"); // 日记本
  mFileTypes.put("3C3F786D6C", "xml");
  mFileTypes.put("68746D6C3E", "html");
  mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
  mFileTypes.put("CFAD12FEC5FD746F", "dbx");
  mFileTypes.put("2142444E", "pst");
  mFileTypes.put("D0CF11E0", "doc");
  mFileTypes.put("5374616E64617264204A", "mdb");
  mFileTypes.put("FF575043", "wpd");
  mFileTypes.put("252150532D41646F6265", "ps");
  mFileTypes.put("255044462D312E", "pdf");
  mFileTypes.put("E3828596", "pwl");
  mFileTypes.put("AC9EBD8F", "qdf");
  mFileTypes.put("504B0304", "docx");
  mFileTypes.put("52617221", "rar");
  mFileTypes.put("57415645", "wav");
  mFileTypes.put("41564920", "avi");
  mFileTypes.put("2E7261FD", "ram");
  mFileTypes.put("2E524D46", "rm");
  mFileTypes.put("000001BA", "mpg");
  mFileTypes.put("000001B3", "mpg");
  mFileTypes.put("6D6F6F76", "mov");
  mFileTypes.put("3026B2758E66CF11", "asf");
  mFileTypes.put("4D546864", "mid");
  mFileTypes.put("1F8B08", "gz");
 }
 
 /**
  * 根据文件路劲获取文件头信息
  * @param filePath ：文件路径
  * @return         : 文件头信息
  */
 public static String getFileType(String filePath) {
  return mFileTypes.get(getFileHeader(filePath));
 }
 
 /**
  * 根据文件路径获取文件头信息
  * @param filePath  ：文件路径
  * @return          ：文件头信息
  */
 public static String getFileHeader(String filePath){
  FileInputStream is = null;
  String value = null;
  try {
   is = new FileInputStream(filePath);
   byte[] b = new byte[4];
   is.read(b,0,b.length);
   value = bytesToHexString(b);
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   if (null != is) {
    try {
     is.close();
    } catch (IOException e2) {
    }
   }
  }
  return value;
 }
 
 /**
  * 将要读取文件信息的文件的byte数组转换成string类型表示
  * @param src ：ya
  * @return
  */
 private static String bytesToHexString(byte[] src) {
  StringBuilder builder = new StringBuilder();
  if (src == null || src.length <= 0) {
   return null;
  }
  String hv;
  for (int i = 0; i < src.length; i++) {
   // 以十六进制(基数 16)无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
   hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
   if (hv.length() < 2) {
    builder.append(0);
   }
   builder.append(hv);
  }
  return builder.toString();
 }

 public static void main(String[] args) {
     
    JFileChooser fileChooser = new JFileChooser("D:\\");  
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
    int returnVal = fileChooser.showOpenDialog(fileChooser);
    String filePath="";  
    if(returnVal == JFileChooser.APPROVE_OPTION) {
        filePath= fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的
    }
    File file = new File(filePath);
    File files[] = file.listFiles();
     for(File f : files) {
        if(f.isFile()) {
           String s=getFileType(f.getAbsolutePath());
           if(!f.getName().substring(f.getName().lastIndexOf(".")+1).equals(s)) {
              System.out.println(f.getAbsolutePath()+" "+f.renameTo(new File(f.getAbsoluteFile()+"."+s)));
           }
        }
     }
 }
}