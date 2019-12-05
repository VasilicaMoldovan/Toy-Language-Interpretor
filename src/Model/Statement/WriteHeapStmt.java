package Model.Statement;

import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private Exp expression;

    public WriteHeapStmt(String newName, Exp newExp){
        this.varName = newName;
        this.expression = newExp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        if ( state.getSymTable().isDefined(varName) ){
            Value aux = state.getSymTable().getValue(varName);
            if ( aux.getType() instanceof RefType){
                RefValue aux2 = (RefValue)aux;
                if (state.getHeapTable().isDefined(aux2.getAddr())){
                    Value expResult = expression.eval(state.getSymTable(), state.getHeapTable());
                    if ( expResult.getType().equals(aux2.getType())){
                        state.getHeapTable().update(((RefValue) aux).getAddr(), aux2);
                    }
                    else
                        throw new MyException("Different types");
                }
                else
                    throw new MyException("Undefined key");
            }
            else
                throw new MyException("Invalid type");
        }
        else
            throw new MyException("Undefined key");
        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new WriteHeapStmt(varName, expression);
    }

    @Override
    public String toString(){
        return "WriteHeap(" + varName + "->" + expression.toString() + ")";
    }
}
