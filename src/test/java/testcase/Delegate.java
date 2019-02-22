package testcase;

interface Action{
    public void invoke();
}

interface FuncString{
    public String invoke();
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

interface FuncStringObjectsString{
    public String invoke(String s, Object... args);
}

interface ActionStringObjects{
    public void invoke(String s, Object... args);
}