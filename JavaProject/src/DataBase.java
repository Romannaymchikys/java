
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.String;
import java.util.stream.IntStream;

public class DataBase {


    /** map для связки по одной строке
     * ключ - строка для сравнение
     * значение - map для хранение слов какие иду после страки для сровнение
     *            ключ - слово которое идет после строка для сравнение
     *            значение - как часто это слово употребляется после строки для сравнение*/
    private static Map<String, Map<String, Integer>> wordsMap = new HashMap<>();
    /** вектор для слов с заглавной буквы */
    private static Vector<String> vecBigLetters = new Vector<>();
     /** вектор для слов с точкой */
     private static Vector<String> vecWordPoint = new Vector<>();
    static boolean Selector;
    {
        Selector = true;
    }
    /**создаем вектор для имен частей речи для открытие файлов*/
    private static final Vector<String> vecNamePartsSpeech = creatVector();
    /** вектор с окончаниями частей речиж*/
    private static final Vector<Vector<String>> vecPronunciationOfSpeech = createMap();
    /** вектор для хранение частей речи
     * каждый вложеный вектор для хранение слов определенной
     * части речи
     * нам нужны группы вложеных векторов для подсчета
     * слов определенной части речи
     * */
    private static Vector<Vector<String>> vecPartsSpeech = createVecPartsSpeech();


    /** вектор для данных записываемых в фаел*/
    private static Vector<String> vecGenerationText = new Vector<>();
    /** вектор для подсчета сколько слов определенной части речи*/
    private static Vector<Integer> vecProcentGenerationText = new Vector<>();

    /**________________________________________________________________________*/
    private static Vector<String> creatVector() {
        Vector<String> vecNamePartsSpeech =
                new Vector<>(WorkFile.ReadFile("namePartsOfSpeech.txt"));
        return vecNamePartsSpeech;
    }

    private static Vector<Vector<String>> createMap()
    {
        Vector<Vector<String>> myVec = new Vector<>();
        vecNamePartsSpeech.forEach(NamePartsSpeech ->
                myVec.add(new Vector<>(WorkFile.ReadFile(NamePartsSpeech + ".txt"))));
        int Size = vecNamePartsSpeech.size();
        return myVec;
    }

    private static Vector<Vector<String>>  createVecPartsSpeech()
    {
        Vector<Vector<String>> myVec = new Vector<>();
        Vector<String> linesStart = new Vector<>();
        linesStart.add(" ");
        IntStream.range(0,vecNamePartsSpeech.size()).
                forEach(index -> myVec.add(index,linesStart));
        return myVec;
    }


    /**________________________________________________________________________*/


    public static  Map<String, Map<String, Integer>>  getWordsMap()
    {
        return wordsMap;
    }
    public static Vector<Vector<String>> getVecPartsSpeech()
    {
        return vecPartsSpeech;
    }
    public static Vector<String> getVecGenerationText()
    {
        return vecGenerationText;
    }
    public static Vector<Integer> getVecProcentGenerationText()
    {
        return vecProcentGenerationText;
    }
    public static  Vector<Vector<String>> getVecPronunciationOfSpeech()
    {
        return vecPronunciationOfSpeech;
    }
    public static  Vector<String> getVecBigLetters()
    {
        return vecBigLetters;
    }
    public static  Vector<String> getVecWordPoint()
    {
        return vecWordPoint;
    }
    /**______________________________________________________________________*/
    public static void setWordsMap( Map<String, Map<String, Integer>> newWordsMap)
    {
        wordsMap = newWordsMap;
    }
    public static void setVecPartsSpeech( Vector<String> newVecPartsSpeech, int index)
    {
        vecPartsSpeech.set(index, newVecPartsSpeech);
    }
    public static void setVecProcentGenerationText( Vector<Integer> newVecProcentGenerationText)
    {
        vecProcentGenerationText = newVecProcentGenerationText;
    }
    public static void setVecGenerationText( Vector<String> newVecGenerationText)
    {
        vecGenerationText = newVecGenerationText;
    }
    public static void setVecBigLetters(Vector<String> newVecBigLetters)
    {
        vecBigLetters = newVecBigLetters;
    }
    public static void setVecWordPoint(Vector<String> newVecWordPoint)

    {
        vecWordPoint = newVecWordPoint;
    }
}
/**________________________________________________________________________*/
 class WorkFile
{
    public static  String getFileRead()
    {
        return FILEREAD;
    }
    /**фаел для чтения*/
    private static final String FILEREAD = "tyrgenev.txt";
    /** для прочтение файла*/
    public static Vector<String> ReadFile(final String FILE_NAME)
    {
        Vector<String> lines = new Vector<>(100);
        Path path = Paths.get(FILE_NAME);
        try (BufferedReader r = new BufferedReader(new InputStreamReader
                (new FileInputStream(FILE_NAME), "cp1251")))
        {
            while (r.ready())
            {
                lines.add(r.readLine());
            }
            r.close();
        } catch (IOException ignored) {}
        return lines;
    }
}













    /*
     Группы окончаний:
 1. прилагательные
 2. причастие
 3. глагол
 4. существительное
 5. наречие
 6. числительное
 7. союз
 8. предлог
     $groups = array(
 1 => array ('ее','ие','ые','ое','ими','ыми','ей','ий','ый','ой','ем','им','ым','ом',
'его','ого','ему','ому','их','ых','ую','юю','ая','яя','ою','ею'),
 2 => array ('ивш','ывш','ующ','ем','нн','вш','ющ','ущи','ющи','ящий','щих','щие','ляя'),
 3 => array ('ила','ыла','ена','ейте','уйте','ите','или','ыли','ей','уй','ил','ыл','им','ым','ен',
'ило','ыло','ено','ят','ует','уют','ит','ыт','ены','ить','ыть','ишь','ую','ю','ла','на','ете','йте',
'ли','й','л','ем','н','ло','ет','ют','ны','ть','ешь','нно'),
 4 => array ('а','ев','ов','ье','иями','ями','ами','еи','ии','и','ией','ей','ой','ий','й','иям','ям','ием','ем',
'ам','ом','о','у','ах','иях','ях','ы','ь','ию','ью','ю','ия','ья','я','ок', 'мва', 'яна', 'ровать','ег','ги','га','сть','сти'),
 5 => array ('чно', 'еко', 'соко', 'боко', 'роко', 'имо', 'мно', 'жно', 'жко','ело','тно','льно','здо','зко','шо','хо','но'),
 6 => array ('чуть','много','мало','еро','вое','рое','еро','сти','одной','двух','рех','еми','яти','ьми','ати',
'дного','сто','ста','тысяча','тысячи','две','три','одна','умя','тью','мя','тью','мью','тью','одним'),
 7 => array ('более','менее','очень','крайне','скоре','некотор','кажд','други','котор','когд','однак',
'если','чтоб','хот','смотря','как','также','так','зато','что','или','потом','эт','тог','тоже','словно',
'ежели','кабы','коли','ничем','чем'),
 8 => array ('в','на','по','из')
);
    */

