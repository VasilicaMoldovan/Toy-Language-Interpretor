package Model.Expressions;

import Model.DataStructures.IHeap;
import Model.DataStructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1 - and, 2 - or

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer,Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())){
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1, n2;
                n1 = (boolean)v1.getVal();
                n2 = (boolean)v2.getVal();
                if ( op == 1)   return new BoolValue(n1 && n2);
                if ( op == 2 )  return new BoolValue(n1 || n2);
                if ( op > 2 )
                    throw new MyException("invalid operation");
            }
            else
                throw new MyException("first operand is not an integer");
        }
        return null;
    }
}
