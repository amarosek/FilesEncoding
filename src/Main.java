import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        String fileDestination = "C:\\Users\\appma\\OneDrive\\Pulpit\\send.txt";
        String text = "";

        try {
            text = readFileAsString(fileDestination);
        } catch (IOException e) {
            System.out.println("Error");
        }
        System.out.println(text);

        byte[] bytes = stringToBytes(text);
        String decimalBytes = toBinary(bytes);
        String prettyBinary = prettyBinary(decimalBytes, 8, " ");
        String codedBinary = codeBinary(prettyBinary);
        String prettyCodedBinary = prettyBinary(codedBinary, 8, " ");
        saveToFile(prettyCodedBinary);
    }

    public static String readFileAsString(String fileDest) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileDest)));
    }

    public static byte[] stringToBytes(String text) {
        byte[] bytes = text.getBytes();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            builder.append(bytes[i]).append(" ");
        }
        return bytes;
    }

    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * bytes.length; i++) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString();
    }

    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }

    public static String codeBinary(String binaryText) {

        Random generator = new Random();
        String codedText = "";

        String[] inputArray = binaryText.split(" ");

        for (int j = 0; j < inputArray.length; j++) {
            char[] characters = inputArray[j].toCharArray();
            int min = 1;
            int max = 7;
            int random = generator.nextInt(max - min + 1) + min;
            if (characters[random] == '0') {
                characters[random] = '1';
            } else {
                characters[random] = '0';
            }
            inputArray[j] = new String(characters);
            codedText += inputArray[j];
        }
        return codedText;
    }

    public static void saveToFile(String finalText) throws IOException {
        File file = new File("C:\\Users\\appma\\OneDrive\\Pulpit\\received.txt");
        FileWriter write = new FileWriter(file);
        write.write(finalText);
        write.close();
    }
}
