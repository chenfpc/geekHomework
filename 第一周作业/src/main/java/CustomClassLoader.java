import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CustomClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        new CustomClassLoader().findClass("jvm.Hello").newInstance();
    }

    @Override
    protected Class<?> findClass(String name) {
//        File file = new File("./src/main/resources/Hello.xclass");
        FileInputStream fileInputStream = (FileInputStream)this.getClass().getResourceAsStream("Hello.xclass");


       byte[] originBytes = null;
        try {

            FileChannel fileChannel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
            while (fileChannel.read(byteBuffer) > 0) {}
            originBytes = byteBuffer.array();
        } catch (IOException e) {

        }



        byte[] reverseBytes = new byte[originBytes.length];

        for (int i = 0; i < originBytes.length; i++) {
            reverseBytes[i] = (byte) (255 - originBytes[i]);
        }

        return defineClass(name, reverseBytes, 0, reverseBytes.length);
    }
}
