import parcs.AM;
import parcs.AMInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Runner implements AM {
    @Override
    public void run(AMInfo info) {
    	var test = info.parent.readInt();
    	info.parent.write(test);
    }
}
