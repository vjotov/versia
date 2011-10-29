public class MagicNumber {
    private Integer number;
    public void increase (){
        ++number;
    }
    public void decrease () {
        --number;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public MagicNumber (){
        number = 0;
    }
}