package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.Exceptions.MyException;
import Model.DataStructures.MyIDictionary;
import Model.Values.Value;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value val){
        this.e = val;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer,Value> heap) throws MyException {
        return e;
    }

    @Override
    public String toString(){
        return e.toString();
    }
}
