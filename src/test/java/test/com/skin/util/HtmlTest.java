package test.com.skin.util;

import java.io.File;

import com.skin.ayada.util.HtmlUtil;
import com.skin.ayada.util.IO;

/**
 * @author weixian
 * @version 1.0
 */
public class HtmlTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        execute(new String[]{"D:\\workspace\\jourdyn\\1.html"});
    }

    /**
     * @param args
     */
    public static void execute(String[] args) {
        if(args == null || args.length < 1) {
            System.out.println("usage: HtmlTest [FILE]");
            return;
        }

        File file = new File(args[0]);
        System.out.println("file: " + args[0]);

        if(!file.exists() || !file.isFile()) {
            System.out.println("文件不存在！");
            return;
        }

        try {
            String source = IO.read(file, "utf-8", 4096);
            System.out.println(HtmlUtil.remove(source));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
