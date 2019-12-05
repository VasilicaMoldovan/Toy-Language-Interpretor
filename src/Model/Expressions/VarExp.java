package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.Exceptions.MyException;
import Model.DataStructures.MyIDictionary;
import Model.Values.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String newId){
        this.id = newId;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer,Value> heap) throws MyException {
        return tbl.lookup(id);
    }

    @Override
    public String toString(){
        return id;
    }
}
