package SharedImplemintations;

import Interfaces.IIORProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IORFileProvider implements IIORProvider {
    private String IORCache =null;
    private String path;

    public IORFileProvider(String path) {
        this.path = path;
    }

    @Override
    public String GetIOR() throws IOException {
        if(IORCache == null) {
            BufferedReader in = new BufferedReader(new FileReader(path));
            IORCache = in.readLine();
            in.close();
        }
        return IORCache;
    }
}
