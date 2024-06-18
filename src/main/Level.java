package main;
public class Level {

    String[] stringArr = null;

    public String randomPrint() 
    {
        if (stringArr == null || stringArr.length == 0) 
        {
           
            return "";
        }

        String str = stringArr[(int) (Math.random() * stringArr.length)];
        return str;
    }
    
    
}
