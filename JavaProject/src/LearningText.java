import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LearningText {


    public static void readFileAndLerningText (final String FILE_NAME) throws  Exception
    {
        final String SPASE = " ";
        Vector<String> vecStringRead = new Vector<>(100);
        Path path = Paths.get(FILE_NAME);
        try ( BufferedReader r = new BufferedReader(new InputStreamReader
                (new FileInputStream(FILE_NAME), "cp1251")))
        {
            while (r.ready())
            {
                String[] words = r.readLine().split(SPASE);
                vecStringRead.addAll(Arrays.asList(words));
            }
            r.close();
        }
        catch (IOException ignored) {}
        while (vecStringRead.remove(""));
        int index = 0;
        String wordValueOne = capitalization(vecStringRead.elementAt(index));
        String wordValueTwo = capitalization(vecStringRead.elementAt(++index));
        String wordValueThree = capitalization(vecStringRead.elementAt(++index));

        for(; index  < vecStringRead.size(); index++)
        {
           if(index ==vecStringRead.size() -1)
           {
            System.out.println("grg");
           }
            /** определяем части речи*/
            findingPartsSpeech(indexSearch(wordValueOne), wordValueOne);
            findingPartsSpeech(indexSearch(wordValueTwo), wordValueTwo);
            findingPartsSpeech(indexSearch(wordValueThree), wordValueThree);

            LearningText.lerningText(wordValueOne, wordValueTwo);
            LearningText.lerningText(wordValueOne + SPASE + wordValueTwo,
                    wordValueThree);

            wordValueOne = wordValueTwo;
            wordValueTwo = wordValueThree;
            wordValueThree = capitalization(vecStringRead.elementAt(index));
        }
    }
    /**_____________________________________________________________________________*/
    public static String capitalization(String word)
    {
        /**добавляет слово с точкой в вектор VecWordPoint*/
        if(word.endsWith("."))
        {
            DataBase.getVecWordPoint().add(word);
        }
        /** добавляет слово с Заглавной буквы в вектор VecBigLetters*/

         if(Character.isUpperCase(word.charAt(0)))
        {
             DataBase.getVecBigLetters().add(word);
        }
        return word;
    }
    /**_____________________________________________________________________________*/
    public static void lerningText(String wordKey ,
                                             String wordValue )
    {
        Map<String, Map<String, Integer>> WordsMap =  DataBase.getWordsMap();

        /**есть ли сравнивоемое слово(wordKey) нет в калекции*/
            if(!(WordsMap.containsKey(wordKey)))
            {
                /** если нет(wordTwo) добавляем его в мар и добавляе
                // слово(wordOne) во вложенный мар*/
                WordsMap.put(wordKey, new HashMap<>());
                DataBase.getWordsMap().get(wordKey).put(wordValue, 1);
            }
            else {
                /** проверяем есть ли данное слово(wordValue) уже в вложеном мар*/
                if( DataBase.getWordsMap().get(wordKey).containsKey(wordValue))
                {
                    /** если есть слово(wordValue) обновляем мар
                    // увеличивая счетчик этого слово в вложеном мар*/
                    DataBase.getWordsMap().get(wordKey).put(wordValue,
                            DataBase.getWordsMap().get(wordKey).get(wordValue) + 1);
                }
                /** иначе добавляем слово(wordValue) во вложенный мар*/
                else
                    DataBase.getWordsMap().get(wordKey).put(wordValue, 1);
                 }
    }
    /**_____________________________________________________________________________*/
    /** определяет индекс части речи
     записывая индекс относительно вектора VecNamePartsSpeech*/
    public static int indexSearch(String word)
    {
        Vector<Vector<String>> myVec =
                DataBase.getVecPronunciationOfSpeech();
        for(Vector<String> myVecEmd : myVec)
        {
            for(String speech : myVecEmd)
            {
                if(word.endsWith(speech))
                {
                    DataBase.Selector = true;
                    return myVec.indexOf(myVecEmd);
                }
            }
        }
        DataBase.Selector = false;
        return myVec.size() - 1;
    }
    /**_____________________________________________________________________________*/
    /**для соотношение частей речи
     * добовляет слово в определенную группу векторов*/
    public static void findingPartsSpeech(int index, String word)
    {
        if(DataBase.Selector )
        {
             DataBase.getVecPartsSpeech().elementAt(index).add(word);
        }
    }

}
