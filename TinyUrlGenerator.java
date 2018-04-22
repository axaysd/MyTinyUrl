import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.StringBuilder;

public class TinyUrlGenerator
{
    //base 62
    public String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    //base 36
    //public String charSet = "abcdefghijklmnopqrstuvwxyz0123456789";

    public final int base = charSet.length();

    private AtomicInteger counter = new AtomicInteger(10);

    public Map<Long, String> store = new HashMap<>();

    public String generateUrl(String url)
    {
        final long nextNumber = getNextNumber();
        String tinyUrl = convertAndGetBase62Code(nextNumber);
        store.put(nextNumber, url);
        return tinyUrl;
    }

    public String reverseUrl(String tinyUrl)
    {
        long tinyUrlId = convertToBase10(tinyUrl);
        return store.get(tinyUrlId);
    }

    private String convertAndGetBase62Code(long num)
    {
        StringBuffer sb = new StringBuffer("");
        while(num>0)
        {
            int reminder = (int) (num%base);
            sb.append(charSet.charAt(reminder));
            num = num/base;
        }
        return sb.toString();
    }

    private long convertToBase10(String tinyUrlCode)
    {
            long nBase10=0;
            char[] chars = new StringBuilder(tinyUrlCode).toString().toCharArray();
            for(int i=chars.length-1; i>=0; i--)
            {
                int index = charSet.indexOf(chars[i]);
                nBase10 += index * (long) Math.pow(base, i);
            }
            return nBase10;
    }

    private long getNextNumber()
    {
        int counterValue = counter.incrementAndGet();
        long systemTime = Long.valueOf("" + counterValue + System.currentTimeMillis());
        return systemTime;
    }

    public static void main(String[] args)
    {
        TinyUrlGenerator tug = new TinyUrlGenerator();
        String url1 = "http://www.google.com";
        String tinyUrl1 = tug.generateUrl(url1);
        System.out.print("Short URL for " + url1 + ":   ");
        System.out.println("axaysd.com/"+tinyUrl1);
        String reversedUrl1 = tug.reverseUrl(tinyUrl1);
        System.out.print("Original URL axaysd.com/" + tinyUrl1 + ": ");
        System.out.println(reversedUrl1);
    }
}