package encrption;

import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.extra.Config;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestEncryption {
    public static void main(String[] args) {
        Config.registryEncryption();
        String commands3 =ResourceReader.read("testfile/encrption/encrption.script");
        //JsonConvertor jc = new JsonConvertor(commands);
        JsonConvertor jc = new JsonConvertor(new String[]{commands3});
        //System.out.println(commands3);
        String dest = jc.convert("{}");
        System.out.println(dest);
    }
}
