import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.IntStream;
public class CompilationAndWriteText
{
    /**поиск следуючего слова*/
    public static void addingWord(Map<String, Map<String,
            Integer>> WordsMap, String word )
    {
        Vector<Integer> myVecProcent =
                DataBase.getVecProcentGenerationText();

        Vector<Integer> setValues = new Vector<>(WordsMap.
                get(word).values());

        Vector<String> setKey = new Vector<>(WordsMap.
                get(word).keySet());
        Iterator<Integer> itrSetValues = setValues.iterator();
        Iterator<String> itrSetKey = setKey.iterator();
        Vector<String> myVecWords = new Vector<>();
        for (Integer max = 0, value = 0; itrSetValues.hasNext(); )
        {
            value = itrSetValues.next();
            String Key = itrSetKey.next();
            if (max < value) {
                max = value;
                myVecWords.removeAllElements();
                myVecWords.add(Key);
            } else if (max == value) {
                myVecWords.add(Key);
            }
        }
        // если два слова равные по рэйтенгу
        // то надо смотреть какого слово нет в
        // ввыходном тексте
        if (myVecWords.size() > 1) {
            /** определяем какой части речи не хватает*/
            Random random = new Random();
            int indexRandom = random.nextInt(myVecWords.size());
            String wordAdd = myVecWords.elementAt
                    (indexRandom);
            DataBase.getVecGenerationText().add(wordAdd);
        } else {
            DataBase.getVecGenerationText().add(myVecWords.lastElement());
        }
    }
    public static void WriteText() throws Exception
    {
        String lineSeparator = System.getProperty("line.separator");
        Vector<String> vecGenerationText =
                DataBase.getVecGenerationText();
        File file = new File("Example.txt");
        // Создание файла
        file.createNewFile();
        // Создание объекта FileWriter
        FileWriter writer = new FileWriter(file);
        int sizeLine = 0;
        // Запись содержимого в файл
        for(String word : vecGenerationText)
        {
            if(sizeLine >= 70){
                writer.write(lineSeparator);
                sizeLine = 0;
            }
            writer.write(word + " ");
            sizeLine += word.length() + 1;
        }
        writer.flush();
        writer.close();
    }

    public static void compilationText() throws Exception
    {
        Vector<String> vecGenerationText =
                DataBase.getVecGenerationText();
        Random random = new Random();
        /** добавляем в вектор первое слово с заглавной буквы*/
       int sizeVecBigLetters = DataBase.getVecBigLetters().size();
       int indexRandom = random.nextInt(DataBase.getVecBigLetters().size());
        vecGenerationText.add(DataBase.getVecBigLetters().
                elementAt(indexRandom));

        /**вектор для  соотношение частей речи*/
        Vector<Integer> myVecProcent = DataBase.getVecProcentGenerationText();
        /**его индексация*/
        Vector<Integer> finalMyVecProcent = myVecProcent;
        IntStream.range(0,DataBase.getVecPartsSpeech().size()).
                forEach(index -> finalMyVecProcent.add(0));
        myVecProcent = finalMyVecProcent;
        /**добовляем первое слово в процентное соотношение*/
        myVecProcent = finalMyVecProcent;
        String wordOne = vecGenerationText.lastElement();
        myVecProcent = colculProc(myVecProcent, wordOne);

        Scanner in = new Scanner(System.in);
        System.out.print("Введите количество слов:");
        int quantityWords = in.nextInt();
        Map<String, Map<String, Integer>> WordsMap =
                DataBase.getWordsMap();
        for (int count = 0 ; quantityWords != 4; --quantityWords, ++count)
        {
            /** если в генерируемом тексте больше одного слова*/
           if( DataBase.getVecGenerationText().size() > 1)
           {
               String wordTwo = vecGenerationText.lastElement() + " "
                       + wordOne;
               wordOne = vecGenerationText.lastElement();
               /** если  есть совпо дение по двум словам*/
               if(WordsMap.containsKey(wordTwo))
               {
                   addingWord(WordsMap, wordTwo);
               }
               else
                   {
                   addingWord(WordsMap, wordOne );
               }
               myVecProcent = colculProc(myVecProcent, wordOne);
           }
           else
               {
               addingWord(WordsMap, wordOne);
               wordOne = vecGenerationText.lastElement();
           }
            myVecProcent = colculProc(myVecProcent, wordOne);
           if(count == 10)
           {
               wordSearch();
               count = 0;
           }
        }
    /** в конце добовляем слово сточкой*/

        DataBase.getVecGenerationText().add(wordSearchWithDot());
    }
    /**_________________________________________________________________________
     * ичем слово сточкой */

    public static void wordSearch()
    {
        int index = percentRelativity( DataBase.getVecProcentGenerationText());
        boolean selector = false;
        Vector<String> vecStr = DataBase.getVecPartsSpeech().elementAt(1);
        int indexVl = 0;
        for(;indexVl < vecStr.size(); indexVl++ )
        {
            if(DataBase.getVecGenerationText().contains(vecStr.elementAt(indexVl)))
            {
                selector = true;
            }
        }
        if(!(selector))
        {
            String str = vecStr.elementAt(indexVl);
            DataBase.getVecGenerationText().add(str);
        }
    }

     public static String wordSearchWithDot()
     {
         /** вычисляем каую часть речи нужно добавлять*/
         int index = percentRelativity( DataBase.getVecProcentGenerationText());

         /**берем вектор этих часте речи*/
         Vector<String> vecWords = DataBase.
                 getVecPartsSpeech().elementAt(index);
         Random random = new Random();
         int indexRandom = random.nextInt(DataBase.getVecWordPoint().size());
         return DataBase.getVecWordPoint().elementAt(indexRandom);
     }
    /**___________________________________________________________________________
     * Определяем к какой части речи относится слово
     * каждых слов пять нужно делать соотношение
     */
    public static Vector<Integer> colculProc(Vector<Integer> myVecProcent,
                                             String word)
    {
        int index = LearningText.indexSearch(word);
        if(index < myVecProcent.size())
        {
            return myVecProcent;
        }
        int indProc = myVecProcent.elementAt(index) + 1;
        myVecProcent.add(index, indProc);
        return myVecProcent;
    }

    /*** вычмсление процентного соотношение*/
    public static int percentRelativity(Vector<Integer> myVecProcent) {
        int counter = 0;
        for (int index = 0; myVecProcent.size() > index; ++index) {
            int sizePr = DataBase.getVecPartsSpeech().elementAt(index).size();
            int del = myVecProcent.elementAt(index) / (sizePr / 100);
            if (counter < del) {
                counter = del;
            }
        }
        return counter;
    }
}
