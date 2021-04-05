package reghzy.atominacraft;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class GameLauncher implements Runnable {
    public static void main(String[] args) {
        GameLauncher game = new GameLauncher();
        game.start();
        System.out.println("Main func returned");
    }

    private Thread thread;

    public void start() {
        thread = new Thread(this, "AtominaCraft");
        thread.start();
    }

    @Override
    public void run() {

        //try {
        //    JFileChooser chooser = new JFileChooser();
        //    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        //        String hash = getFileChecksum(MessageDigest.getInstance("SHA-256"), chooser.getSelectedFile());
        //        System.out.println(hash);
        //    }
        //}
        //catch (Exception e) {
        //}

        AtominaCraft atominaCraft = new AtominaCraft();
        atominaCraft.run();
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            //sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        //return complete hash
        return sb.toString();
    }
}
