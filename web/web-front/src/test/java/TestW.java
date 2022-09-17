import org.junit.Test;
import util.MD5;

/**
 * @author 柚mingle木
 * @version 1.0
 * @date 2022/9/8
 */
public class TestW {
    @Test
    public void test1() {
        String s = "Wb5644Hs5075";
        String encrypt = MD5.encrypt(s);
        System.out.println(encrypt);//1db493192cd70868ad410da0f0c8319f
    }

    @Test
    public void test2() {
        String s = "123456789";
        String encrypt = MD5.encrypt(s);
        System.out.println(encrypt);//25f9e794323b453885f5181f1b624d0b
    }


}
