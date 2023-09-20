package jsonio;

import java.io.OutputStream;

public interface IToJson {

    OutputStream toOutputStream();

    boolean writeToFile();

}
