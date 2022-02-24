public class PalabraRandom {

    private String paraula;

    public PalabraRandom() {
        paraula = "";
        pensa();
    }

    public String pensa() {
        String[] paraules = {"Menja", "Pizza", "Pinya", "Linux"};

        int numero = (int) (Math.random()*4);
        paraula = paraules[numero];
        //System.out.println("He pensat la paraula " + getParaula());

        return paraula;
    }

    public String comprova(String n) {
        if(paraula.equals(n)) return "0";
        else return "1";
    }

    public String getParaula() {
        return paraula;
    }

    public void setParaula(String paraula) {
        this.paraula = paraula;
    }

    @Override
    public String toString() {
        return "SecretNum{" +
                "paraula='" + paraula + '\'' +
                '}';
    }
}