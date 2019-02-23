package testcase;

interface Func<R>{
    public R invoke();
}

interface FuncDD{
    public double invoke(double x);
}

interface FuncIII {
    public int invoke(int x, int y);
}

interface FuncLLL {
    public long invoke(long x, long y);
}

interface FuncILI {
    public int invoke(int x, long y);
}

interface FuncVar2<T1, T2, R>{
    public R invoke(T1 s, T2... args);
}

interface Action{
    public void invoke();
}

interface Action1<T>{
    public void invoke(T a);
}

interface Action2<T1, T2>{
    public void invoke(T1 t1, T2 t2);
}

interface ActionVar2<T1, T2>{
    public void invoke(T1 s, T2... args);
}