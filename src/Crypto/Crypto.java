package Crypto;



public class Crypto {
    static char[] alphabet1 = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    
    // Function to encrypt texts using Cesar
    public String crypt(int n, String sIn) {
        sIn = sIn.toLowerCase();
        char[] charSIn = sIn.toCharArray();
        char[] charSOut = new char[charSIn.length];      
        int pos1, pos2;      
        for(int i = 0; i < charSIn.length; i++)   {
            pos1 = posChar(charSIn[i], alphabet1);    
            pos2 = newPos(pos1, n);    
            if(pos2 == -1) charSOut[i] = ' ';
            else charSOut[i] = alphabet1[pos2];   
        }      
        return new String(charSOut);
    }  
    
    // Function to decrypt texts using Cesar
    public String decrypt(int n, String sIn)  {
        sIn = sIn.toLowerCase();
        char[] charSIn = sIn.toCharArray();
        char[] charSOut = new char[charSIn.length];   
        int pos1, pos2;      
        for(int i = 0; i < charSIn.length; i++) {
            pos1 = posChar(charSIn[i], alphabet1);    
            pos2 = newPos(pos1, -n);    
            if(pos2 == -1) charSOut[i] = ' ';   
            else charSOut[i] = alphabet1[pos2];   
        }   
        return new String(charSOut);
    } 
    
    // Function to return the character number
    private static int posChar(char c, char[] tab)  {
        for(int i = 0; i < tab.length; i++)   { 
            if(tab[i] == c) return i;
        }   
        return -1; 
    } 
    
    // Function to return the new position of the character
    private static int newPos(int pos,int n)  {
        int pos2 = pos;
        if(pos <= -1) {
            pos2 = -1;   
        } else {
            int i = 0;
            while(i < abs(n)) {
                if(n < 0) {
                    if(pos2 - 1 == -1) pos2 = 25;
                    else pos2--;
                } else {
                    if(pos2 + 1 >= 25) pos2 = 0;
                    else pos2++;
                }
                i++;
            }
        }
        return pos2;
    }
    
    // Function to calculate the absolute value of a number
    public static int abs(int a)  {
        if(a >= 0) return a;
        else return -a;
    }
}
