public class MainCap
{
    public static void Cap() throws Exception

    {
        DataBase Data = new DataBase();
        LearningText.readFileAndLerningText(WorkFile.getFileRead());
        try
        {
            CompilationAndWriteText.compilationText();
            CompilationAndWriteText.WriteText();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
