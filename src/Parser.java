import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Parser {
    private static Map<String,Integer> result;


    Parser(){
        result = new HashMap<>();
    }

    public void countJavaWords(String javaReservedWorlds, String inputFile, String outputFile){
        try (OutputStream fileToWriteIn = new FileOutputStream(outputFile);
             InputStream javaWords = new FileInputStream(javaReservedWorlds);
             InputStream fileToReadFrom = new FileInputStream(inputFile))
        {
             byte[] bytesStreamJavaWords = new byte[javaWords.available()];
             byte[] bytesStreamInputFile = new byte[fileToReadFrom.available()];
             javaWords.read(bytesStreamJavaWords, 0, bytesStreamJavaWords.length);
             fileToReadFrom.read(bytesStreamInputFile, 0, bytesStreamInputFile.length);

             String javaWords_String = new String(bytesStreamJavaWords);
             String[] arrayOfReservedWords = javaWords_String.split(" ");

             String fileToReadFrom_String = new String(bytesStreamInputFile);
             String[] arrayInputFile = fileToReadFrom_String.split("\\s");

            for (int i = 0; i < arrayOfReservedWords.length; i++) {
                int count = 0;
                for (int j = 0; j < arrayInputFile.length; j++) {
                    if(arrayOfReservedWords[i].equals(arrayInputFile[j])) {
                        result.put(arrayOfReservedWords[i],++count);
                    }
                }
            }

            String[] result_String = new String[result.size()];
            int index = 0;
            for (Map.Entry entry : result.entrySet()){
                result_String[index] = "Word " + "\"" + entry.getKey() + "\"" +
                        " was used in file "
                        + inputFile + " "
                        + entry.getValue() + " times.\n";
                index++;
            }

            StringBuilder stringBuilder= new StringBuilder();
            for (String aResult_String : result_String) {
                stringBuilder.append(aResult_String);
            }

            byte[] buffer = stringBuilder.toString().getBytes();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(buffer);
            bos.writeTo(fileToWriteIn);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
