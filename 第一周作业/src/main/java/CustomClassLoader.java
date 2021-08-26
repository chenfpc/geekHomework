import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CustomClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        CustomClassLoader customClassLoader = new CustomClassLoader();

        Class clazz = customClassLoader.loadClass("Hello");
        customClassLoader.invoke(clazz,"hello");
    }

    /**
     * @Description:   调用指定类的指定方法
     * @Author: huxing
     * @Date: 2021-06-28 09:36
     * @param classz:   class类字节码
     * @param methodName:  方法名
     * @return: void
     **/
    private void invoke(Class<?> classz, String methodName) throws Exception{
        // 初始化创建类对象
        Object instance = classz.getDeclaredConstructor().newInstance();
        // 初始化创建类方法
        Method method = classz.getMethod(methodName);
        // 调用方法
        method.invoke(instance);
    }

//    @Override
//    protected Class<?> findClass(String name) {
//        BufferedInputStream inputStream = (BufferedInputStream) this.getClass().getClassLoader().getResourceAsStream("Hello1.xlass");
//        int byteSize = 10240;
//        byte[] originBytes = new byte[byteSize];
//        byte[] reverseBytes = new byte[originBytes.length];
//
//        int len = 0;
//        int start = 0;
//        try {
//            while ((len = inputStream.read(originBytes)) != -1) {
//                for (int i = 0; i < len; i++) {
//                    if (len < byteSize) {
//                        reverseBytes[start] = originBytes[byteSize - len];
//                    } else{
//                        reverseBytes[start] = originBytes[i];
//                    }
//                }
//            }
//        } catch (IOException e) {
//
//        }
//        for (int i = 0; i < originBytes.length; i++) {
//            reverseBytes[i] = (byte) (255 - originBytes[i]);
//        }
//
//        return defineClass(name, reverseBytes, 0, reverseBytes.length);
//    }

    public static byte[] decode(byte[] byteArray){
        // 定义一个解密字节数组长度
        byte[] targetArray = new byte[byteArray.length];
        // 转义长度
        for (int i=0; i<byteArray.length; i++){
            targetArray[i] = (byte)(255 - byteArray[i]);
        }
        // 返回解密后字节长度
        return targetArray;
    }

    @Override
    protected Class<?> findClass(String name) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("/Users/fanpengchen5/IdeaProjects/geekHomework/第一周作业/src/main/resources/Hello.xlass");
            byte[] originBytes = new byte[fileInputStream.available()];
            fileInputStream.read(originBytes);
            byte[] reverseBytes = decode(originBytes);
            return defineClass(name, reverseBytes, 0, reverseBytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
