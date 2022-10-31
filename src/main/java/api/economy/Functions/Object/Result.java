package api.economy.Functions.Object;

import java.lang.reflect.Array;

public class Result {

    private Object[] array = new Object[2];

    public Result(boolean status, Object value) {
        this.array[0] = status;
        this.array[1] = value;
    }

    public boolean getStatus(){ return (boolean) this.array[0]; }
    public Object getValue(){ return this.array[1]; }
}
