package testcase;

class Blob {

    public int value;

    public Blob(){
    }

    public Blob(int value){
        this.value = value;
    }

}

interface Blob2Int {
    public int invoke(Blob self);
}

interface BlobSetter {
    public void invoke(Blob b, int value);
}
