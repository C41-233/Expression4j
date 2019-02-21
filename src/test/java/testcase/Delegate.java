package testcase;

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