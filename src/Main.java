public class Main {
    public static void main(String[] args)
    {
        try {
            new MainForm();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}