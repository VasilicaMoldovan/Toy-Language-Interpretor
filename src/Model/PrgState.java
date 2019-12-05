package Model;

import Model.DataStructures.*;
import Model.Exceptions.MyException;
import Model.Statement.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram; //optional field, but good to have
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heapTable;
    private int id = 1;
    private static int lastAssign = 1;

    public static int getId(){
        lastAssign++;
        return lastAssign;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symtbl,
             MyIList<Value> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> fileTbl, IHeap heap, int newId){

        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg.deepCopy();//recreate the entire original prg
        fileTable = fileTbl;
        heapTable = heap;
        id = newId;
        exeStack.push(prg);
    }

    public PrgState(IStmt initialProgram){
        exeStack = new MyStack<>();
        symTable = new MyDictionary<>();
        out = new MyList<Value>();
        this.originalProgram = initialProgram;
        fileTable = new MyDictionary<>();
        heapTable = new Heap<Integer, Value>();

        exeStack.push(originalProgram);
    }

    /*public int getId(){
        return id;
    }*/

    public void setId(int newId){
        id = newId;
    }

    public PrgState oneStep() throws MyException{
        if (exeStack.isEmpty())
            throw new MyException("Program state stack is empty");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted(){
        if (this.exeStack.isEmpty())
            return false;
        return true;
    }

    public void addOutput(Value element){
        this.out.add(element);
    }

    @Override
    public String toString(){
        return "Thread id: " + id + " " + exeStack.toString() + "Symbol Table:\n" + symTable.toString() + out.toString() +
                "File table:\n" + fileTable.toString() + "\n" + "Heap:\n" + heapTable.toString() + "\n";
    }
    public MyIStack<IStmt> getStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public IHeap getHeapTable(){
        return heapTable;
    }

    public MyIList<Value> getOut(){
        return out;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    public void setExeStack(MyIStack<IStmt> newExeStack) {
        exeStack = newExeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> newSymTable){
        symTable = newSymTable;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt newProgram){
        originalProgram = newProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTbl){
        this.fileTable = fileTbl;
    }


    //public void addFile(Value, Bu)
}
