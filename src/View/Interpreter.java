package View;
import Controller.Controller;
import Model.*;
import Model.Exceptions.MyException;
import Model.Expressions.*;
import Model.Statement.*;
import Model.Types.BoolType;
import Model.Types.RefType;
import Model.Values.BoolValue;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.RefValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.InMemRepo;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
    private static String read() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        return s;
    }


    public static void main(String[] args){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignmentStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        ArrayList<PrgState> program1 = new ArrayList<>();
        program1.add(new PrgState(ex1));
        IRepository firstRepo = new InMemRepo(program1, "log1.txt");
        Controller firstController = new Controller(firstRepo);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignmentStmt("b",new ArithExp('+',new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        ArrayList<PrgState> program2 = new ArrayList<>();
        program2.add(new PrgState(ex2));
        IRepository secondRepo = new InMemRepo(program2, "log2.txt");
        Controller secondController = new Controller(secondRepo);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignmentStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignmentStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignmentStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        ArrayList<PrgState> program3 = new ArrayList<>();
        program3.add(new PrgState(ex3));
        IRepository thirdRepo = new InMemRepo(program3, "log3.txt");
        Controller thirdController = new Controller(thirdRepo);

        String s = "";

        IStmt ex4 = new CompStmt(new CompStmt(new VarDeclStmt("a",new IntType()),
                new OpenRFileStmt(new ValueExp(new StringValue("log1.txt")))),
                new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("log1.txt")), "a"),
                        new CompStmt(new PrintStmt(new VarExp("a")),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new CompStmt(new ReadFileStmt(new ValueExp(new StringValue("log1.txt")), "a"),
                                                new PrintStmt(new VarExp("a"))), new PrintStmt(new ValueExp(new IntValue(0)))),
                                        new CloseFileStmt(new ValueExp(new StringValue("log1.txt")))))));

        ArrayList<PrgState> prg4 = new ArrayList<>();
        prg4.add(new PrgState(ex4));
        IRepository repo4 = new InMemRepo(prg4, "log4.txt");
        Controller controller4 = new Controller(repo4);


        IStmt ex5 = new CompStmt(new CompStmt(new VarDeclStmt("a",new IntType()), new VarDeclStmt("b",new IntType())),
                new CompStmt(new OpenRFileStmt(new ValueExp(new StringValue("log2.txt"))), new CompStmt( new ReadFileStmt
                        (new ValueExp(new StringValue("log2.txt")), "a"), new CompStmt( new ReadFileStmt
                        (new ValueExp(new StringValue("log2.txt")), "b"),   new CompStmt(new PrintStmt(new VarExp("a")),
                        new CompStmt(new PrintStmt(new VarExp("b")),new CloseFileStmt(new ValueExp(new StringValue("log6.txt")))))))));

        ArrayList<PrgState> prg5 = new ArrayList<>();
        prg5.add(new PrgState(ex5));
        IRepository repo5 = new InMemRepo(prg5, "log6.txt");
        Controller controller5 = new Controller(repo5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(
                new AssignmentStmt("v",new ValueExp(new IntValue(10))), new WhileStmt(
                new RelationalExp(">", new VarExp("v"),new ValueExp(new IntValue(0))),
                new CompStmt(new PrintStmt(new VarExp("v")),
                        new AssignmentStmt( "v",new ArithExp('-',new VarExp("v"),
                                new ValueExp(new IntValue(1))))))));


        ArrayList<PrgState> prg6 = new ArrayList<>();
        prg6.add(new PrgState(ex6));
        IRepository repo6 = new InMemRepo(prg6, "log6.txt");
        Controller controller6 = new Controller(repo6);

        IStmt ex7 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new NewStmt("v", new ValueExp(new IntValue(20)))), new VarDeclStmt("a",
                new RefType(new RefType(new IntType())))), new NewStmt("a", new VarExp("v"))),
                new NewStmt("v", new ValueExp(new IntValue(30)))), new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))));

        ArrayList<PrgState> prg7 = new ArrayList<>();
        prg7.add(new PrgState(ex7));
        IRepository repo7 = new InMemRepo(prg7, "log5.txt");
        Controller controller7 = new Controller(repo7);


        IStmt ex8 = new CompStmt(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new NewStmt("v", new ValueExp(new IntValue(20)))),
                new CompStmt(new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new NewStmt("a", new VarExp("v"))),  new CompStmt(new PrintStmt(new VarExp("v")),
                new PrintStmt(new VarExp("a")))));

        ArrayList<PrgState> prg8 = new ArrayList<>();
        prg8.add(new PrgState(ex8));
        IRepository repo8 = new InMemRepo(prg8, "log6.txt");
        Controller controller8 = new Controller(repo8);

        IStmt ex9 = new CompStmt( new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt
                ("v", new RefType(new IntType())), new NewStmt("v", new ValueExp(new IntValue(20)))),
                new VarDeclStmt("a", new RefType(new RefType(new IntType())))), new NewStmt("a",
                new VarExp("v"))), new PrintStmt(new ReadHeapExp(new VarExp("v")))), new PrintStmt(
                        new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                new ValueExp(new IntValue(5)))));

        ArrayList<PrgState> prg9 = new ArrayList<>();
        prg9.add(new PrgState(ex9));
        IRepository repo9 = new InMemRepo(prg9, "log9.txt");
        Controller controller9 = new Controller(repo9);

        IStmt ex10 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v",
                new RefType(new IntType())), new NewStmt("v",
                new ValueExp(new IntValue(20)))), new PrintStmt(new ReadHeapExp(new VarExp("v")))),
                new WriteHeapStmt("v", new ValueExp(new IntValue(30)))), new PrintStmt(
                        new ArithExp('+', new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)))));

        ArrayList<PrgState> prg10 = new ArrayList<>();
        prg10.add(new PrgState(ex10));
        IRepository repo10 = new InMemRepo(prg10, "log6.txt");
        Controller controller10 = new Controller(repo10);

        IStmt ex11 = new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()), new VarDeclStmt("a", new RefType(new IntType()))),
                new AssignmentStmt("v", new ValueExp(new IntValue(10)))), new NewStmt("a", new ValueExp(new IntValue(30)))),
                new ForkStmt(new CompStmt(new CompStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(22))), new AssignmentStmt("v", new ValueExp(new IntValue(32)))),
                        new PrintStmt(new VarExp("v"))), new PrintStmt(new ReadHeapExp(new VarExp("a")))))), new PrintStmt(new VarExp("v"))),
                new PrintStmt(new ReadHeapExp(new VarExp("a"))));


        ArrayList<PrgState> prg11 = new ArrayList<>();
        prg11.add(new PrgState(ex11));
        IRepository repo11 = new InMemRepo(prg11, "log8.txt");
        Controller controller11 = new Controller(repo11);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),firstController));
        menu.addCommand(new RunExample("2",ex2.toString(),secondController));
        menu.addCommand(new RunExample("3",ex3.toString(),thirdController));
        menu.addCommand(new RunExample("4",ex4.toString(),controller4));
        menu.addCommand(new RunExample("5",ex5.toString(),controller5));
        menu.addCommand(new RunExample("6",ex6.toString(),controller6));
        menu.addCommand(new RunExample("7",ex7.toString(),controller7));
        menu.addCommand(new RunExample("8",ex8.toString(),controller8));
        menu.addCommand(new RunExample("9",ex9.toString(),controller9));
        menu.addCommand(new RunExample("10",ex10.toString(),controller10));
        menu.addCommand(new RunExample("11",ex11.toString(),controller11));


        menu.show();
    }
}
