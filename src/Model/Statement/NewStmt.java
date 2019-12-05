package Model.Statement;

import Model.DataStructures.IHeap;
import Model.Exceptions.MyException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements IStmt {
    private String varName;
    private Exp expression;

    public NewStmt(String newName, Exp newExpression){
        this.varName = newName;
        this.expression = newExpression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException{
        if (state.getSymTable().isDefined(varName) && state.getSymTable().getValue(varName).getType() instanceof RefType){
            try {
                Value aux = expression.eval(state.getSymTable(), state.getHeapTable());
                RefValue valueSymTable = (RefValue)state.getSymTable().getValue(varName);
                IHeap<Integer, Value> heapTable = state.getHeapTable();
                if ( aux.getType().equals(valueSymTable.getLocationType())){
                    RefValue newValue = new RefValue((Integer) heapTable.getFirstFree(), aux.getType());
                    newValue.setAddress((Integer) heapTable.getFirstFree());
                    state.getSymTable().update(varName, newValue);
                    heapTable.update(aux);
                }
            }
            catch (MyException error){
                throw new MyException("Error on executing expression");
            }
        }
        else
            throw new MyException("Error on executing the new statement");

        return null;
    }

    @Override
    public IStmt deepCopy(){
        return new NewStmt(varName, expression);
    }

    @Override
    public String toString(){
        return "New( " + varName + "->" + expression.toString() + " )";
    }
}
